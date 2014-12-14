(ns oxlang.parser
  (:refer-clojure :exclude [read-string])
  (:require [clojure.string :as string]
            [clj-antlr.core :as antlr]))

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
 :raw_symbol
 :reader_macro
 :inf
 :nan)

(defmethod -transform :n_DecimalIntegerLiteral [[_ x]]
  (Long/parseLong x 10))

(defmethod -transform :n_HexIntegerLiteral [[_ x]]
  (-> x
    (string/replace-first #"0[xX]" "")
    (Long/parseLong 16)))

(defmethod -transform :n_DecimalFloatingPointLiteral [[_ x]]
  (-> x -transform Double/parseDouble))

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

(defmethod -transform :ns_symbol [[_ ns _ sym]]
  (symbol (name (-transform ns))
          (name (-transform sym))))

(defmethod -transform :lit_keyword [[_ _ [_ [sym-type & data]]]]
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

    (let [[[_ ns] _ [_ name]] data]
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

(defmethod -transform :default [x] x)

(defn read-string [s]
  (-> s -antlr4-parser second -transform))
