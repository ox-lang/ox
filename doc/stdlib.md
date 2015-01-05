# Oxlang Standard Library

This is just a scratchpad of stuff which Oxlang will have to do and
provide based on what I've assumed exists in the reader and
elsewhere. This is not (yet) a spec or a structure. See
[the interfaces](/doc/interfaces.md) sketch if that's more your thing.

## Maps

*hash-map*, function of a list of pairs, returns an object being a
 hash lookup table with the same key/value pairs as the input alist.

*merge*, function of two maps, returns a new map with the right map
 "winning" in cases of key conflicts.

## Symbols

*symbol*, function of a single string, returns an unqualified symbol
 as named by the argument string.

*qualified-symbol*, function of two strings, returns a namespace
 qualified symbol as named by the two strings with the first string
 being the namespace and the second string being the name.

## Keywords

*keyword*, function of a string, returns an unqualified symbol as
 named by the argument string.

*qualified-keyword*, function of two strings, returns a qualified
 keyword with the first string being the namespace and the second
 string being the name.

## Msc

*with-meta*, function of an object and a map, returns the first
 argument with metadata equal to the argument map. Clobbers existing
 metadata if any exists.

*vector*, function of a list, returns an object being a conc-list like
 tree of implementation defined branching factor.

*big-integer*, function of a string and an integer being a
 radix. Decodes the string as an integer in the given radix, returning
 a big integer of the decoded value.

*unquote-string*, function of a string, decodes the string returning a
 new string as if the string had been read via read-string.

*read-string*, function of an environment and a string.
`(lambda (x y) (eval-read-eval x (parse-string y)))`.
