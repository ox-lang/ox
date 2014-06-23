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
(-> (in-ns 'scratch)      ;; raw empty environment mapping
    (load "ox/core")      ;; loads in the oxlang core
	(def foo 3)
	(println foo))
```
