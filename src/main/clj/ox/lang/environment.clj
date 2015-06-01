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
   {:pre [(t/ns? env)
          (symbol? sym)]}
   (let [ns   (-> env second :ns)
         _    (assert (symbol? ns))
         qsym (symbol (name ns) (name sym))]
     (-> env second
         (assoc-in [:bindings sym]  (t/->alias qsym))
         (assoc-in [:bindings qsym] (t/->value value))
         (#(vector (first env) %))))))

(defn get-entry
  "λ [Env, Symbol] → Maybe[Binding]

  Walks the environment stack, returning the environment entry (not the value)
  packaging the bound value."
  [env symbol]
  {:pre [(symbol? symbol)]}
  (when (and env symbol)
    (or (-> env
            second
            (get :bindings)
            (get symbol))
        
        (get-entry (:parent (second env))
                   symbol)

        (-> (str symbol " is not bound in any enclosing scope!")
            (Exception.)
            (throw)))))

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
         (vector k (t/->value v)))
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
