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
  "Implementation detail of read-eval. This form computes the
  read-eval of a form given that it has already been recursively
  read-eval'd.

  (let* ((env ...))
  (read-eval env '(read-eval FORM))
  => (eval env FORM)

  forms which do not start with read-eval are returned unmodified."
  [eval env tree]
  (if (= 'read-eval (first tree))
    (eval env (second tree))
    tree))

(defn read-eval
  "Recursively walks the given tree, read-evaluating children nodes
  first then read-eval-1ing the parent of the evaluated nodes. This
  pass exists so that datastructures like maps, floats and vectors can
  be implemented as part of bootstrap rather than being baked into a
  platform's implementation.

  When reading structures, users are required to read-eval their forms
  before macroexpanding or evaluating them."
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
  "Walks a previously read-eval'd tree, expanding macros occuring in
  the tree via macroexpand-1. Children are expanded first until they
  reach a fixed point, then the parent is expanded until it fixes as
  well."
  [eval env tree]
  (if (list? tree)
    (->> tree
         (map (partial macroexpand eval env))
         (fix (partial macroexpand-1 eval env)))
    tree))

(defn apply
  "λ [λ[Env, Form] → [Env, Form], Env, f & args] → [Env, Form]

  Applies f in the given environment with args, as computed
  interpreted assembled or what have you by the provided eval
  function.

  Returns a new [env getValue] pair."
  [eval env f & args]
  )

(defn eval-all
  [eval env forms]
  nil)

;; FIXME: trampoline? How did I do that before...
;;
;; FIXME: do I need different interpreters for pure and impure code?
;; probably... I don't want say a def in a lambda context to actually work.
(defn interpreting-eval
  [vm env form]
  (cond (symbol? form)
        ,,(vector env (env/get-value env form))

        (list? form)
        ,,(case (first form)
            (def*)
            ,,(let [[_ name meta val] form]
                (vector (env/inter env name meta val) nil))

            (do)
            ,,(loop [env         env
                     *1          nil
                     [f & forms] (rest form)]
                (let [[e' *1 :as res] (interpreting-eval env f)]
                  (if-not (empty? forms)
                    (recur e' *1 forms)
                    res)))

            ;; FIXME: This is bloody hard. I think that the approach
            ;; here is that you iterate across the loop body, using
            ;; interpreting eval as is normal in the other special
            ;; forms.
            ;;
            ;; The problem is how to track the context of being in a
            ;; tail position where recur is legal. Also what should
            ;; recur be capable of? It's been debated as to whether
            ;; recur should be able to cross methods.. all kinds of
            ;; stuff.
            (loop)
            ,,nil

            (recur)
            ,,nil
            
            ;; FIXME: for now, lambda* and fn* do the same thing which is just
            ;; to return the function term unmodified. The interpreting apply
            ;; will do the leg work.
            ;;
            ;; FIXME: lambda* is supposed to be a "pure" context in which you
            ;; can't do IO or defs or whatever. That needs to be enforced.
            (fn* lambda*)
            ,,(vector env form)

            (if*)
            ,,(let [_                  (assert (= 4 (count form)) "if* requires test, rhs and hls!")
                    [_if expr lhs rhs] form
                    [e' *1]            (interpreting-eval env expr)]
                (interpreting-eval e' (if *1 lhs rhs)))

            ;; FIXME: how even do I
            (let*)
            ,,nil

            ;; FIXME: all at once or one at a time? The naive implementation
            ;; is all at once...
            (list*)
            ,,nil

            (quote)
            ,,(do (assert (= 2 (count form)) "quote only quotes one form!")
                  (vector env (second form)))

            (ns*)
            ,,nil

            ;; General case of invocation.
            ;; `(apply (eval (first f)) (map eval (rest f)))`
            ;; or whatever that translates to given all the environment stuff that I have to deal with here.
            (apply vm interpreting-eval))

        ;; FIXME: Clojure has a whole bunch of stuff which behaves like a
        ;; function, keep or chuck? this imp'l chucks.
        :else
        ,,(vector env form)))

(defn eval-form
  "Analyzes, macroexpands and interprets a single form in the given environment,
  returning a pair [env, result]."
  [eval env form]
  (->> form
       (read-eval eval env)
       (macroexpand eval env)
       (eval env)))
