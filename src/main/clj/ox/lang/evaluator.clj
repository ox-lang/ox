(ns ox.lang.evaluator
  (:refer-clojure :exclude [eval apply macroexpand-1 macroexpand compile])
  (:require [clojure.java.io :as io]
            [clojure.core.match :refer [match]]
            [ox.lang.environment :as env]
            [ox.lang.parser :as parser]))

(declare eval)

(defn read-eval-1
  "Implementation detail of read-eval. This form computes the read-eval of a
  form given that it has already been recursively read-eval'd.

  (let* ((env ...))
  (read-eval env '(read-eval FORM))
  => (eval env FORM)

  forms which do not start with read-eval are returned unmodified."
  [eval env tree]
  (if (= 'read-eval (first tree))
    (eval env (second tree))
    tree))

(defn read-eval
  "Recursively walks the given tree, read-evaluating children nodes first then
  read-eval-1ing the parent of the evaluated nodes. This pass exists so that
  datastructures like maps, floats and vectors can be implemented as part of
  bootstrap rather than being baked into a platform's implementation.

  When reading structures, users are required to read-eval their forms before
  macroexpanding or evaluating them."
  [eval env tree]
  (if (list? tree)
    (->> tree
         (map (partial read-eval eval env))
         (read-eval-1 eval env))
    tree))



(defn macroexpand-1
  [eval env tree]
  (if (and (list? tree)
           (symbol? (first tree))
           (true? (env/macro? env (first tree))))
    (eval env tree)
    tree))

(defn fix [f x]
  (loop [x x]
    (let [x' (f x)]
      (if-not (= x x')
        (recur x')
        x))))

(defn macroexpand
  "Walks a previously read-eval'd tree, expanding macros occuring in the tree
  via macroexpand-1. Children are expanded first until they reach a fixed point,
  then the parent is expanded until it fixes as well."
  [eval env tree]
  (if (list? tree)
    (->> tree
         (map (partial macroexpand eval env))
         (fix (partial macroexpand-1 eval env)))
    tree))

(defn apply
  "λ [λ[Env, Form] → [Env, Form], Env, f & args] → [Env, Form]

  Applies f in the given environment with args, as computed interpreted
  assembled or what have you by the provided eval function.

  Returns a new [env value] pair."
  [eval env f & args]
  )

(defn interpreting-eval
  [env form]
  (let [*e (partial interpreting-eval env)
        *a (partial apply env)])
  (cond (list? form)
        ,,(match [form]
            ;; FIXME: is there a better way to handle special forms
            ;; than hardcoding them here? multimethod maybe? Idunno.
            
            ;; FIXME: case of def*
            ;; FIXME: case of do*
            ;; FIXME: case of fn*
            ;; FIXME: case of lambda*
            ;; FIXME: case of if*
            ;; FIXME: case of let*
            ;; FIXME: case of list*
            ;; FIXME: case of letrc*
            ;; FIXME: case of quote
            ;; FIXME: case of ns*

            :else
            ,,[env nil])

        (symbol? form)
        ,,[env (env/get-value env form)]

        :else
        ,,[env form])
  )

(defn eval-form
  "Analyzes, macroexpands and interprets a single form in the given
  environment, returning a pair [env, result]."
  [eval env form]
  (->> form
       (read-eval eval env)
       (macroexpand eval env)
       (eval env)))
