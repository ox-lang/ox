(ns ox.lang.environment.types
  (:refer-clojure :exclude [vector alias ns])
  (:require [clj-tuple :refer [vector]]
            [guten-tag.core :refer [deftag]]))

;; Types to do with bindings in environments

(deftag binding/alias [name]
  {:pre [(symbol? name)]})

(deftag binding/value [meta val])

(deftag binding/special [name]
  (symbol? name))

(def binding?
  (some-fn alias?
           value?
           special?))

;; Types to do with environments


(deftag env/base [bindings]
  {:pre [(map? bindings)
         (every? symbol? (keys bindings))]})

(declare ns? env?)

;;;; global type
;;;;;;;;;;;;;;;;;;;;

(deftag env/global [parent bindings meta]
  {:pre [(base? parent)
         (map? bindings)
         (every? symbol? (keys bindings))]})

;;;; ns type
;;;;;;;;;;;;;;;;;;;;

(deftag env/ns [name imports defs meta]
  {:pre [(symbol? name)
         (not (namespace name))
         (every? symbol? imports)
         (every? symbol? defs)]})

;; FIXME: do I need some other non-local context?

;;;; local type
;;;;;;;;;;;;;;;;;;;;

(declare local? env? dynamic?)

(deftag env/local [parent bindings meta]
  {:pre [(env? parent)
         (map? bindings)
         (every? symbol?
                 (keys bindings))
         (every? (comp not namespace)
                 (keys bindings))]})

(deftag env/dynamic [parent bindings meta]
  {:pre [(env? parent)
         (map? bindings)
         (every? symbol?
                 (keys bindings))
         (every? namespace
                 (keys bindings))]})

;;;; env type
;;;;;;;;;;;;;;;;;;;;

(def env?
  (some-fn base?
           global?
           ns?
           local?
           dynamic?))

;;;; empty environments
;;;;;;;;;;;;;;;;;;;;

(def empty-base
  (->base {}))

(def empty-env
  (->global empty-base {} nil))

(def bootstrap-env
  (let [base-binds   {'ox.lang.bootstrap/def*    (->special 'ox.lang.bootstrap/def*)
                      'ox.lang.bootstrap/do*     (->special 'ox.lang.bootstrap/do*)
                      'ox.lang.bootstrap/fn*     (->special 'ox.lang.bootstrap/fn*)
                      'ox.lang.bootstrap/lambda* (->special 'ox.lang.bootstrap/lambda*)
                      'ox.lang.bootstrap/if*     (->special 'ox.lang.bootstrap/if*)
                      'ox.lang.bootstrap/let*    (->special 'ox.lang.bootstrap/let*)
                      'ox.lang.bootstrap/list*   (->special 'ox.lang.bootstrap/list*)
                      'ox.lang.bootstrap/letrc*  (->special 'ox.lang.bootstrap/letrc*)
                      'ox.lang.bootstrap/quote   (->special 'ox.lang.bootstrap/quote)
                      'ox.lang.bootstrap/ns*     (->special 'ox.lang.bootstrap/ns*)
                      'ox.lang.bootstrap/*ns*    (->value {:dynamic true} 'user)}

        user-ns      (->ns 'user #{} #{} nil)
        bootstrap-ns (->ns 'ox.lang.bootstrap #{} (set (keys base-binds)) nil)

        base         (->base base-binds)
        global       (->global base {} nil)]
    (-> global
        (assoc-in [:bindings 'ox.lang.bootstrap] (->value nil bootstrap-ns))
        (assoc-in [:bindings 'user] (->value nil user-ns)))))
