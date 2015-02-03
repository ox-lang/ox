# Special forms

This is a list of the special forms required for the Green proposal.

## \*empty-env\*

Special symbol evaluating to the "empty" (base) environment. Not
actually empty, contains whatever the implementation needs as far as
the special forms go.

## module

```Clojure
(module *empty-env* ;; implicit
    (def* x nil 1)
    (def* y nil (+ x 5)))
```

Macro that exists in `*empty-env*`. foldrs over its body forms
starting with `*empty-env*` macroexpanding and evaling each form in
the environment result and evaluating to the final composite
environment.

What I think is cool about this approach is because we macroexpand and
eval all "top level" forms normally, function calls can exist at the
top level which means that users can manipulate and introspect the
`ns-env` value as they see fit. So lets say that you wanted to add a
`set-public!` macro that adds `#^{:public true}` to a def simply by
associng the metadata on the def in the module AST.

Since the value of evaling a file is the value of evaling the last
(only) form in the file, evaling a file containing a single module of
this structure (see `load`) will eval to the module environment which
another context can use bits of (see `require`).

## ns*

```clojure
(ns* <namespace>
     docstring ;; Nillable
     attr-map  ;; Nillable
     )
```

Constructs a namespace (compilation context) value with a name,
docstring and metadata attributes, returning that namespace as a
value.

## ns

```Clojure
(module (ns demo) ;; implicit require ox.core
    (defn -main [& args]
      (doseq [x args]
         (println args))))
```

Macro around `ns*` that provides the familiar Clojure creature comforts.

## load*

Operation from a environment and a string naming a file to an
environment. While technically usable at the top level if you want to
evaluate the target as if it were textually within the current
context, the common pattern is

```Clojure
(let* ((p ...)
       (env (load *empty-env* p)))
  ;; do something with the environment value
  ;; return a new env with new bits from the other env
  )
```

Load is expected to be cached by the implementation unless change is
detected in the file specified as the loading target or any of its
dependencies in which case full or selective reloading must occur as
appropriate.

## require*

This is hard. Require needs to be a special form because otherwise how
do you require in anything at all? Implemented atop load as a function
that parses the require DSL and then links stuff out of the "other"
env into the "current" env returning a new current env. Top level as a
result.

## def*

```Clojure
(def* <top-env>   ;; Non-nillable.
      <sym>       ;; Non-nillable.
      <attr-map>  ;; Nillable
      <val>       ;; Nillable
      )
```

Note that the namespace compilation context is an explicit parameter
here. I'll get to that in a minute. Operation from a namespace to an
"updated" namespace with the given symbol rebound to the new `<val>`.

Note that the `<ns-val>` as produced by either `ns*` or `def*` is an
`env` for the purposes of `eval`.

## deftype*

```Clojure
(deftype* <top-env>
          <name>
          (<field-name> <field-name> <field-name> ))
```

The one way to build a "class" or closed record type. As with `def*`
this is an form which goes from an `env` to an `env` and in order to
have any effect must occur in the top level `->`. Note that out of the
box a type will implement no protocols. This is expected and
deliberate. Implementations should provide a `deftype` macro which
allows for protocol names to occur after the members list followed by
complete specifications of the protocol methods. This should
macroexpand to a `deftype*` followed by the appropriate `extend-type*`
forms.

## defprotocol*

```Clojure
(defprotocol* <top-env>
          <name>
          (<method-name> <arglist>)
          (<method-name> <arglist>)
          ...)
```

The one way to define a new protocol. Protocols are singly dispatched
on the type of the first (leftmost) argument. As with the other `def*`
forms goes from an environment to a new environment which has the new
protocol value bound and named in.

## extend-type*

```Clojure
(extend-type* <top-env> <type-name> <proto-name>
          (<proto-method-name> <method-args> <expr>)
          (<proto-method-name> <method-args> <expr>)
          (<proto-method-name> <method-args> <expr>)
          ...)
```

As with the `def` family, goes from a top level environment to a new
environment in which implementations of each protocol method for the
given type are known. Expected to be the target of the `deftype` and
`extend-type` macros.

## eval

```Clojure
(eval <env>
      <form>)
```

Does exactly what you'd expect.

## fn*

```Clojure
(fn* <name> ;; Non-nillable but need not be used
     (<arglist> ;; Non-nillable, may be empty
      <pre-list> ;; Non-nillable, may be empty
      <post-list> ;; Non-nillable, may be empty
      <expr>      ;; The single body form
      )
      ...
      )
```

Evaluates to a function result as you would expect. Note that `recur`
can cross arities and does so with TCO. TCO is expected.

An open question is whether syntactic closures are supported/allowed.

## let*

```Clojure
(let* ( (<sym> <expr>)
        ...
    )
  <expr> ;; The single body form
  )
```

Scheme style let, no implicit do at the special form
level. Implementations are expected to provide a `let` macro which
includes the implicit `do*`. `eval`'s the body in a new local `env`
extending the wrapping `env` (or `ns*` level `env`).

An open question is all at once vs sequential binding computation.

## letrc*

```Clojure
(letrc* ( (<sym> <expr>)
      ...
      )
  <expr> ;; The single body form
  )
```

Again from Scheme, same as `let*` except that bindings are all at once
and may refer to each other.

## if*

```Clojure
(if* <expr>
     <lhs>
     <rhs>)
```

Two armed if, evaluates the left hand side iff `<expr>` evaluates to
`nil` (the empty collection) or `false`. Otherwise evaluates the right
hand arm. Neither arm is nillable. Implementations are expected to
provide `if`, `if-not`, `when` and `when-not` macros where ifs only
cover the two armed cases and when only covers single armed cases. It
shall be a compile error to evaluate a single armed `if*`.

## quote

Doesn't need an explanation.

## eval

```Clojure
(eval <env> <form>)
```

eval[uate] a form in an environment returning some result. Top level
environments may be returned as results. Local environments may not be
returned as results. Needs to be in `*empty-env*` so that `module` can
use it.

## macroexpand

```Clojure
(macroexpand <env> <form>)
```

Expands all macros in the given form via the macros bound in the
specified environment until expansion reaches a fixed point, then
returns the fully expanded form. Must return a form evaluatable by
`eval`. Needs to be in `*empty-env*` so that `module` can use it.

## \*env\*

Evaluates to the current environment in any context. Immutable.
