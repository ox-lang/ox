(ns ox.lang.evaluator
  (:refer-clojure :exclude [eval apply macroexpand-1 macroexpand])
  (:require [ox.lang.environment :as env]))

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
           (:macro (env/get-meta (first tree))))
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



(defn interpreting-eval
  "Walks a previously read-eval'd and macroexpand'd form, evaluating it by
  interpretation. Returns a pair [env, result]."
  [env form]
  (let [r (comp second (partial interpreting-eval env))]
    (if (list? form)
      (let [[f & args] form]
        (case f
          ('apply)
          ,,(->> args last r
                 (concat (map r (butlast args)))
                 (cons f)
                 (recur env))

          ('def*)
          ,,(let [[s m v & more] args]
              (assert (symbol? s) "Not a binding symbol!")
              (assert (map? m) "Metadata not a map!")
              (assert (empty? more) "More args to def than expected!")
              [(-> env
                   (env/inter s v)
                   (env/set-meta s v))
               s])
          
          ('do*)
          ,,
          
          ('fn*)
          ('if)
          ('let*)
          ('letrc*)
          ('ns)
          ('quote)
          :else
          )))))
