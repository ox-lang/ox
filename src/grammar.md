# Oxlang Grammar

Clojure's grammar is at the "form" level, being a single top level s
expression. In contrast, Oxlang seeks to do whole file parses and
assumes that code reloading must occur at the whole module or whole
scope level rather than at the single form level. This is a
consequence of Oxlang's immutable and platform agnostic environment
implementation.

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
`@`, `:`, `#` either qualified or unqualified. The characters
`[`, `]`, `{`, `}`, `(`, `)`, `;` may not exist anywhere within a
Symbol.

Clojure specifies that a single instance of `/` is a special
symbol. Oxlang further requires that for any otherwise valid symbol
qualifying prefix `a`, `a//`, `a///`, `a////` and soforth shall be
legitimate symbols.

### Keywords

Keywords may be regarded as a special case of a symbol prefixed with
":". Keywords are values and are not environment bound like
symbols. As keywords are values, it is idiomatic to use keywords where
quoted symbols or enums would otherwise be used for symbolic values.

Note that Keywords exhibit all the same naming limitations as Symbols.

### Numbers

Oxlang and Clojure provide support for all Java number formats, as
specified
[here](http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10)
in the Java 1.7 spec.

### Strings

Strings in Oxlang and Clojure are to Java 1.7 spec, as detailed
[here](http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.5).

### Lists


### Vectors


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
