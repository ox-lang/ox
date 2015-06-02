(ns ox.lang.evaluator
  (:refer-clojure :exclude [eval apply macroexpand-1 macroexpand compile vector])
  (:require [clj-tuple :refer [vector]]
            [clojure.java.io :as io]
            [clojure.core.match :refer [match]]
            [ox.lang.environment :as env]
            [ox.lang
             [parser :as parser]
             [util :refer :all]]))

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

(defn analyze
  [eval env form]
  nil)

(defn apply
  [eval env f args]
  nil)

;; FIXME: trampoline? How did I do that before...
(defn interpreting-eval
  [env form]
  (let [e (partial interpreting-eval env)]
    (cond (symbol? form)
          ,,(vector env (env/get-value env form))

          (list? form)
          ,,(case (first form)
              (def*)
              ,,(let [[_ name meta val] form]
                  (vector (env/inter env name meta val) nil))

              (do*)
              ,,nil

              (fn*)
              ,,nil

              (lambda*)
              ,,nil

              (if*)
              ,,nil

              (let*)
              ,,nil

              (letrc*)
              ,,nil

              (list*)
              ,,nil

              (quote)
              ,,nil

              (ns*)
              ,,nil
              
              :else
              ,,(vector env nil))

          ;; FIXME: Clojure has a whole bunch of stuff which behaves like a
          ;; function, keep or chuck? this imp'l chucks.
          :else
          ,,[env form])))

(defn eval-form
  "Analyzes, macroexpands and interprets a single form in the given
  environment, returning a pair [env, result]."
  [eval env form]
  (->> form
       (read-eval eval env)
       (macroexpand eval env)
       (eval env)))
