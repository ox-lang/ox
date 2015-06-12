(ns ox.lang.environment.types
  (:refer-clojure :exclude [vector alias])
  (:require [clj-tuple :refer [vector]]
            [guten-tag.core :refer [deftag]]))

;; Types to do with bindings in environments

(deftag binding/alias [x]
  {:pre [(symbol? x)]})

(deftag binding/value [meta x]
  (map? meta))

(deftag binding/special [x]
  (symbol? x))

;; Types to do with environments


(deftag env/base [parent bindings]
  {:pre [(nil? parent)
         (map? bindings)
         (every? symbol? (keys bindings))))]})

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
       (->base)))

(declare ns?)

;;;; global type
;;;;;;;;;;;;;;;;;;;;

(deftag env/global [parent name namespaces]
  {:pre [(map? v)
         (symbol? name)
         (re-find #"(\w+\.?)+"
                  (clojure.core/name name))
         (every? ns? namespaces)
         (base? parent)]})

;;;; ns type
;;;;;;;;;;;;;;;;;;;;

(deftag env/ns [ns imports bindings]
  {:pre [(symbol? ns)
         (map? namespaces)
         (every? symbol? (keys namespaces))
         (every ns?)
         (:ns v)
         (:loaded-namespaces v)
         (:imports v)
         (:bindings v)))]})

;; FIXME: do I need some other non-local context?

;;;; local type
;;;;;;;;;;;;;;;;;;;;

(declare local? env? dynamic?)

(deftag env/local [parent bindings]
  {:pre [(-> v :parent env?)
         (-> v :bindings map?)
         (every? symbol?
                 (keys bindings))
         (every? (comp not namespace)
                 (keys bindings))]})

(deftag env/dynamic [parent bindings]
  {:pre [(env? parent)
         (map? bindings)
         (every? symbol? (keys bindings))
         (every? namespace (keys bindings))]})

;;;; env type
;;;;;;;;;;;;;;;;;;;;

(def env? (some-fn ns? local? base? dynamic?))
