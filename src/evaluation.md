# Oxlang evaluation

Clojure's approach to immutable datastructures is a programming model
which I find attractive and elegant. Unfortunately, the reference
Clojure implementation does not practice what it preaches especially
with regards to the use of mutable state to implement environments or
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
dependency graphs between functions or forwards declarations: all
directly used declarations must exist at single form compilation time.

Particularly, Clojure uses atomically transactional "var" objects to
store and retreive bindings from textual symbols to their def'd
values. A "namespace" is really just a named set of mappings from
aliases including prefixes and renamings to vars which the compiler
uses when evaluating a form.

However, vars also have the property of being dereferenced only at
runtime not at compile time and of being dynamically rebindable. Tis
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
`with-bindings` to alter `clojure.core` is semantically valid for
instance.

As defs create globally visible named vars, and anonymous vars can be
used to create by users to implement stack based mutable local
bindings as above, it is my contention that the var structure is
overloaded and inappropriate. In a statically compiled and checked
language, declarations (defs) should be declarative rather than
imperative and consequently should be statements rather than
expressions whose return values are meaningful.

As reference types tend to be used as an exposed algorithmic detail
rather than expressing a problem in terms of some other structure such
as the state monad I confess that I have little sympathy for the use
case of Vars as a dynamic user binding stack structure. Furthermore
the capability to rebind more than a top level definition with a
`with-bindings` form creates additional confusion for implementers in
terms of having to detect whether a "rebound" value is either a def'd
binding or a local "var".
