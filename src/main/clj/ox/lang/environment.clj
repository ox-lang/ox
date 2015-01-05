(ns ox.lang.environment)

(defn env?
  "FIXME: quick and dirty predicate"
  [x]
  (and (vector? x)
       (#{:env/local :env/ns} (first x))))

(defn ns?
  "FIXME: quick and dirty predicate"
  [x]
  (and (env? x)
       (= :env/ns (first x))))

(defn make-environment
  "λ [] → Env

  Returns the empty environment. Analyzing or evaluating any namespace must
  start with the empty environment."
  []
  [:env/ns
   {:parent            nil ; link to parent environment

    :loaded-namespaces {}  ; map from symbols to the definition environment

    :imports           #{} ; set of imported classes

    :bindings          {}  ; map from qualified and unqualified
                           ; symbols to a binding descriptor.
    }])

(defn make-local-environment
  "λ [Env] → Env

  Returns a new local environment with no bindings, having the argument
  environment as a parent."
  [env]
  [:env/local
   {:parent   env
    :bindings {}}])

(defn inter
  "λ [Env, Symbol, Form] → Env

  Returns a new environment where the specified symbol is bound to the given
  form value. Used for installing defs into an environment."
  [env symbol value]
  (assert false "Unimplemented"))

(defn get-meta
  "λ [Env, Symbol] → Map

  Returns the environment metadata associated with a binding, def,
  import or require. Returns the empty map if the symbol has no
  metadata, fails if the symbol is not bound in the given
  environment."
  [env symbol]
  (assert false "Unimplemented"))

(defn push-locals
  "λ [Env, (Seq (Symbol . Form))] → Env

  Pushes local bindings, returning a new environment with the pushed
  local bindings."
  [env symbol form]
  (assert false "Unimplemented"))

(defn pop-locals
  "λ [Env] → Env

  Pops the last set of pushed local bindings, returning the parent environment."
  [env]
  (assert false "Unimplemented"))

(defn macro?
  "λ [Env, Symbol] → Bool

  Indicates whether a given symbol is bound to a macro in the given
  environment."
  [env symbol]
  (assert false "Unimplemented"))
