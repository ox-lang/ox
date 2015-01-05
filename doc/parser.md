# The Oxlang Parser

[Implementation](/resources/grammar/Oxlang.g4).

Oxlang's grammar is heavily derived from Clojure's in case you can't tell.

## Datatypes

Lists are `()` delimited sequences of arbitrary length. These are a
primitive kind, and must be implemented as part of any Oxlang
platform.

```Clojure
> (parse-string "(()(((())()())))")
(() (((()) () ())))
> (parse-string "nil")
()
```

Longs, being sequences of base 10 characters, or base 16 characters
with the `"0[xX]"` prefix are a primitive kind and are constructed in
the reader as are lists.

```Clojure
> (parse-string "0xF")
15
> (parse-string "15")
15
```

Symbols, being qualified with a namespace or unqualified symbolic
names are a primitive kind and are constructed in the reader and thus
require platform support.

```Clojure
> (parse-string "a")
a
> (parse-string "a/b")
a/b
```

Characters are a primitive kind and are constructed in the reader.

```Clojure
> (parse-string "\\space")
\space
> (int (parse-string "\\space"))
32
```

Booleans are a primitive kind and are constructed in the reader.

```Clojure
> (parse-string "true")
true
> (parse-string "false")
false
```

Vectors are `[]` delimited sequences of arbitrary length. These are
not a primitive kind, and implementations are expected to provide the
vector constructor for use by `read-eval`.

```Clojure
> (parse-string "[1 2 3]")
(read-eval (vector (1 2 3)))
```

Maps are `{}` delimited pairs of keys and values. As with vectors,
maps are not a primitive kind, and implementations are expected to
provide the map constructor for use by `read-eval`.

```Clojure
(parse-string "{1 2}")
(read-eval (hash-map ((1 2))))
```

Sets are sequences of forms beginning with `#{` and ending with
`}`. As with vectors and maps, sets are not a primitive kind and
implementations are expected to provide the set constructor for use by
`read-eval`.

```Clojure
> (parse-string "#{1 3 2}")
(read-eval (set 1 3 2))
```

Strings are sequences of characters delimited by `"`. Strings are not
a primitive kind for various reasons, and implementations are expected
to provide the `unquote-string` function for use by `read-eval`.

```Clojure
> (parse-string "\"foo bar baz\"")
(read-eval (unquote-string "\"foo bar baz\""))
```

Regexes are strings prefexed with the `#` character. As with strings,
they are not a primitive kind and implementations are expected to
provide a the `re-compile` function for use by `read-eval`.

```Clojure
> (parse-string "#\"[a-zA-Z]\"")
(read-eval (re-compile (read-eval (unquote-string "\"[a-zA-Z]\""))))
```

Floats or doubles are not a primitive kind. Implementations must
provide a function `float` for use by `read-eval`.

```Clojure
> (parse-string "-1.3e5")
(read-eval (float "-1.3e5"))
```

Big integers are not a primitive kind. Implementations must provide a
function `big-integer` for use by `read-eval`.

```Clojure
> (parse-string "919191919199191919191n")
(big-integer "919191919199191919191" 10)
> (parse-string "2r1111111111111111111111111111011111111111")
(big-integer "1111111111111111111111111111011111111111" (integer "2"))
```

## Parser macros

Oxlang's reader, as with Clojure's reader, is not user extensible
however it does feature some open dispatch capabilities allowing for a
restricted set of custom reader literals. These are implemented by
emitting `read-eval` forms where appropriate, or by generating special
lists.

Quote, `'FORM`, is a reader macro for `(quote FORM)` where `FORM` is
any arbitrary expression. `macroexpand` of `(quote x)` for any `x` is
`(quote x)`. `eval` of `(quote x)` is `x`. `read-eval` of
`(quote x)` is `(list 'quote (read-eval x))`.

Backtick, unquote, unquote-splicing are all reproduced from Clojure as
are `@` prefixing for dereferencing and `#` postfixing of
namespace-unqualified symbols for gensym creation.

Var quoting `#'foo/bar` may be supported. See oxlang/oxlang#11.

Clojure style metadata via `^foo` and `#^{foo true}` is supported.

Reader discard via `#_` is supported, but may be dropped see oxlang/oxlang#5.

Feature expressions ala `#+clj`, `#+ox`, `#+(or ox pixie)` are
supported, but may be dropped see oxlang/oxlang#

EDN style data readers are supported ala `#user/foo "my foo input"`,
or `#uuid "7823e36b-45e5-440c-a18a-fa094e64d2e2"`.

```Clojure
> (parse-string "#uuid \"7823e36b-45e5-440c-a18a-fa094e64d2e2\"")
(read-eval ((resolve-reader-macro (this-ns) 'uuid)
            (read-eval (unquote-string "\"7823e36b-45e5-440c-a18a-fa094e64d2e2\""))))
```
