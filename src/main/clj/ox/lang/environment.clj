(ns ox.lang.environment
  (:refer-clojure :exclude [resolve]))

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

(defn alias?
  "FIXME: quick an dirty predicate"
  [x]
  (and (vector? x)
       (#{:binding/alias} (first x))))

(defn make-environment
  "λ [] → Env

  Returns the empty environment. Analyzing or evaluating any namespace must
  start with the empty environment."
  [ns]
  [:env/ns
   {:ns                ns  ; symbol naming current namespace
    :parent            nil ; link to parent environment

    :loaded-namespaces {}  ; map from symbols to the definition environment

    :imports           #{} ; set of imported classes

    ;; map from qualified and unqualified
    ;; symbols to a binding descriptor.
    :bindings          {'+       (list 'fn* (list (list 'x 'y) (list 'invoke #'clojure.core/+ 'x 'y)))
                        'println (list 'fn* (list (list 'x) (list 'invoke #'clojure.core/println 'x)))
                        'apply   [:binding/special 'apply]
                        'def*    [:binding/special 'def*]
                        'do*     [:binding/special 'do*]
                        'fn*     [:binding/special 'fn*]
                        'if*     [:binding/special 'if*]
                        'let*    [:binding/special 'let*]
                        'letrc*  [:binding/special 'letrc*]
                        'ns      [:binding/alias   'ox.lang.bootstrap/ns]
                        'ns*     [:binding/special 'ns*]
                        'quote   [:binding/special 'quote]
                        'invoke  [:binding/special 'invoke]}}])

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
  )

(defn resolve
  "λ [Env, Symbol] → Maybe[Symbol]

  Resolves the given symbol in the current environment."
  [env sym]
  (let [bindings (:bindings env)]
    (loop [sym sym]
      (let [entry (get bindings sym)]
        (if (alias? entry)
          (recur (second entry))
          sym)))))

(defn get-value
  "λ [Env, Symbol] → Value

  Returns the value of the given symbol in the specified
  environment."
  [env symbol]
  (if-let [val (-> env second (get :bindings) (get symbol))] val
          (if-let [parent (:parent (second env))]
            (get-value parent symbol)
            (assert false (str symbol " is not bound in any enclosing scope!")))))


(defn special?
  "FIXME: quick and dirty predicate"
  [env symbol]
  (if-let [x (get-value env (resolve env symbol))]
    (and (vector? x)
         (#{:binding/special} (first x)))))



(defn get-meta
  "λ [Env, Symbol] → Map

  Returns the environment metadata associated with a binding, def,
  import or require. Returns the empty map if the symbol has no
  metadata, fails if the symbol is not bound in the given
  environment."
  [env symbol]
  (assert false "Unimplemented"))

(defn set-meta
  "λ [Env, Symbol, Map] → Env

  Returns an updated environment where the metadata of the given
  symbol has been altered to equal the argument map."
  [env symbol v]
  (assert false "Unimplemented"))



(defn push-locals
  "λ [Env, {[Symbol Form]}] → Env

  Pushes local bindings, returning a new environment with the pushed
  local bindings."
  [env bindings]
  [:env/locals {:parent env :bindings bindings}])

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
