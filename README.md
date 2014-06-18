# Oxlang
### What does the Ox say?

Oxlang is a Lisp dialect, born of Rich Hicky's Clojure, which seeks to
preserve the data immutable semantics of Clojure while providing a
clear, clean, platform independant language specification which may be
reasonably targeted by language and compiler writers.

The Oxlang spect contains, in order
 - A formal parse grammar with explicit whitespace handling rules
 - A spec for the Oxlang/Clojure "standard library"
 - A spec for standard library language extensions
 - A spec for implementation specific host interop

The intent of Oxlang is not to become a fork of Clojure, but to
describe the common operations and libraries of all existing Clojure
implementations and by clarifying a standard independant of the JVM
implementation details present in the standard Clojure implementation
ease the implementation of Clojure atop other platforms and runtimes.

## License

Oxlang and all related material is copyright Reid "arrdem" McKenzie &
contributors 2014, made avalable for distribution under the MIT/X11 license.
