(ns ox.lang.parser
  "This namespace implements a parser for Oxlang, which supports most of the
  Clojure reader forms but does very little datastructure construction and no
  evaluation at read time. This parser is appropriate for use in a bootstrapping
  context where the evaluation of some forms (such as maps, vectors, big
  integers) may not be defined yet.

  parse-string is the only public element in this namespace."
  (:refer-clojure :exclude [read-string])
  (:require [clj-antlr.core :as antlr]
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

(defmethod -transform :default [x] x)

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

(defmethod -transform :hex [[_ x]]
  (-> x
      (string/replace-first #"0[xX]" "")
      (Long/parseLong 16)))

(defmethod -transform :bign [[_ x]]
  (let [num (.substring x 0 (dec (count x)))]
    `(~'parse-big-integer ~num 10)))

(defmethod -transform :rint [[_ s]]
  (let [[_ radix body] (re-find #"([1-9][0-9]*)r(.*)" s)]
    `(~'parse-big-integer ~body ~(-transform [:long radix]))))

(defmethod -transform :float [[_ x]]
  `(~'parse-float
    ~(-transform x)))

(defmethod -transform :boolean [[_ x]]
  (= x "true"))

(defmethod -transform :any_char [[_ ^String s]]
  `(~'parse-character ~s))

(defmethod -transform :u_hex_quad [[_ ^String s]]
  `(~'parse-hex-character ~(.substring 2 4 s)))

(defmethod -transform :named_char [[_ name]]
  (let [name (.substring name 1)]
    `(~'parse-named-character ~name)))

(defmethod -transform :string [[_ quoted-str]]
  `(~'unquote-string ~quoted-str))

(defmethod -transform :regex [[_ _ s]]
  `(~'re-compile
    ~(-transform s)))

(defmethod -transform :list [[_ _ forms]]
  (->> forms -transform))

(defmethod -transform :nil [_]
  nil)

(defmethod -transform :vector [[_ _ forms]]
  (->> forms -transform
       list
       (cons 'vector)))

(defmethod -transform :set [[_ _ forms]]
  (->> (-transform forms)
       (cons 'set)))

(defmethod -transform :map [[_ _ & more]]
  (->> more butlast
       (map -transform)
       (partition 2)
       list
       (cons 'hash-map)))

(defmethod -transform :simple_sym [[_ s]]
  (symbol s))

(defmethod -transform :ns_symbol [[_ s]]
  (let [[_ ns name] (re-find #"([^/]+)/(.*)" s)]
    (symbol ns name)))

(defmethod -transform :simple_keyword [[_ _ [_ [sym-type & data]]]]
  ;; FIXME: a real match would be ballin' here
  (if (= sym-type :simple_sym)
    (let [[name] data]
      (list 'keyword name))

    (let [[s] data
          [_ ns name] (re-find #"([^/]+)/(.*)" s)]
      (list 'qualified-keyword ns name))))

(defmethod -transform :macro_keyword [[_ _ _ [_ [sym-type & data]]]]
  ;; FIXME: a real match would be ballin' here
  (if (= sym-type :simple_sym)
    (let [[name] data]
      `(~'qualified-keyword
        (~'name (~'this-ns))
        ~name))

    (let [[s]         data
          [_ ns name] (re-find #"([^/]+)/(.*)" s)]
      `(~'qualified-keyword
        (~'name
         (~'resolve-ns-alias
          (~'this-ns)
          ~ns))
        ~name))))

(defmethod -transform :backtick [[_ _ form]]
  `(~'backtick ~(-transform form)))

(defmethod -transform :unquote_splicing [[_ _ form]]
  `(~'unquote-splicing ~(-transform form)))

(defmethod -transform :unquote [[_ _ form]]
  `(~'unquote ~(-transform form)))

(defmethod -transform :quote [[_ _ form]]
  `(~'quote ~(-transform form)))

(defmethod -transform :deref [[_ _ target]]
  `(~'deref ~(-transform target)))

(defmethod -transform :host_expr [[_ _ cond value]]
  (let [cond  (-transform cond)
        value (-transform value)]
    `(~'when (~'eval-feature-expr '~cond)
       ~value)))

(defmethod -transform :meta_data [[_ _ map-form target-form]]
  (let [map-form    (-transform map-form)
        target-form (-transform target-form)]
    `(~'let* ((res# (~'quote ~target-form)))
             (~'with-meta res#
               (~'merge (~'meta res#)
                        ~map-form)))))

(defmethod -transform :tag [[_ _ tag form]]
  (let [tag  (-transform tag)
        form (-transform form)]
    `(~'let* ((res# (~'quote ~form)))
             (~'with-meta res#
               (~'merge (~'meta res#)
                        (~'hash-map ((~tag true))))))))

(defmethod -transform :record [[_ _ tag map _]]
  `(~'record
    ~(-transform tag)
    ~(-transform map)))

(defmethod -transform :pair [[_ l forms r]]
  (list 'parse-pair (list 'tuple l r)
        (-transform forms)))

(defn parse-string
  "Oxlang single-form parser for reading from strings.

  Will construct:
  - Symbols
  - Lists
  - Integers
  - Booleans

  Will generate constructor forms for all other types including:
  - Big Integers
  - Characters
  - Strings
  - Regular expressions
  - Maps
  - Vectors
  - Keywords
  - Sets"
  [s]
  (-> s
      -antlr4-parser
      second
      -transform))

(defn parse-file
  "Oxlang multiple form parser for reading from files."
  [s]
  (->> s
       -antlr4-parser
       rest
       (map -transform)))

;; FIXME: Add a file/resource parser
