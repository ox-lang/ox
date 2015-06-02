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

(defn update-vals
  [m f & args]
  (->> (for [[k v] m]
         [k (apply f v args)])
       (into {})))

(defn analyze
  [eval env form])

(defn apply
  [eval env f args])

;; FIXME: trampoline? How did I do that before...
(defn interpreting-eval
  [env form]
  (let [e (partial interpreting-eval env)]
    (cond (symbol? form)
          ,,[env (env/get-value env form)]

          (list? form)
          ,,(match [form]
              [(['def* name meta value] :seq)]
              ,,[(env/inter env name meta value) nil]

              ;; FIXME: how are fn* and lambda* distinct?
              ;; FIXME: compile here?
              ;; FIXME: type infer here?
              ;; FIXME: SSA rewrite here?
              [([(:or 'fn* 'lambda*) & forms] :seq)]
              ,,[env form]

              ;; Note that quote only takes one trailing form!
              [(['quote x] :seq)]
              ,,[env x]

              [(['do* & forms] :seq)]
              ,,(loop [env         env
                       [f & forms] forms
                       *1          nil]
                  (if (not (empty? forms))
                    (let [[e' v] (interpreting-eval env f)]
                      (recur e' forms v))
                    [env *1]))

              [(['if* e l r] :seq)]
              ,,(let [[env' x] (interpreting-eval env e)]
                  (if x
                    (interpreting-eval env' l)
                    (interpreting-eval env' r)))

              ;; Note that let* only takes one trailing form!
              ;;
              ;; Note that bindings are an all at once deal! the macro let will
              ;; have to deal with doing data analysis. Maybe. I dunno yet.
              ;;
              ;; Note that we
              ;; return (pop-bindings (first (eval (push-bindings)))). This
              ;; means that nested inter and soforth will work just
              ;; dandy. Whether this is a feature or not is a subject for
              ;; debate. Def under def, or defs in fns are a problem.
              [(['let* bindings form] :seq)]
              ,,(let [[e' r] (interpreting-eval (env/push-locals env (update-vals e bindings)) form)]
                  [(env/pop-bindings e') r])

              ;; FIXME: unsupported! unneeded?
              [(['letrc* bindings form] :seq)]
              ,,[env nil]

              ;; FIXME: unsupported! What does it even do?
              [(['ns* name directives] :seq)]
              ,,[env nil]

              :else
              ,,(apply interpreting-eval env
                       (e (first form))
                       (map e (rest form))))

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
