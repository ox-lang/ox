(ns ox.lang.evaluator
  (:refer-clojure :exclude [eval apply macroexpand-1 macroexpand compile])
  (:require [clojure.java.io :as io]
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

(def mapl (comp list* map))

(defn -analyze-form
  [env form]
  (cond (list? form)
        ,,true
  
        (symbol? form)
        ,,true

        true
        ,,true))

(defn analyze-form
  "Walks the given form qualifying all used globals (defs) and renaming locals
  to an explicit SSA form."
  [env form]
  (trampoline (partial -analyze-form env) form))

(defn eval-form
  "Analyzes, macroexpands and interprets a single form in the given
  environment, returning a pair [env, result]."
  [eval env form]
  (->> form
       (read-eval eval env)
       (macroexpand eval env)
       (eval env)))
