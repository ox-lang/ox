# Oxlang Grammar

Oxlang and Clojure both define per-form grammars, build from the
following terminal cases.

## Reader literals

### Symbols

Arguably the catch all reader literal, symbols are used for the names
in a program. Symbols may be prefixed with `#"(\w+\.)+"` as with Java
symbols, may contain capitalization, may contain a Clojure style
namespace qualified symbol suffix, and may end with `"."`.

#### Examples
```
a
a.b
a.b.C.
a.b/c
a.b-c/d
```

#### Warnings

To avoid grammar conflicts, symbols may not start with any of `'`,
`@`, `:`, `#` either qualified or unqualified. Symbols may however
contain these characters. The characters `[`, `]`, `{`, `}`, `(`, `)`
and `;` may not exist anywhere within a Symbol. Symbols containing
these tokens may legally generate parser errors, and the explicit
construction of such a symbol via a constructor method shall be an
error. The empty string likewise is not a legitimate symbol.

Clojure specifies that a single instance of `/` is a special
symbol. Oxlang further requires that for any otherwise valid symbol
qualifying prefix `a`, `a//`, `a///`, `a////` and soforth shall be
legitimate symbols.

### Keywords

Keywords may be regarded as a special case of a symbol prefixed with
":". Keywords are values and are not environment bindings like
symbols. As keywords are values, it is idiomatic to use keywords where
quoted symbols or enums would otherwise be used for symbolic values.

Note that Keywords exhibit all the same naming limitations as Symbols,
as the grammar definition of Keyword is

    Keyword := ":" ":"? Symbol?

### Numbers

Oxlang and Clojure provide support for all Java number formats, as
specified
[here](http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10)
in the Java 1.7 spec. Support is further provided for BigIntegers and
for Rationals, according to their respective notations.

### Strings

Strings in Oxlang and Clojure are to Java 1.7 spec, as detailed
[here](http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.5).

### Lists

Lists are sequences of elements, written as a whitespace deliminated
sequence enclosed in "(" and ")". If a list is not quoted, it is
interpreted as a form rather than as a datastructure and will be
compiled as a form (code) rather than a list (datastructure).

### Vectors

Vectors are sequences of elements, written as a witespace deliminated
sequence enclosed in "[" and "]". Unlike Lists, Vectors will never be
interpreted as forms and always represent a datastructure.

### Maps


### Sets


### Quote


### Characters


### Deref


### Metadata


### Dispatch


### Syntax-quote


## Parser grammar

See the reference Clojure grammar in [clojure.ebnf](clojure.ebnf).
