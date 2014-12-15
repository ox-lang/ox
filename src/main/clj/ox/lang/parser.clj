(ns ox.lang.parser
  (:refer-clojure :exclude [read-string])
  (:require [clojure.string :as string]
            [clojure.core.match :refer [match]]
            [clj-antlr.core :as antlr])
  (:import [ox.lang Util]))

(def -antlr4-parser (antlr/parser "grammar/Oxlang.g4"))

;; FIXME: This entire multimethod would be better served as a single
;; huge explicit pattern match but I need a pattern matching imp'l
;; first before I can do that. Since I'm bootstrapping this core.match
;; is probably in order <3 dnolen

(defmulti -transform first)

(defmethod -transform :file [[_ & more]]
  (map -transform more))

(defmacro punt-on [& xs]
  `(do ~@(for [x xs]
           `(defmethod -transform ~x [[_# x#]]
              (-transform x#)))))

(punt-on
 :form
 :literal
 :lit_symbol
 :n_IntegerLiteral
 :n_FloatingPointLiteral
 :symbol
 :keyword
 :reader_macro
 :character
 :number)

(defmethod -transform :long [[_ x]]
  (Long/parseLong x 10))

(defmethod -transform :bign [[_ x]]
  (let [num (.substring x 0 (dec (count x)))]
    (BigInteger. num 10)))

(defmethod -transform :hex [[_ x]]
  (-> x
    (string/replace-first #"0[xX]" "")
    (Long/parseLong 16)))

(defmethod -transform :float [[_ x]]
  (-> x -transform Double/parseDouble))

(defmethod -transform :boolean [[_ x]]
  (= x "true"))

(defmethod -transform :any_char [[_ ^String s]]
  (.charAt s 1))

(defmethod -transform :u_hex_quad [[_ s]]
  (Util/readUnicodeChar s 2 4 16))

(def -named-char-table
  {"newline"   \newline
   "return"    \return
   "space"     \space
   "tab"       \tab
   "formfeed"  \formfeed
   "backspace" \backspace})

(defmethod -transform :named_char [[_ name]]
  (let [name (.substring name 1)]
    (if-let [entry (find -named-char-table name)]
      (second entry)
      (throw (Exception. (str "Unknown character named " name))))))

;; FIXME: Add string support

;; FIXME: Add pattern support

(defmethod -transform :list [[_ _ & more]]
  (->> more
    (butlast)
    (map -transform)))

(defmethod -transform :vector [[_ _ & more]]
  (->> more
    (butlast)
    (mapv -transform)))

(defmethod -transform :set [[_ _ & more]]
  (->> more
    (butlast)
    (map -transform)
    (cons 'set)
    (list 'read-eval)))

(defmethod -transform :map [[_ _ & more]]
  (->> more butlast
     (map -transform)
     (cons 'hash-map)
     (list 'read-eval)))

(defmethod -transform :simple_sym [[_ s]]
  (symbol s))

(defmethod -transform :ns_symbol [[_ s]]
  (let [[_ ns name] (re-find #"([^/]+)/(.*)" s)]
    (symbol ns name)))

(defmethod -transform :simple_keyword [[_ _ [_ [sym-type & data]]]]
  ;; FIXME: a real match would be ballin' here
  (if (= sym-type :raw_symbol)
    (let [[[_ name]] data]
      (keyword name))

    (let [[[_ ns] _ [_ name]] data]
      (keyword ns name))))

(defmethod -transform :macro_keyword [[_ _ _ [_ [sym-type & data]]]]
  ;; FIXME: a real match would be ballin' here
  (if (= sym-type :raw_symbol)
    (let [[[_ name]] data]
      `(~'read-eval
        (~'keyword
         (~'name (~'this-ns))
         ~name)))

    (let [[s]         data
          [_ ns name] (re-find #"([^/]+)/(.*)" s)]
      `(~'read-eval
        (~'keyword
         (~'name
          (~'resolve-ns-alias
           (~'this-ns)
           ~ns))
         ~name)))))

(defmethod -transform :backtick [[_ _ form]]
  `(~'backtick ~(-transform form)))

(defmethod -transform :unquote_splicing [[_ _ form]]
  `(~'unquote-splicing ~(-transform form)))

(defmethod -transform :unquote [[_ _ form]]
  `(~'unquote ~(-transform form)))

(defmethod -transform :quote [[_ _ form]]
  (list 'quote (-transform form)))

(defmethod -transform :deref [[_ _ target]]
  (list 'deref (-transform target)))

(defmethod -transform :dispatch [[_ _ target value]]
  (let [target (-transform target)
        value  (-transform value)]
    `(~'read-eval
      ((~'resolve-reader-macro
        (~'this-ns)
        (~' quote ~target))
       ~value))))

(defmethod -transform :tag_map [[_ _ map-form target-form]]
  (let [map-form    (-transform map-form)
        target-form (-transform target-form)]
    `(~'read-eval
      (~'with-meta
        (~'quote ~target-form)
        ~map-form))))

(defmethod -transform :tag [[_ _ tag form]]
  (let [tag  (-transform tag)
        form (-transform form)]
    `(~'read-eval
      (~'with-meta
        (~'quote ~form)
        (~'hash-map ~tag true)))))

(defmethod -transform :default [x] x)

(defn parse-string [s]
  (-> s -antlr4-parser second -transform))

;; FIXME: Add a file/resource parser
