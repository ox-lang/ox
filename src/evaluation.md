# Oxlang evaluation

This document seeks to describe both the rationale and mechanisms of
macro expansion and evaluation in the Oxlang environment in contrast
to the same mechanisms as implemented elsewhere.

## Background on Clojure

Clojure's approach to immutable datastructures is a programming model
which I find attractive and elegant. Unfortunately, the reference
Clojure implementation does not practice what it preaches especially
with regards to the use of mutable state to implement environments and
binding scopes in Clojure.

Mike Anderson's language [Kiss](https://github.com/mikera/kiss) is a
step in an interesting direction: rendering bindings static and first
class datastructures which can be manipulated from within Clojure
without requiring the implementation specific host interop which the
reference Clojure implementation provides.

Consider the following program:

```Clojure
(in-ns 'scratch)                       ;; special form which names a new empty environment

(require '[clojure/core :refer :all])  ;; loads in the clojure core & creates aliases

(def foo 3)                            ;; creates the binding scratch/foo, alias foo

(defn -main []                         ;; creates a "main" function using foo
  (println foo))
```

The Clojure compiler will sequentially read each one of these forms,
wrap the form in a function of no arguments which is then compiled and
invoked for side-effects on a mutable object representing the
`scratch` namespace. This is why Clojure doesn't support arbitrary
dependency graphs between functions without forwards declarations: all
directly used declarations must exist at single form compilation time
because all symbols must be resolved either to host interop, local
bindings or namespace bindings, the last of which requires that vars
binding the targeted symbols be interred, this being possible normally
only via a def form.

Particularly, Clojure uses atomically transactional "var" objects to
store and retreive bindings from textual symbols to their def'd
values. A "namespace" is really just a named set of mappings from
aliases including prefixes and renamings to vars which the compiler
uses when evaluating a form.

However, vars also have the property of being dereferenced only at
runtime not at compile time and of being dynamically rebindable. This
makes it possible for Clojure programmers to selectively and locally
alter or replace some definition or value anywhere else in the program
within a local scope. An example of this is the following...

```Clojure
(defn -main []
  (with-local-vars [x 3]
    (println @x)
	(with-bindings {x 4}
	  (println @x))
    (println @x)))
```

This example demonstrates that when used as values, Vars can be
locally rebound. While in this example I only rebound a temporary
unnamed var, the same technique applies to arbitrary symbols. Using
`with-bindings` to redefine vars in `clojure.core` is semantically
valid for instance. In a statically compiled and checked language,
declarations (defs) should be final and declarative rather than
imperative directly producing mutation. Consequently defs should be
defined as functions from a concrete environment to a concrete
environment rather than being imperative.

## On "fexprs" and the "vau calculus"

While some Lisp dialects have explored $vau and fexprs, and while
these techniques lead to interesting properties in terms of programs
ability to provide extensible selective evalauation fexprs and the
proposed vau calculus are fundimentally engines of dynamic binding
allowing for the dynamic redefinition of arbitrary symbols much in the
Clojure style and presuming the presence of an "eval" operation at
program runtime. While this has benifits for metaprogramming and
dynamic code generation, it comes at the cost of all ability to reason
statically about program behavior.

## Oxlang - Ns

In clojure, the namespace form mutates the `*ns*` dynamic value via
the `in-ns` special form and then uses more side-effects on the
mutable namespace object bound to `*ns*` to achieve local aliasing of
defs in "library" code. While an effective implementation, it is an
implementation not a model for namespaces and loading, and taken as a
model it is not an especially good or "pure" one as it relies entirely
upon side-effects.

In Oxlang, files are read form by form, and loading is defined to be

```
(defn load [resource]
  (reduce eval
    (make-empty-env)
	(read-all-forms resource)))
```

This is an operation from a "resource" to an "environment"
representing that loaded resource, which may be cached as an
implementation detail.

Environments are structures having a name (symbol), dependencies
(environments) and exports (symbol/value bindings).

In this context, the `load` form is used to _purely_ get the
environment represented by some artifact, and the `refer` function is
then an operation from two environments and a referral selector to an
environment which represents the first environment with added
dependencies.

Defs are then operations from a binding (symbol), a value, and an
environment to a "new" environment with the added binding.

This model greatly simplifies form analysis, as at every step of this
environment update sequence the environment either has all the data to
fully resolve used symbols or the form being analyzed is in
error.

