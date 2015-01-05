(ns ox.lang.parser
  (:refer-clojure :exclude [read-string])
  (:require [clj-antlr.core :as antlr]
            [clojure.core.match :refer [match]]
            [clojure.java.io :as io]
            [clojure.string :as string])
  (:import [ox.lang Util]))

(def -antlr4-parser
  (-> "grammar/Oxlang.g4"
      io/resource
      slurp
      antlr/parser))

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
 :symbol
 :keyword
 :reader_macro
 :character
 :number)

(defmethod -transform :forms [[_ & more]]
  (map -transform more))

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

(defmethod -transform :rint [[_ s]]
  (let [[_ radix body] (re-find #"([1-9][0-9]*)r(.*)" s)]
    (BigInteger. body (Long/parseLong radix 10))))

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

(defmethod -transform :list [[_ _ forms]]
  (->> forms -transform))

(defmethod -transform :vector [[_ _ forms]]
  (->> forms -transform vec))

(defmethod -transform :set [[_ _ forms]]
  (->> (-transform forms)
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
  (if (= sym-type :simple_sym)
    (let [[name] data]
      (keyword name))

    (let [[s] data
          [_ ns name] (re-find #"([^/]+)/(.*)" s)]
      (keyword ns name))))

(defmethod -transform :macro_keyword [[_ _ _ [_ [sym-type & data]]]]
  ;; FIXME: a real match would be ballin' here
  (if (= sym-type :simple_sym)
    (let [[name] data]
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
  (list 'backtick (-transform form)))

(defmethod -transform :unquote_splicing [[_ _ form]]
  (list 'unquote-splicing (-transform form)))

(defmethod -transform :unquote [[_ _ form]]
  (list 'unquote (-transform form)))

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

(defmethod -transform :host_expr [[_ _ cond value]]
  (let [cond  (-transform cond)
        value (-transform value)]
    `(~'read-eval
      (~'when (~'eval-feature-expr '~cond)
        ~value))))

(defmethod -transform :meta_data [[_ _ map-form target-form]]
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
