# Oxlang
### What does the Ox say?

Oxlang is a Lisp dialect, born of Rich Hicky's Clojure, which seeks to
address the design and specification limitations of Clojure and
Clojurescript. Clojure is born of the JVM, and explicitly embraces its
host enthusiastically. This means that it is possible to write near
raw Java code in Clojure and much of the Clojure language
implementation is built in JVM interoperation with Clojure specific
JVM primitives rather than abstractly specified. This means that
implementing Clojure atop other platforms is fundamentally difficult
or even impossible as the language is defined only in terms of a
standard library which is enmeshed in its JVM implementation.

Oxlang seeks to preserve as much as possible of Clojure while
extending the persistent datastructure philosophy which makes Clojure
code so elegant into the language itself by formalizing the language
core grammar and evaluation semantics into a platform independent form
that can be reasonably retargeted.

The Oxlang spect contains, in order
 0. [A formal parse grammar with explicit whitespace handling rules](src/grammar.md)
 1. [A spec for evaluation semantics](src/evaluation.md)
 2. [A spec for the Oxlang/Clojure "standard library" data abstractions](src/interfaces.md)
 3. [A spec for the Oxlang "standard library" functions](src/functions.md)
 4. [A spec for standard library language extensions](src/modules.md)
 5. [A spec for implementation specific host interop](src/interop.md)

## License

Copyright 2014 Reid "arrdem" McKenzie & contributors

Made avalable for distribution under the MIT/X11 license.
