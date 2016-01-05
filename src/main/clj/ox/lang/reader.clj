(ns ox.lang.reader
  (:refer-clojure :exclude [read read-string vector])
  (:require [ox.lang.core :as ox]
            [clj-tuple :refer [vector]])
  (:import java.lang.StringBuilder
           java.util.ArrayList

           (java.io
            ,,Reader
            ,,StringReader
            ,,BufferedReader
            ,,File
            ,,FileReader)

           clojure.lang.IMeta

           (ox.lang
            ,,Box)

           (ox.lang.parser
            ,,Position
            ,,QueuePosReader)))

(defn file? [o]
  (instance? File o))

(defn reader? [o]
  (instance? Reader o))



(defn qpr? [o]
  (instance? QueuePosReader o))

(defn- -chr [c]
  (if (pos? c)
    (char c)
    :eof))

(defn peek-chr [^QueuePosReader r]
  (-chr (.peek r)))

(defn read-chr! [^QueuePosReader r]
  (-chr (.read r)))

(defn pop-chr! [^QueuePosReader r]
  (.pop r)
  nil)

(defn get-pos [^QueuePosReader r]
  (.getPos r))



(defn rbind [m & args]
  (loop [m               m
         [chrs f & args] args]
    (if (and chrs f)
      (recur (reduce #(assoc %1 %2 f) m chrs) args)
      m)))



(declare ^:dynamic *reader-dispatch*
         ^:dynamic *terminators*
         ^:dynamic *unhandled-character-reader*
         ^:dynamic *unterminated-form-fn*)

(defn get-reader
  "Iteratively reads a character from the given Reader, indexing into
  *reader-dispatch* with that character until either an invalid
  transition is encountered or an appropriate transition is found.

  Returns the appropriate reader fn. If no fn is found, or the given
  transition is unbound, calls the *unhandled-character-reader* fn
  with the full path."

  [^QueuePosReader r]
  {:pre [(qpr? r)]}
  (let [_     (peek-chr r)
        start (get-pos r)]
    (loop [table  *reader-dispatch*
           buffer (StringBuilder.)]
      (let [c (peek-chr r)]
        (if-not (= c :eof)
          (let [val (or (get table c)
                        (get table :default ::not-found))]
            (.append buffer c)
            (if-not (fn? val)
              (if (#{::not-found :close} val)
                (*unhandled-character-reader* start (.toString buffer))
                (do (pop-chr! r)
                    (recur val buffer)))
              (vector val start)))

          )))))

;; Readers return either:
;; - :nothing
;; - [val start end]
;;
;; :nothing is interpreted to mean that 0 or more characters were
;; consumed but no result was produced.
;;
;; Otherwise the 3-tuple is used to return a result to the parent
;; context.

(declare number-reader
         char-reader)

(defn form-reader [^QueuePosReader r]
  (let [[reader pos] (get-reader r)]
    (if reader
      (reader r pos)
      :nothing)))

(defn read-something [^QueuePosReader r]
  (loop [res (form-reader r)]
    (cond
      (and (= res :nothing)
           (= :eof (peek-chr r)))
      ,,:nothing

      (= res :nothing)
      ,,(recur (form-reader r))

      :else
      ,,res)))

(defn whitespace-reader [^QueuePosReader r ^Position start]
  (.pop r)
  :nothing)

(defn make-list-reader [^Character startc ^Character endc builder]
  (fn [^QueuePosReader r ^Position start]
    {:pre [(qpr? r)
           (not= nil start)
           (= startc (peek-chr r))]}
    (pop-chr! r)
    (binding [*terminators* (cons (vector start startc endc) *terminators*)]
      (loop [acc (ArrayList.)]
        (let [chr (peek-chr r)]
          (cond
            (= chr endc)
            ,,(let [end (get-pos r)]
                (.pop r)
                #_(do (doseq [e *terminators*]
                        (println startc endc "  " e))
                      (println startc endc "  ^d"))
                (vector (builder (.toArray acc)) start end))

            (= :eof chr)
            ,,(*unterminated-form-fn*)

            :else
            ,,(let [res (form-reader r)]
                (when-not (= :nothing res)
                  (.add acc (first res)))
                (recur acc))))))))

(def list-reader
  (make-list-reader \( \) (partial apply list)))

(def vector-reader
  (make-list-reader \[ \] (partial apply vector)))

(def map-reader
  (make-list-reader \{ \} (partial apply hash-map)))

(def set-reader
  (make-list-reader \{ \} (partial apply hash-set)))

(defn comment-reader [^QueuePosReader r ^Position start]
  {:pre [(qpr? r)
         (= \; (peek-chr r))]}
  (pop-chr! r)
  (loop []
    (let [chr (read-chr! r)]
      (case chr
        (\newline \formfeed :eof)
        ,,:nothing

        (recur)))))

(defn meta-reader [^QueuePosReader r ^Position start]
  {:pre [(qpr? r)
         (= \^ (peek-chr r))]}
  (pop-chr! r)
  (let [[me _  _ ]  (read-something r)
        [ve _  end] (read-something r)]
    #_(println "WARNING: meta-expr discards meta!")
    (vector ve start end)))

(defn quote-reader [^QueuePosReader r ^Position start]
  {:pre [(qpr? r)
         (= \' (peek-chr r))]}
  (pop-chr! r)
  (let [[form _ end] (read-something r)]
    (vector (list 'quote form) start end)))

(defn deref-reader [^QueuePosReader r ^Position start]
  {:pre [(qpr? r)
         (= \@ (peek-chr r))]}
  (pop-chr! r)
  (let [[form _ end] (read-something r)]
    (vector (list 'deref form) start end)))

(defn discard-reader [^QueuePosReader r ^Position start]
  {:pre [(qpr? r)
         (= \_ (peek-chr r))]}
  (pop-chr! r)
  (let [[form _ end] (read-something r)]
    :nothing))

(defn symbol-reader [^QueuePosReader r ^Position start]
  {:pre [(qpr? r)]}
  (binding [*reader-dispatch* (as-> *reader-dispatch* d
                                (apply dissoc d "0123456789")
                                (assoc d :eof :close))]
    (let [buff (StringBuilder.)]
      (loop []
        (let [c (peek-chr r)]
          #_(println "symbol-reader" c)
          (cond
            (or (= :eof c)
                (get *reader-dispatch* c))
            ,,[(symbol (.toString buff)) start (get-pos r)]

            :else
            ,,(do (.append buff c)
                  (pop-chr! r)
                  (recur))))))))

(defn keyword-reader [^QueuePosReader r ^Position start]
  {:pre [(qpr? r)
         (= \: (peek-chr r))]}
  (pop-chr! r)
  (let [sym (if (= \: (peek-chr r))
              (do (pop-chr! r)
                  'macro-keyword)
              'keyword)]
    (let [[sym _ end] (symbol-reader r (get-pos r))]
      (vector (list sym sym) start end))))

(defn dispatch-reader [^QueuePosReader r ^Position start]
  {:pre [(qpr? r)]}
  (let [[sym _ _]    (symbol-reader r (get-pos r))
        [body _ end] (read-something r)]
    (vector (list 'dispatch sym body) start end)))

(defn string-reader [^QueuePosReader r ^Position start]
  {:pre [(qpr? r)
         (= \" (peek-chr r))]}
  (pop-chr! r)
  (binding [*terminators* (cons (vector start \" \") *terminators*)]
    (let [buff (StringBuilder.)]
      (loop []
        (let [c (peek-chr r)]
          #_(println (get-pos r) c)
          (case c
            (\")
            ,,(let [end (get-pos r)]
                (pop-chr! r)
                (vector (.toString buff) start end))

            (\\ )
            ,,(let [[chr _ _] (char-reader r nil)]
                (.append buff chr)
                (recur))

            (:eof)
            ,,(*unterminated-form-fn*)

            (do (.append buff c)
                (pop-chr! r)
                (recur))))))))



(def ^:dynamic
  *reader-dispatch*
  "T := Chr \to Either T Fn

  That is, a map of characters recursively to reader functions. For
  example, {\\( list-reader} would be one state of this table. Used by
  form-reader to determine how to parse a given prefix string using
  LL(1) style table dispatch."

  (-> {\(       list-reader
       \)       :close
       \{       map-reader
       \}       :close
       \[       vector-reader
       \]       :close
       \"       string-reader
       \:       keyword-reader
       \'       quote-reader
       \;       comment-reader
       \^       meta-reader
       \@       deref-reader
       \\       char-reader
       \#       {\^       meta-reader
                 \{       set-reader
                 \_       discard-reader
                 :default dispatch-reader}
       :eof     :close
       :default symbol-reader}

      ;; A hat trick for binding a lot of characters in one go
      (rbind "0123456789"
             ,,number-reader

             [\newline \space \tab \backspace \formfeed \return \,]
             ,,whitespace-reader)))

(def ^:dynamic
  *unhandled-character-reader*
  "Function from an unhandled prefix string to a handler function."

  (fn [start prefix]
    (println "Unhandled prefix" (pr-str prefix))))

(def ^:dynamic
  *unterminated-form-fn*
  "Function for generating meaningful errors when a missing terminator
  is found."

  (fn []
    (throw
     (Exception.
      (with-out-str
        (let [[startp startc endc] (first *terminators*)]
          (println "Unterminated form," (str startc endc) "at" (str startp) "in context:"))
        (doseq [[startp startc endc] (rest *terminators*)]
          (println (format "    %s got %s, expecting %s" startp startc endc))))))))

(def ^:dynamic
  *terminators*
  nil)



(defprotocol AReadable
  (as-reader [_]))

(extend-protocol AReadable
  String
  (as-reader [s]
    (->> s
         (StringReader. )
         (QueuePosReader. )))

  File
  (as-reader [f]
    (QueuePosReader. ^File f))

  Reader
  (as-reader [r] r))

(defn read-reader [r]
  {:pre [(reader? r)]}
  (let [res (read-something r)]
    (when-not (= res :nothing)
      (let [[res start end] res
            meta            {:start start :end end}]
        (ox/with-meta res meta)))))

(defn read-file [^File f]
  {:pre [(file? f)]}
  (-> f
      (as-reader)
      (read-reader)))

(defn read-string [^String s]
  {:pre [(string? s)]}
  (-> s
      (as-reader)
      (read-reader)))
