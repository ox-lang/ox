(ns ox.lang.environment.types
  (:refer-clojure :exclude [vector alias ns])
  (:require [clj-tuple :refer [vector]]
            [guten-tag.core :refer [deftag]]))

;; Types to do with bindings in environments

(deftag binding/alias [name]
  {:pre [(symbol? name)]})

(deftag binding/value [meta val]
  (map? meta))

(deftag binding/special [name]
  (symbol? name))

;; Types to do with environments


(deftag env/base [bindings]
  {:pre [(map? bindings)
         (every? symbol? (keys bindings))]})

(def base-env
  (->base {'def*    (->special 'def*)
           'do*     (->special 'do*)
           'fn*     (->special 'fn*)
           'lambda* (->special 'lambda*)
           'if*     (->special 'if*)
           'let*    (->special 'let*)
           'list*   (->special 'list*)
           'letrc*  (->special 'letrc*)
           'quote   (->special 'quote)
           'ns*     (->special 'ns*)
           'ns      (->alias 'ox.lang.bootstrap/ns)}))

(declare ns? env?)

;;;; global type
;;;;;;;;;;;;;;;;;;;;

(deftag env/global [parent namespaces]
  {:pre [(base? parent)
         (every? ns? namespaces)
         (base? parent)]})

;;;; ns type
;;;;;;;;;;;;;;;;;;;;

(deftag env/ns [parent name imports bindings]
  {:pre [(env? parent)
         (map? bindings)
         (symbol? name)
         (not (namespace name))
         (every? symbol? (keys bindings))
         (every? ns? (vals bindings))]})

;; FIXME: do I need some other non-local context?

;;;; local type
;;;;;;;;;;;;;;;;;;;;

(declare local? env? dynamic?)

(deftag env/local [parent bindings]
  {:pre [(env? parent)
         (map? bindings)
         (every? symbol? (keys bindings))
         (every? (comp not namespace) (keys bindings))]})

(deftag env/dynamic [parent bindings]
  {:pre [(env? parent)
         (map? bindings)
         (every? symbol? (keys bindings))
         (every? namespace (keys bindings))]})

;;;; env type
;;;;;;;;;;;;;;;;;;;;

(def env?
  (some-fn base? global? ns? local? dynamic?))

;;;; empty environments
;;;;;;;;;;;;;;;;;;;;

(def empty-global
  (->global base-env {}))

(def empty-user
  (->ns empty-global 'user {} {}))
