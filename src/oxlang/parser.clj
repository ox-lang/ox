(ns oxlang.parser
  (:refer-clojure :exclude [read-string])
  (:require [clojure.string :as string]
            [clj-antlr.core :as antlr]))

(def -antlr4-parser (antlr/parser "grammar/Oxlang.g4"))

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
 :reader_macro)

(defmethod -transform :n_DecimalIntegerLiteral [[_ x]]
  (Long/parseLong x 10))

(defmethod -transform :n_HexIntegerLiteral [[_ x]]
  (-> x
    (string/replace-first #"0[xX]" "")
    (Long/parseLong 16)))

(defmethod -transform :n_DecimalFloatingPointLiteral [[_ x]]
  (Double/parseDouble x))

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
    (set)))

(defmethod -transform :map [[_ _ & more]]
  (->> more butlast
     (map -transform)
     (cons 'hash-map)))

(defmethod -transform :simple_sym [[_ s]]
  (symbol s))

(defmethod -transform :ns_symbol [[_ ns _ sym]]
  (symbol (name (-transform ns))
          (name (-transform sym))))

(defmethod -transform :default [x] x)

(defn read-string [s]
  (-> s -antlr4-parser second -transform))
