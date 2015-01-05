(ns ox.lang.evaluator
  (:refer-clojure :exclude [eval macroexpand-1 macroexpand]))

(declare eval)

(defn read-eval-1
  "Implementation detail of read-eval. This form computes the read-eval of a
  form given that it has already been recursively read-eval'd.

  (let* ((env ...))
    (read-eval env '(read-eval FORM))
  => (eval env FORM)

  forms which do not start with read-eval are returned unmodified."
  [env tree]
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
  [env tree]
  (if (list? tree)
    (->> tree
         (map (partial read-eval env))
         (read-eval-1 env))
    tree))



(defn macroexpand-1
  [env tree])

(defn macroexpand
  "Walks a previously read-eval'd tree, expanding macros occuring in the tree
  via macroexpand-1. Support is provided for symbol macros."
  [env tree])
