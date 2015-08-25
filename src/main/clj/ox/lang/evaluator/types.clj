(ns ox.lang.evaluator.types
  (:refer-clojure :exclude [fn? var?])
  (:require [guten-tag.core :refer [deftag]]))

(declare ast?)

(deftag if [meta test lhs rhs]
  {:pre [(ast? test)
         (ast? lhs)
         (ast? rhs)]})

(deftag do [meta forms]
  {:pre [(every? ast? forms)]})

(deftag let [meta bindings body]
  {:pre [(every? ast? bindings)
         (ast? body)]})

(deftag fn [meta bindings body]
  {:pre [(every? ast? bindings)
         (ast? body)]})

(deftag invoke [meta fn args]
  {:pre [(ast? fn)
         (every? ast? args)]})

(deftag quote [meta form]
  {:pre [(ast? form)]})

(deftag var [meta name]
  {:pre [(clojure.core/symbol? name)
         (clojure.core/namespace name)
         (clojure.core/name name)]})

(deftag local [meta name]
  {:pre [(clojure.core/symbol? name)
         (clojure.core/name name)
         (not (clojure.core/namespace name))]})

(deftag def [meta var form]
  {:pre [(var? var)
         (ast? form)]})

;; FIXME: list expression?
;; FIXME: map expression?
;; FIXME: set expression?
;; FIXME: number expression?
;; FIXME: type expression?
;; FIXME: typeclass expression?

(deftag try [meta body cases finally])

(def ast?
  (some-fn nil? if? do? let? fn? invoke? quote? var? local? def?))
