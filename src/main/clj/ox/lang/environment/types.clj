(ns ox.lang.environment.types
  (:require [clj-tuple :refer [vector]])
  (:refer-clojure :exclude [vector]))

(defmacro def-tag-check [name tag & tests]
  `(defn ~name [~'x]
     (and (vector? ~'x)
          (= ~tag (first ~'x))
          (let [~'% ~'x]
            ~@(or tests '(true))))))

;; Types to do with bindings in environments

(def-tag-check alias? :binding/alias
  (let [v (second %)]
    (and (symbol? v)
         (namespace v))))

(defn ->alias
  [x]
  {:pre  [(symbol? x)]
   :post [(alias? %)]}
  (vector :binding/alias x))

(def-tag-check value? :binding/value)

(defn ->value
  [x]
  {:post [(value? %)]}
  (vector :binding/value x))

(def-tag-check special? :binding/special
  (-> % second symbol?))

(defn ->special
  [x]
  {:post [(special? %)]}
  (vector :binding/special x))


;; Types to do with environments


(def base-env
  (->> {:bindings
        {'def*    (->special 'def*)
         'do*     (->special 'do*)
         'fn*     (->special 'fn*)
         'lambda* (->special 'lambda*)
         'if*     (->special 'if*)
         'let*    (->special 'let*)
         'list*   (->special 'list*)
         'letrc*  (->special 'letrc*)
         'quote   (->special 'quote)
         'ns*     (->special 'ns*)
         'ns      (->alias 'ox.lang.bootstrap/ns)}}
       (vector :env/base)))

(def-tag-check base? :env/base
  (-> % second map?)
  (-> % second :bindings  map?))

;;;; ns type
;;;;;;;;;;;;;;;;;;;;

(def-tag-check ns? :env/ns
  (let [v (second %)]
    (and (map? v)
         (:ns v)
         (:loaded-namespaces v)
         (:imports v)
         (:bindings v))))

(defn ->ns
  "λ [ns] → Env

  Returns the empty environment. Analyzing or evaluating any namespace must
  start with the empty environment."
  [ns]
  {:pre  [(symbol? ns)]
   :post [(ns? %)]}
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

(declare local? env? dynamic?)

(def-tag-check local? :env/local
  (let [v        (second %)
        bindings (:bindings v)]
    (and (-> v :parent env?)
         (-> v :bindings map?)
         (every? symbol?
                 (keys bindings))
         (every? (comp not namespace)
                 (keys bindings)))))

(defn ->local
  "λ [Env] → Env
  λ [Env, Bindings] → Env

  Returns a new local environment with no bindings, having the argument
  environment as a parent or with the specified legitimate bindings."
  ([parent]
   (->local parent {}))
  ([parent bindings]
   {:pre  [(or (local? parent)
               (dynamic? parent)
               (ns? parent))
           (every? symbol?
                   (keys bindings))
           (every? (comp not namespace)
                   (keys bindings))]
    :post [(local? %)]}
   [:env/local
    {:parent   parent
     :bindings bindings}]))

(def-tag-check dynamic? :env/dynamic
  (let [v                         (second %)
        {:keys [parent bindings]} v]
    (and (env? parent)
         (map? bindings)
         (every? symbol? (keys bindings))
         (every? namespace (keys bindings)))))

(defn ->dynamic
  "λ [Env] → Env
  λ [Env, Bindings] → Env

  Returns a new environment stack entry containing local bindings intended for
  binding dynamic vars. That bound vars are dynamic is not validated here."
  ([env]
   (->dynamic env {}))
  ([env bindings]
   {:pre [(every? symbol? (keys bindings))
          (every? namespace (keys bindings))]
    :post [(dynamic? %)]}
   [:env/dynamic
    {:parent   env
     :bindings bindings}]))

;;;; env type
;;;;;;;;;;;;;;;;;;;;

(def env? (some-fn ns? local? base? dynamic?))
