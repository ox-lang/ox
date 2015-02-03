# 牛 - Green proposal

This proposal assumes the parser grammar and read-eval model of the
[Straw man](/straw/) proposal without modification. The goal of this
proposal was to explore exactly what special forms would be required
to specify a more minimal and portable form of the straw proposal.

This proposal deliberately does not privilege the evaluation context
or environment. Note that the `def*` and type manipulation forms are
merely common functions that manipulate (`assoc` into) the compilation
environment to create and manipulate module level bindings rather than
being privileged forms with compiler support as in Clojure.

This comes at the price putting all the evaluation context
(importantly including the `gensym` counter) within the state monad
including all the consequences of that with regards to use and update
model. In order to make this style of programming with multiple
interleaved updates on multiple values and no side-effects special
sugar along the lines of Haskell's `do` notation is probably in
order. However while additional support for usability may be in order
the essentials of this proposal do not require it.

[Special forms](/green/special-forms.md)
