# 牛: The Notation

Ox is a prefix notation language in the Lisp tradition. While Ox is whitespace insensitive, there
are standard formatting & style rules.

## Comments

Comments begin with one or more semicolons, and continue until the first end of line. Multiple
sequential comment lines may be considered to be a single logical comment. Comments are for the
benefit of the programmer, and may be used to generate documentation or just leave notes inline.

    ; This is a comment!
	;; So is this!
	
	This is not a comment ; but this is!

## Lists

Lists are written using brackets for instance

    []                          ; The empty list
    [true false nil 12 3.14159] ; A list containing several values

Small lists of known length are sometimes referred to as tuples.

While lists are a data structure, they are also the fundamental element of Ox's syntax. Ox programs
consist of terms, such as symbols and numbers, arranged using lists into trees which constitute the
logical structure of the program. Lists of symbols and terms which constitute the majority of a
program are annotated using parenthesis.  For instance, the list

    (if true 1 2)

may be understood to be either a list of terms, or it may be evaluated as an `if` expression
consisting of a `condition` and a pair of clauses.

Both kinds of lists are common in Ox's syntax. Parenthetical lists are used to write function calls,
macros and type expressions. Bracketed lists are used to group related terms. For instance a
function which accepts two arguments and adds one could be written

    (fn [x y]
	  (+ x y))

## Symbols

We've already introduced a few symbols - `if`, `true`, `false`, `nil` and `+` to be precise. Symbols
are just names which a programmer uses to reference terms in a program.

Symbols may contain the `/` character once¹. `/` itself is a valid symbol, and symbols containing
`/` are said to be two symbols called namespace and a name joined together with the `/` and are said
to be "fully qualified" symbols.

[1] Exception is made for the symbol `/` which can become qualified.

Some examples would include

	;; Unqualified symbols
	fn
	let
    foo
	.
	
	;; Qualified symbols
    org.oxlang.lang/let
	org.oxlang.lang/+
	org.oxlang.lang//
	com.foo.my-product/transact

## Strings

As in other languages, Ox denotes strings using double quotation marks. `"hello, world"` being a
canonical example. Strings may contain escape sequences, consisting of `\` followed either by a
character with special meaning. Some common escape sequences include `\n` being the newline
character and `\"` being a double quotation mark which may legally occur within a string.

## Numbers

Ox supports two kinds of number literals - integers and floating point values. Integers are written
using decimal notation, and may contain the `_` character as a visual separator. For instance `13`
would be a legal integer, as would `+12` or `-6`.

Floating point values are also written using decimal notation, and are differentiated from integers
by having either (or both of!) one or more decimal digits or an exponent. `1.0`, `-3.122226`,
`6.71e8`, `1.616229e−35` would all be floating point values.

## Mappings

Mappings are an essential concept, and describe relating one group of values to another.  In keeping
with other data notations, in Ox mappings are written using curly braces. For instance, a ring of 4
could be written as a mapping from state ID to state ID such as

    {
	 1 2
	 2 3
	 3 1
	 ;; ...
	}

## Sets

Sets are another common and valuable structure. As a set may be considered to be a mapping of values
in the set to `true`, sets have a very similar notation to mappings. For instance the domain of the
booleans would be the set

    #{true false}

## Quoting

While symbols are usually used to refer to program elements and parenthetical lists describe
fragments of a program, they are themselves values which may be manipulated.

The expression `(quote e)` prevents `e` from being evaluated. For instance where the list `(+ 1 2)`
alone is an addition expression, whereas the expression `(quote (+ 1 2))` is the list `[+ 1 2]`.

The single quote character `'` is equivalent to using the full `(quote e)` expression. `'(1 2 3)'`
is equivalent to `[1 2 3]`.

## Keywords

Programmers frequently make use of special values, for instance in constructing maps of known
structure or in manipulating abstract terms. It is possible to make use of quoting to use symbols in
these cases, however this rapidly proves awkward as lots of quoting becomes required.

Keywords are values much like symbols, which have no meaning other than as data. They provide an
alternative to quoted symbols or simple strings as keys in mappings and their use is encouraged.

Keywords are written as symbols, with the `:` prefix. Any legal symbol with the `:` prefix is a
legal keyword. Like symbols, keywords are said to be composed of a namespace and a name. Some
examples would include

    ;; unqualified keywords
	:foo
	:bar
	
	;; qualified keyword
	:me.arrdem/foo

## Tags

As a typed language, it is sometimes convenient to annotate parts of a program with a type
explicitly. The carrot `^` is used to denote that the next symbol or parenthetical list is
understood to be a tag on the subsequent expression, rather than being an expression itself. For
instance `^integer (+ 1 2)` would annotate the list form `(+ 1 2)` with the tag `integer`,
understood to be a reference to a type.

Type tags may also be expressions, for instance if `map` were a generic type relating a key type to
a value type, a map expression could be annotated `^(map int int) {1 1}`.
