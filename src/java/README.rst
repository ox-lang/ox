FIXME:

`io.lacuna.bifurcan` is a source checkout of

 - https://github.com/ox-lang/bifurcan
 - https://github.com/lacuna/bifurcan

because there isn't a good way I've been able to find for convincing pants how to build dependencies
which aren't placed relative to the source root. This entire package tree should be discarded when
bifurcan becomes available as a jar dependency.
