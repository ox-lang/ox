(ns ox.lang.environment
  (:require [clojure.java.io :as io]
            [clojure.core.match :refer [match]]
            [ox.lang.environment.types :as t]
            [clj-tuple :refer [vector]])
  (:refer-clojure :exclude [resolve vector alias]))

(defn e!
  [& xs]
  (throw (Exception. (apply format xs))))

(declare get-value)

(defn install
  "λ [Env, Symbol, Value] → Env'

  Returns a new environment with the given symbol bound as if by a global def to
  the given value. The value must be a Binding value as from env.types."
  ([env sym value]
   {:pre  [(t/env? env)
           (symbol? sym)
           (t/binding? value)]
    :post [(t/env? %)]}
   (match [(seq env) (namespace sym) (name sym)]

     [([:env/base _] :seq) _ _]
     ,,(e! "Cannot inter into the base environment!")

     [([:env/global _] :seq) nil _]
     ,,(if-let [*ns* (get-value env 'ox.lang.bootstrap/*ns*)]
         (install env (symbol (name *ns*) (name sym)) meta value)
         (e! "Cannot inter into the global environment without a target namespace!"))

     [([:env/global _] :seq) _ nil]
     ,,(e! "Cannot inter into the global environment without a name!")

     [([:env/global _] :seq) ns name]
     ,,(do (try (get-value env (symbol (clojure.core/namespace sym)))
                (catch Exception e
                  (e! "Cannot inter into uninitialized namespace '%s'"
                      (clojure.core/name (clojure.core/namespace sym)))))

           (-> env
               (assoc-in [:bindings sym] value)
               (update-in [:bindings (symbol (namespace sym)) :val :defs] #(conj (set %1) %2) sym)))

     [[(:or :env/local :env/dynamic) _] _ _]
     ,,(update-in env [:parent] install sym value))))

(defn inter
  "λ [Env, Symbol, Value] → Env

  Returns a new environment where the specified symbol is bound to the given
  form value. Used for installing defs into an environment."
  ([env [k v]]
   (inter env k v))

  ([env sym value]
   (inter env sym nil value))

  ([env sym meta value]
   {:pre  [(t/env? env)
           (symbol? sym)
           (namespace sym)
           (name sym)]
    :post [(t/env? %)]}
   (install env sym (t/->value meta value))))

(defn alias
  "λ [Env, Symbol, Symbol] → Env

  Creates an alias from one fully qualified symbol to another, returning a new
  environment with the specified alias installed.

  Note that no checking is done to ensure that aliases are acyclic or of bounded
  depth. This is especially important since the various resolution operations
  impose depth bounds."
  [env l r]
  {:pre  [(t/env? env)
          (symbol? l)
          (namespace l)
          (symbol? r)
          (namespace r)]
   :post [(t/env? %)]}
  (install env l (t/->alias r)))

(defn get-global-env
  "λ [Env] → Global

  Walks the parent environment links to find the global environment, then
  returns that. If the argument cannot be walked to give a global environment,
  returns nil.

  FIXME: nil or asertion error? the API is inconsistent here."
  [env]
  {:pre [(not (t/base? env))]}
  (if (t/global? env)
    env
    (recur (t/env->parent env))))

(defn rebase
  "λ [Env, Env] → Env

  Function from an 'old' and a 'new' environment which rebases the 'old'
  environment atop the 'new' environment. The old environment may be of any
  type, the new environment must be a global environment. Produces a new
  environment with all the same 'local' structure as the 'old' environment and
  the 'global' structure of the 'new' environment.

  This is useful for code loading because it allows for the
  expression (logically) of handing off the 'current' continuation to have new
  code loaded in it, and then continue with a 'new' continuation which has all
  the new state from the loaded code."
  [old new]
  (if (t/global? old)
    (assoc-in  old [:parent] new)
    (update-in old [:parent] rebase new)))

(defn get-binding
  "λ [Env, Symbol] → Binding

  Walks the environment stack, returning the environment entry (not the value)
  packaging the bound value or else throwing an exception. Note that this
  function returns the _first_ binding, not the _final_ binding. It will _not_
  chase aliases. To find the _final_ binding (or the visible root binding) use
  get-entry instead."
  [env symbol]
  {:pre  [(t/env? env)
          (symbol? symbol)]}
  (try
    (or
     ;; Try to get a binding out of the current context
     (get-in env [:bindings symbol])

     ;; Try to get a binding out of the parent context
     (get-binding (t/env->parent env) symbol)

     ;; All else failing die
     (e! "Symbol '%s' is not bound in any enclosing scope!" (str symbol)))

    (catch AssertionError e
      (e! "Symbol '%s' is not bound in any enclosing scope!" (str symbol)))))

(defn get-entry
  "λ [Env, Symbol] → Maybe[Binding]
  λ [Env, Symbol, Depth] → Maybe[Binding]

  Walks the environment stack and any alias bindings encountered up to a
  configurable alias limit (depth 3 by default) returning the root binding."
  ([env sym]
   (get-entry env sym 3))

  ([env sym n]
   (if (zero? n)
     (e! "Binding depth error! Symbol '%s' could not be resolved within specified depth!"
         (str sym))
     (let [b (get-binding env sym)]
       (if (t/alias? b)
         (try (get-entry env (:name b) (dec n))
              (catch Exception e
                (e! "Binding depth error! Symbol '%s' could not be resolved within specified depth!"
                    (str sym))))
         b)))))

(defn resolve
  "λ [Env, Symbol] → Maybe[Symbol]
  λ [Env, Symbol, Depth] → Maybe[Symbol]

  Walks the environment stack and any alias bindings until the root binding
  symbol is identified then returns that symbol. As with get-entry, searches to
  a depth limit before failing (default depth of 3)."
  ([env sym]
   (resolve env sym 3))

  ([env sym n]
   (if (zero? n)
     (e! "Binding depth error! Symbol '%s' could not be resolved within specified depth!"
         (str sym))
     (let [entry (get-binding env sym)]
       (if (t/alias? entry)
         (try (resolve env (:name entry) (dec n))
              (catch Exception e
                (e! "Binding depth error! Symbol '%s' could not be resolved within specified depth!"
                    (str sym))))
         sym)))))

(defn get-value
  "λ [Env, Symbol] → Value
  λ [Env, Symbol, Depth] → Value

  Returns the value of the given symbol in the specified environment. As with
  get-entry takes an optional depth parameter (defaults to 3)."
  ([env symbol]
   (get-value env symbol 3))

  ([env symbol n]
   (let [entry (get-entry env symbol n)]
     (:val entry))))


;; Metadata

(defn get-meta
  "λ [Env, Symbol] → Map

  Returns the environment metadata associated with a binding, def,
  import or require. Returns the empty map if the symbol has no
  metadata, fails if the symbol is not bound in the given
  environment."
  [env symbol]
  (:meta (get-entry env symbol)))

(defn alter-meta
  "λ [Env, Symbol, λ[Map] → Map] → Env

  Returns an updated environment where the metadata of the given
  symbol has been altered to equal the argument map."
  [env symbol updater & args]
  (let [s        (resolve env symbol)
        bindings (:bindings env)]
    (if-let [[k v] (find bindings s)]
      (update-in env [:bindings k]
                 #(assoc % :meta (apply updater (:meta %) args)))

      (update-in env [:parent]
                 #(apply alter-meta % symbol updater args)))))

(defn set-meta
  "λ [Env, Symbol, Map] → Env

  Returns an updated environment where the metadata of the given
  symbol has been altered to equal the argument map."
  [env symbol meta]
  (alter-meta env symbol (constantly meta)))



(defn push-locals
  "λ [Env, {[Symbol Value]}] → Env
  λ [Env, {[Symbol Value]}, Meta] → Env

  Pushes local bindings, returning a new environment with the pushed local
  bindings. If metadata is not supplied, then it defaults to the empty map."
  ([env bindings]
   (push-locals env bindings {}))

  ([env bindings meta]
   {:pre [(t/env? env)
          (every? symbol? (keys bindings))]}
   (as-> bindings v
     (for [[k v] v]
       (vector k (t/->value nil v))) ;; FIXME: should have line information for the form, source expr & c
     (into {} v)
     (t/->local env v nil))))

(defn dynamic?
  "λ [Env, Symbol] → Bool

  Indicates whether a given symbol's binding is dynamic (and thus rebindable)
  rather than being static."
  [env symbol]
  (->> symbol
       (get-meta env)
       :dynamic))

(defn push-dynamics
  "λ [Env, {[Symbol Value]}] → Env

  Pushes dynamic bindings of fully qualified dynamic symbols."
  [env bindings]
  {:pre [(t/env? env)
         (every? symbol?
                 (keys bindings))
         (every? namespace
                 (keys bindings))
         (every? (partial dynamic? env)
                 (keys bindings))]}
  (as-> bindings v
    (for [[k v] v]
      (vector k (t/->value (get-meta env k) v)))
    (into {} v)
    (t/->dynamic env v nil)))

(defn pop-bindings
  "λ [Env] → Env

  Pops the last set of pushed bindings whether local, dynamic or otherwise,
  returning the parent environment."
  [env]
  {:pre [(t/env? env)
         (or (t/local? env)
             (t/dynamic? env))]}
  (t/env->parent env))

(defn macro?
  "λ [Env, Symbol] → Bool

  Indicates whether a given symbol is bound to a macro in the given
  environment."
  [env symbol]
  (->> symbol
       (get-meta env)
       :macro))
