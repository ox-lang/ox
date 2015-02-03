# 牛: Ox, the language

Master: [![Master Build Status](https://travis-ci.org/oxlang/oxlang.svg?branch=master)](https://travis-ci.org/oxlang/oxlang?branch=master)
[![Master Coverage Status](https://coveralls.io/repos/oxlang/oxlang/badge.png?branch=master)](https://coveralls.io/r/oxlang/oxlang?branch=master)

[![Gittip button](http://img.shields.io/gittip/arrdem.svg)](https://www.gittip.com/arrdem/ "Support this project")

Oxlang is the child of my efforts on
[Oxcart](https://github.com/oxlang/oxcart), in that Oxlang is an
experiment aimed at building a simpler, static lisp. See
[my blog posts](http://arrdem.com/2014/09/10/ox:_a_preface/) for some
real motivational information.

## Usage

As of this writing, Oxlang is just a parser, bare bones interpreter
and a test suite. See [the parser docs](/doc/parser.md) for some
information and examples of the parser in action. The
[evaluation notes on the parser](https://github.com/oxlang/oxlang/blob/master/doc/evaluation.md#reader)
explain why the parser/reader is the way it is now. If you want to
play with it, the parser is implemented in `ox.lang.parser`.

[See the docs](/doc/README.md) for my notes on what Oxlang is and
will be. [The issue tracker](https://github.com/oxlang/oxlang/issues)
is also being used to keep notes on what's up with Oxlang.

Oxlang includes a bootstrap file, sorta like
[BODOL](https://github.com/bodil/BODOL)'s prelude file. The hope is
that the entirity of Oxlang can be bootstrapped using only the prelude
file given an appropriate foundation. So lets load 'er up!

```Clojure
user> (require '[ox.lang.evaluator :as e])
user> (e/interpreting-load "ox/lang/bootstrap")
[eval] (def* list nil (fn* ((& xs) (list* xs))))
[eval] (def* first nil (fn* ((x) (apply* (fn* ((x & more) x)) x))))
[eval] (def* second nil (fn* ((x) (apply* (fn* ((x y & more) y)) x))))
[eval] (def* rest nil (fn* ((x) (apply* (fn* ((x & more) more)) x))))
[eval] (def* keyword nil (fn* ((n) (list (quote keyword/unqualified) n)) ((ns n) (list (quote keyword/qualified) ns n))))
[:env/ns
 {:ns user,
  :parent
  [:env/base
   {:bindings
    {ns* [:binding/special ns*],
     ns [:binding/alias ox.lang.bootstrap/ns],
     let* [:binding/special let*],
     list* [:binding/special list*],
     do* [:binding/special do*],
     fn* [:binding/special fn*],
     if* [:binding/special if*],
     quote [:binding/special quote],
     apply* [:binding/special apply*],
     def* [:binding/special def*],
     letrc* [:binding/special letrc*],
     invoke [:binding/special invoke]}}],
  :loaded-namespaces {},
  :imports #{},
  :bindings
  {first [:binding/alias user/first],
   rest [:binding/alias user/rest],
   user/second [:binding/value (fn* ((x) (apply* (fn* ((x y & more) y)) x)))],
   user/first [:binding/value (fn* ((x) (apply* (fn* ((x & more) x)) x)))],
   user/list [:binding/value (fn* ((& xs) (list* xs)))],
   second [:binding/alias user/second],
   user/rest [:binding/value (fn* ((x) (apply* (fn* ((x & more) more)) x)))],
   user/keyword [:binding/value (fn* ((n) (list 'keyword/unqualified n)) ((ns n) (list 'keyword/qualified ns n)))],
   list [:binding/alias user/list],
   keyword [:binding/alias user/keyword]}}]
```

The return value of loading a file is the namespace result of
evaluating all forms in the target. This is an immutable value
representing a loaded namespace that we can throw around. So if we try
interpreting a form using this loaded namespace as the environment, we
are interpreting the form in the namespace. So since we have
definitions of `list` and `first` lets try those out.

```Clojure
user> (e/interpreting-eval *1 '(let* ((x '(1 2))) (first x)))
[eval] (let* ((x (quote (1 2)))) (first x))
[eval] (quote (1 2))
[eval] (do* (first x))
[eval] (first x)
[eval] first
[eval] (apply* (fn* ((x) (apply* (fn* ((x & more) x)) x))) x nil)
[eval] x
[eval] nil
[eval] (do* (apply* (fn* ((x & more) x)) x))
[eval] (apply* (fn* ((x & more) x)) x)
[eval] x
[eval] (do* x)
[eval] x
[[:env/ns
  {:ns user,
   :parent
   [:env/base
    {:bindings
     {ns* [:binding/special ns*],
      ns [:binding/alias ox.lang.bootstrap/ns],
      let* [:binding/special let*],
      list* [:binding/special list*],
      do* [:binding/special do*],
      fn* [:binding/special fn*],
      if* [:binding/special if*],
      quote [:binding/special quote],
      apply* [:binding/special apply*],
      def* [:binding/special def*],
      letrc* [:binding/special letrc*],
      invoke [:binding/special invoke]}}],
   :loaded-namespaces {},
   :imports #{},
   :bindings
   {first [:binding/alias user/first],
    rest [:binding/alias user/rest],
    user/second [:binding/value (fn* ((x) (apply* (fn* ((x y & more) y)) x)))],
    user/first [:binding/value (fn* ((x) (apply* (fn* ((x & more) x)) x)))],
    user/list [:binding/value (fn* ((& xs) (list* xs)))],
    second [:binding/alias user/second],
    user/rest [:binding/value (fn* ((x) (apply* (fn* ((x & more) more)) x)))],
    user/keyword [:binding/value (fn* ((n) (list 'keyword/unqualified n)) ((ns n) (list 'keyword/qualified ns n)))],
    list [:binding/alias user/list],
    keyword [:binding/alias user/keyword]}}]
 1]
```

## License

Copyright 2014 Reid "arrdem" McKenzie & contributors

Made avalable for distribution under the MIT/X11 license.
