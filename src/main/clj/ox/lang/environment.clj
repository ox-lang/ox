(ns ox.lang.environment
  (:require [clojure.java.io :as io]
            [ox.lang.environment.types :as t])
  (:refer-clojure :exclude [resolve]))

(defn inter
  "λ [Env, Symbol, Form] → Env

  Returns a new environment where the specified symbol is bound to the given
  form value. Used for installing defs into an environment."
  [env sym value]
  {:pre [(t/ns? env)
         (symbol? sym)]}
  (let [ns   (-> env :ns)
        qsym (symbol (name ns) (name sym))]
    (-> env
        (assoc-in [:bindings sym]  ^:no-export [:binding/alias qsym])
        (assoc-in [:bindings qsym] [:binding/value value]))))

(defn get-entry
  [env symbol]
  (if-let [val (-> env second (get :bindings) (get symbol))] val
          (if-let [parent (:parent (second env))]
            (get-entry parent symbol)
            (assert false (str symbol " is not bound in any enclosing scope!")))))

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
    (assert (t/value? entry))
    (second entry)))

(defn special?
  "FIXME: quick and dirty predicate"
  [env symbol]
  (let [x (->> symbol
               (resolve env)   ;; must return a valid new symbol or fail
               (get-entry env))]
    (boolean
     (and (vector? x)
          (#{:binding/special} (first x))))))



(defn get-meta
  "λ [Env, Symbol] → Map

  Returns the environment metadata associated with a binding, def,
  import or require. Returns the empty map if the symbol has no
  metadata, fails if the symbol is not bound in the given
  environment."
  [env symbol]
  (meta (get-value env symbol)))

(defn alter-meta
  "λ [Env, Symbol, Map] → Env

  Returns an updated environment where the metadata of the given
  symbol has been altered to equal the argument map."
  [env symbol updater]
  {:pre [(do (get-entry env symbol) true)]}
  (let [s        (resolve env symbol)
        bindings (-> env second (get :bindings))]
    (if-let [[k v] (find bindings s)]
      (update-in env [1 :bindings s] #(with-meta v (updater (meta v))))
      (assoc-in env [1 :parent]      (alter-meta (-> env second :parent)
                                                 symbol updater)))))

(defn set-meta
  "λ [Env, Symbol, Map] → Env

  Returns an updated environment where the metadata of the given
  symbol has been altered to equal the argument map."
  [env symbol meta]
  (alter-meta env symbol (constantly meta)))



(defn push-locals
  "λ [Env, {[Symbol Form]}] → Env

  Pushes local bindings, returning a new environment with the pushed
  local bindings."
  [env bindings]
  [:env/locals {:parent env
                :bindings
                (->> (for [[k v] bindings]
                       [k [:binding/value v]])
                     (into {}))}])

(defn pop-locals
  "λ [Env] → Env

  Pops the last set of pushed local bindings, returning the parent environment."
  [env]
  {:pre [(vector? env)
         (= :env/local (first env))]}
  (-> env second :parent))



(defn macro?
  "λ [Env, Symbol] → Bool

  Indicates whether a given symbol is bound to a macro in the given
  environment."
  [env symbol]
  (:macro (get-meta env (resolve env symbol))))
