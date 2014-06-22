# Oxlang data interfaces

Note that these are abstract interfaces describing common data
structures. These interfaces do not dictate or seek to dictate the
concrete types or implementations of these data structures.

## Booleans

```
 - True
 - False
 - (ox.core.Bool/boolean? Any) : Boolean
```

## Nums

At the end of the day computers are for munging numbers.  Having good
native support for generic numbers is a must.  Note that having
support for numbers is agnostic as to the implementation of numbers.

```
 - (ox.core.Num/num? Any)     : Boolean
 - (ox.core.Num/inc  Num)     : Num
 - (ox.core.Num/dec  Num)     : Num
 - (ox.core.Num/+    Num Num) : Num
 - (ox.core.Num/-    Num Num) : Num
 - (ox.core.Num/*    Num Num) : Num
 - (ox.core.Num//    Num Num) : Num
 - (ox.core.Num/pow  Num Num) : Num
 - (ox.core.Num/mod  Num Num) : Num
 - (ox.core.Num/<    Num Num) : Boolean
 - (ox.core.Num/>    Num Num) : Boolean
 - (ox.core.Num/=    Num Num) : Boolean
 - (ox.core.Num/<=   Num Num) : Boolean
 - (ox.core.Num/>=   Num Num) : Boolean
```

Int, Long, Float, Double, Rational and BigInt are all Nums


## Bitstring

Numbers need not be bitstrings.  Consider a pair (real, imag).  Is it
reasonable to treat this pair as a bistring?  Not obviously, therefore
Bitstrings != general Nums.  However obviously Bitstrings and Nums may
intersect in the case of _sized_ numbers such as Longs, Doubles,
Singles/Ints and soforth.

Bitstrings should likely all be Seqs, but that's an extension detail
not an abstraction essential detail.

```
 - (ox.core.Bits/bitstring? Any)                 : Boolean
 - (ox.core.Bits/<<         Bitstring Num)       : Bitstring
 - (ox.core.Bits/>>         Bitstring Num)       : Bitstring
 - (ox.core.Bits/&          Bitstring Bitstring) : Bitstring
 - (ox.core.Bits/|          Bitstring Bitstring) : Bitstring
 - (ox.core.Bits/^          Bitstring Bitstring) : Bitstring
```


## Sequences

Sequences are an abstraction over datastructures which may be viewed
as (first, rest) in the traditional cons cell view of the world.

```
 - (ox.core.Seq/seq?      Any)     : Boolean
 - (ox.core.Seq/finite?   Seq)     : Boolean
 - (ox.core.Seq/infinite? Seq)     : Boolean
 - (ox.core.Seq/bounded?  Seq)     : Boolean
 - (ox.core.Seq/count     Seq)     : Num
 - (ox.core.Seq/bound     Seq)     : Num
 - (ox.core.Seq/empty?    Seq)     : Boolean
 - (ox.core.Seq/empty     Seq)     : Seq
 - (ox.core.Seq/first     Seq)     : Any
 - (ox.core.Seq/second    Seq)     : Any
 - (ox.core.Seq/nth       Seq Int) : Any
 - (ox.core.Seq/rest      Seq)     : Seq
 - (ox.core.Seq/butlast   Seq)     : Seq
 - (ox.core.Seq/last      Seq)     : Seq
 - (ox.core.Seq/conj      Seq Any) : Seq
 - (ox.core.Seq/concat    Seq Seq) : Seq
```

## Set

Sets will be Sequences and Functions at a minimum, but these are the
theoretic operations that are unique to Sets.

```
 - (ox.core.Set/set?         Any)     : Boolean
 - (ox.core.Set/contains?    Set Any) : Boolean
 - (ox.core.Set/union        Set Set) : Set
 - (ox.core.Set/intersection Set Set) : Set
 - (ox.core.Set/difference   Set Set) : Set
 - (ox.core.Set/complement   Set)     : Set
```

## Mapping

Mappings are also Sequences on their key value pairs, and manipulating
Mappings via rest/conj/concat builds new mappings.

```
 - (ox.core.Map/map?   Any)         : Boolean
 - (ox.core.Map/get    Map Any)     : Any
 - (ox.core.Map/assoc  Map Any Any) : Map
 - (ox.core.Map/dissoc Map Any)     : Map
 - (ox.core.Map/keys   Map)         : Seq
 - (ox.core.Map/vals   Map)         : Seq
```

## Uuid

Since Clojure provides special handling for UUIDs in terms of having
printing and reading literals for them, it's silly that the Clojure
core lacks uuid creation facilities and a uuid predicate.

```
 - (ox.core.Uuid/uuid?     Any)     : Boolean
 - (ox.core.Uuid/rand-uuid)         : Uuid
 - (ox.core.Uuid/uuid      Int Int) : Uuid
```

## Fn

An issue with this spec is that from a lambda calculus standpoint
functions apply as soon as they have sufficient arity to do
so. Clojure violates this by allowing the definition of multiple arity
"functions" meaning that the classical definition of "papply", being a
pure lambda function which creates a partial, and since in the lambda
calculus the partial application of a function of a single argument is
the evaluation of a function everything "just works" when you partial
in that last argument.

Consider

```
(defn foo (fn ([x] x)
              ([x y] y)))

```

What is the meaning of `(partial foo 1)`? Is it the application of the
single arity case, or is it the partial of the double arity case? The
only answer I can see is that rather than defining `apply` in terms of
functions apply is defined in terms of partials and partial is defined
in terms of Fns and Partials. This means that `(apply (partial foo
3))` for example is what we would typically write as `(foo 3)`, while
`(foo 3 4)` is `(apply (partial (partial foo 3) 4))`.

```
 - (ox.core.Fn/fn?     Any)             : Boolean
 - (ox.core.Fn/compose Fn Any)          : Fn
 - (ox.core.Fn/partial Fn Any)          : Fn
 - (ox.core.Fn/apply   Fn Any* [Any])   : Any
```

## Named

Generic protocol for datastructures which have symbolic or programmer
meaningful "names" and participate in the Clojure/Oxlang namespaced
naming scheme.

```
 - (ox.core.Named/named?    Any)   : Boolean
 - (ox.core.Named/namespace Named) : String
 - (ox.core.Named/name      Named) : String
```

## Symbols

Symbols are Named.

```
 - (ox.core.Symbol/symbol? Any)           : Boolean
 - (ox.core.Symbol/symbol  String)        : Symbol
 - (ox.core.Symbol/symbol  String String) : Symbol
```

## Keywords

Also Named.

```
 - (ox.core.Keyword/keyword? Any)           : Boolean
 - (ox.core.Keyword/keyword  String)        : Keyword
 - (ox.core.Keyword/keyword  String String) : Keyword
```
