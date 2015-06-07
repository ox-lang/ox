(ns ox.lang.environment
  (:require [clojure.java.io :as io]
            [ox.lang.environment.types :as t]
            [clj-tuple :refer [vector]])
  (:refer-clojure :exclude [resolve vector]))

(defn inter
  "λ [Env, Symbol, Value] → Env

  Returns a new environment where the specified symbol is bound to the given
  form value. Used for installing defs into an environment."
  ([env [k v]]
   (inter env k v))

  ([env sym value]
   (inter env sym nil value))

  ([env sym meta value]
   {:pre [(t/ns? env)
          (symbol? sym)]}
   (let [ns   (-> env second :ns)
         _    (assert (symbol? ns))
         qsym (symbol (name ns) (name sym))]
     (-> env second
         (assoc-in [:bindings sym]  (t/->alias qsym))
         (assoc-in [:bindings qsym] (with-meta (t/->value value) meta))
         (#(vector (first env) %))))))

(defn get-entry
  "λ [Env, Symbol] → Binding

  Walks the environment stack, returning the environment entry (not the value)
  packaging the bound value or else throwing an exception."
  [env symbol]
  {:pre [(symbol? symbol)]}
  (when (and env symbol)
    (if-not (t/global? env)
      (or (-> env
              second
              (get :bindings)
              (get symbol))

          (get-entry (-> env second :parent) symbol)

          (-> (str symbol " is not bound in any enclosing scope!")
              (Exception.)
              (throw)))

      (recur (-> env second :parent) symbol))))

(defn resolve
  "λ [Env, Symbol] → Maybe[Symbol]

  Resolves the given symbol in the current environment."
  [env sym]
  (let [entry (get-entry env sym)]
    (if (t/alias? entry)
      (recur env (second entry))
      sym)))

(defn get-value
  "λ [Env, Symbol] → Value

  Returns the value of the given symbol in the specified
  environment."
  [env symbol]
  (let [entry (get-entry env symbol)]
    (if (t/alias? entry)
      (recur env (second entry))
      (second entry))))

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
    (recur (-> env second :parent))))

(defn get-ns
  "λ [Env, Symbol] → Ns

  Walks the binding tree to find the namespace with this name (if any) and if
  found returns that namespace. Otherwise throws an exception."
  [env name]
  (if-let [glob (get-global-env env)]
    (let [nss (-> glob second :namespaces)]
      (if-let [ns (get nss name)]
        ns
        (-> "Could not locate the namespace '%s' in the global environment!"
            (format (str name))
            Exception.
            throw)))
    (-> "Could not locate the global environment!"
        Exception.
        throw)))

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
  (if (t/ns? old)
    (assoc-in old [1 :parent] new)
    (update-in old [1 :parent] rebase new)))



(defn get-meta
  "λ [Env, Symbol] → Map

  Returns the environment metadata associated with a binding, def,
  import or require. Returns the empty map if the symbol has no
  metadata, fails if the symbol is not bound in the given
  environment."
  [env symbol]
  (meta (get-entry env symbol)))

;; FIXME: probably shouldn't use real Clojure metadata here. Adding a metadata
;; part to bindings and defs would probably go over better inthe long run.
(defn alter-meta
  "λ [Env, Symbol, λ[Map] → Map] → Env

  Returns an updated environment where the metadata of the given
  symbol has been altered to equal the argument map."
  [env symbol updater]
  {:pre [(get-entry env symbol)]}
  (let [s        (resolve env symbol)
        bindings (-> env second (get :bindings))]
    (if-let [[k v] (find bindings s)]
      (update-in env [1 :bindings k] #(with-meta % (updater (meta %))))
      (assoc-in  env [1 :parent]      (alter-meta (-> env second :parent)
                                                  symbol updater)))))

(defn set-meta
  "λ [Env, Symbol, Map] → Env

  Returns an updated environment where the metadata of the given
  symbol has been altered to equal the argument map."
  [env symbol meta]
  (alter-meta env symbol (constantly meta)))



(defn push-locals
  "λ [Env, {[Symbol Value]}] → Env

  Pushes local bindings, returning a new environment with the pushed
  local bindings."
  [env bindings]
  (->> (for [[k v] bindings]
         (vector k (t/->value v)))
       (into {})
       (t/->local env)))

(defn dynamic?
  "λ [Env, Symbol] → Bool

  Indicates whether a given symbol's binding is dynamic (and thus rebindable)
  rather than being static."
  [env symbol]
  (->> symbol
       (resolve env)
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
  (->> (for [[k v] bindings]
         (vector k (with-meta (t/->value v)
                     (merge (get-meta env k)
                            (meta v)))))
       (into {})
       (t/->dynamic env)))

(defn pop-bindings
  "λ [Env] → Env

  Pops the last set of pushed bindings whether local, dynamic or otherwise,
  returning the parent environment."
  [env]
  {:pre [(t/env? env)
         (or (t/local? env)
             (t/dynamic? env))]}
  (-> env second :parent))



(defn macro?
  "λ [Env, Symbol] → Bool

  Indicates whether a given symbol is bound to a macro in the given
  environment."
  [env symbol]
  (->> symbol
       (resolve env)
       (get-meta env)
       :macro))