Furthermore as the analysis of forms is independent of their
evaluation, such a system can be parallel on single form compilation
as the value of previously loaded forms need not be fully resolved
save in the case of macros. The "core" loading operation need only
construct the next environment state being the addition of a binding
to the environment or finalization of an environment. Only in the case
of macroexpanding a subsequent form are the values of previous forms
required in which case such an engine is at worst no faster than a
traditional sequential compiler/evaluator.

This style does require some "magic", as the `do` form at the top
level must become a macro expanding to `->` composition of its forms
in order to retain the `(λ env) -> env` status of other top level
entities. Also the "empty environment" in which new namespaces are
initialized cannot be truly empty as the machinery for requiring other
dependencies from other namespaces must be loaded "magically". This
means that all user namespaces must being "empty" with the language
core as a dependency and the "ns" form of the language core mapped
into the "current" namespace.

## Oxlang - Macros

Macros are ultimately a compiler bootstrapping tool. Macros enable
programmers to write syntactic transformations and "embedded
compilers" which can rewrite data read by the reader into legitimate
forms which may be executed by eval possibly an implicit
compiler. Macros enable a language to be written in terms of a number
of "primitive" forms (traditionally "lambda", "if", "def" and a
handful of others), in terms of which all other computations can be
written. Other computations, written in terms of macros and functions,
then by way of macros expand down to some simple and fixed
determination of these "primitive" or "special" forms which a compiler
or other evaluator can the process into machine code.

In an imperative, sequentially evaluated language (a traditional Lisp,
including Clojure), macros or "syntax transforms" are defined and then
are applied prior to the evaluation of forms in which they occur. This
means that in a language such as Clojure where concrete definitions
must precede uses, macro writers can only rely on the state of the
program as of when the macro was defined. This means that really while
macros may be implemented in terms of user defined code, they exist in
an independent "phase" of the evaluator with respect to user code.

This definition/use enforced ordering means that, when a macro is
used, all the code defining the behavior of the macro must have
previously been loaded. Consequently, the an evaluator/compiler's
state represents all the requisite information to _interpret_ the
macro in question.

This is how macros behave in Oxlang. Macros may be defined in terms of
the full set of functions and datastructures previously defined in a
program, however as Oxlang is designed to target static compilation,
as with ClojureScript, macros do not exist or execute in the same
"runtime context" of a program although they may leverage all the same
functions and datastructures as the runtime state of the program may.

An example of this limiation in action: Consider an imperative program
in which runtime state, say a Unix environment variable, is captured
into a def either via mutation or by a sequentially loading,
non-declarative evaluator which allows the binding of "runtime" values
to defs due to the nature of "load time". A macro, applied to a
subsequently loaded form in a "runtime load time" lisp such as Clojure
could make use of the load time defined value to determine
macroexpansion behavior. Say the macro used load time to detect the
platform name and specialize itself to Linux differently than Windows,
BSD or Darwin.

As defs should be declarative rather than explicitly imperative, their
use for imperative load order effects is arguably an abuse of language
implementation details and an antipattern which should not be
supported. Consequently, this "limitation" is seen as enforcing better
state management practices and software architecture rather than being
a disfiguring loss to the programming model.

An example of Oxlang style macros in action would be the following:

```Clojure
(ns macro-demo) ;; creates a new environment, with the Oxlang stdlib brought in

(defn foo [x]
  (+ x 1))

(defmacro foo-macro [x]
  {:pre [(number? x)]}
  (foo x))

(defn h [y]
  (+ (foo-macro y) 3))
```

While clearly contrived, this example shows that at macro use time all
the code required to expand the macro must be defined, and that thus
the macro can be safely and statically expanded regardless of whether
the macro transformation occurs before or during final "deployed"
program runtime. This comes with the complement restriction, that
macros cannot impact runtime values save through emitting code which
will do so at runtime. This is not to say that macros must be pure,
simply that any side-effects which they may exhibit at load time
cannot be expected to occur during deployment runtime. That this
separation is not enforced is considered a flaw in the Oxlang design
and is a point of work.

This model can be applied with no adaptation both to a statically
compiled as well as a REPL environment, as in a REPL context the
evaluation of an input is simply the compilation of an "entry"
function in the current REPL context with the read input for a body.

The other nice property of this design is that since a full
interpreter must exist as part of the compiler, the implementation of
partial evaluation and specialization engines is greatly simplified as
all of the target source code is available for execution during ahead
of time compilation allowing for the partial application and
specialization of pure functions as well as other transformations.
