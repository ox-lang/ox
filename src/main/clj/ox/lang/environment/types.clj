(ns ox.lang.environment.types
  (:require [ox.lang.types :as t]))

(defmacro def-tag-check [name tag & preds]
  `(defn ~name [~'x]
     (and (vector? ~'x)
          (= ~tag (first ~'x))
          ~@(for [p preds] (list p 'x)))))

;; Types to do with environments


(def base-env
  [:env/base
   {:bindings
    {'def*    [:binding/special 'def*]
     'do*     [:binding/special 'do*]
     'fn*     [:binding/special 'fn*]
     'lambda* [:binding/special 'lambda*]
     'if*     [:binding/special 'if*]
     'let*    [:binding/special 'let*]
     'list*   [:binding/special 'list*]
     'letrc*  [:binding/special 'letrc*]
     'quote   [:binding/special 'quote]
     'ns*     [:binding/special 'ns*]
     'ns      [:binding/alias   'ox.lang.bootstrap/ns]}}])

(def-tag-check base? :env/base
  (comp map? second)
  (comp map? :bindings second))

;;;; ns type
;;;;;;;;;;;;;;;;;;;;

(def-tag-check ns? :env/ns
  (comp map? second)
  (comp :ns  second)
  (comp :loaded-namespaces second)
  (comp :imports second)
  (comp :bindings second))

(defn ->ns
  "λ [ns] → Env

  Returns the empty environment. Analyzing or evaluating any namespace must
  start with the empty environment."
  [ns]
  {:pre [(t/ns? ns)]}
  [:env/ns
   {:ns                ns            ; symbol naming current namespace
    :parent            base-env      ; link to parent environment

    :loaded-namespaces {}            ; map from symbols to the definition environment

    :imports           #{}           ; set of imported classes

    ;; map from qualified and unqualified
    ;; symbols to a binding descriptor.
    :bindings          {}}])

;; FIXME: do I need some other non-local context?

;;;; local type
;;;;;;;;;;;;;;;;;;;;

(declare local? env?)

(def-tag-check local? :env/local
  (comp env?  :parent second)
  (comp map? :bindings second))

(defn ->local
  "λ [Env] → Env

  Returns a new local environment with no bindings, having the argument
  environment as a parent."
  [parent]
  {:pre [(or (local? parent)
             (ns? parent))]}
  [:env/local
   {:parent   parent
    :bindings {}}])

;;;; env type
;;;;;;;;;;;;;;;;;;;;

(def env? (some-fn ns? local? base?))

;; Types to do with bindings in environments

(defn alias?
  "FIXME: quick an dirty predicate"
  [x]
  (and (vector? x)
       (#{:binding/alias} (first x))))

(defn value?
  "FIXME: quick and dirty predicate"
  [x]
  (and (vector? x)
       (#{:binding/value} (first x))))
