# 牛: Packages and packaging

Ox believes that packaging cannot be a second class citizen of a language environment, and that
ensuring consistent package versioning semantics and efficient testing requires language level
awareness of packages, versioning and change.

Consequently Ox includes the concepts of packages, package versions and packaging as part of
the core language.

## Packages

Every collection of source files, every project and every dependency in Ox has a package.

A package is a named and versioned collection of dependencies and Ox namespaces, together with
a package file, `package.ox`. The package file sits at the root of the source tree, and states
the name and version of the package, along with its dependencies.

A major difference between Ox and most other packaging systems is that Ox makes it possible to
load multiple versions of a package at once.

Within the scope of a package, any definition from any directly depended upon package may be
used. Transitively depended on packages (that is the dependencies of direct dependencies)
are entirely opaque and cannot be accessed directly. This makes it possible for conflicting
versions of a single package to become loaded, and to be used by different packages.

There is an exception to this rule: data types defined in transitively depended packages can become visible even if
another potentially conflicting version is directly depended on if an exposed function or data type in a directly
depended package returns or contains a transitively depended data type.

**aside:** Internally, Ox always refers to all symbols in a manner fully qualified by versioned package name and
namespace. This means that even a seeming conflict where multiple versions of a single type become visible are still
actually trivial and safe.

### package.ox

A `package.ox` consists of a single legal Ox form, being a use of `define-package`.

```
;; -*- mode: ox; -*-
;; package.ox
;;
;; This is an example package file, which defines a package org.oxlang/demo@0.0.0
;; That is, a package with the group "org.oxlang", the name "demo" and the version
;; 0.0.0 in Semantic Version notation.
;;
;; This package is defined using the define-package form,
;;
;;   (define-package 
;;      ^package-identifier                            package
;;      ^(map package-name (list package-constraint))  requirements)
;;
;; That is, define-package accepts a fully version qualified package identifier,
;; and a mapping of version unqualified package names to lists of version constraints.
;;
;;   (package-version ^string group ^string name ^version version)
;;
;; Constructs and returns a fully version qualified package name. That is, a package name
;; which refers only to a single version.
;;
;;   (semver-version ^pos-int major ^pos-int minor ^pos-int patch)
;;
;; Constructs and returns a record representing a Semantic Versioning version.
;; SemVer versions are themselves version constraints (they match only equal versions),
;; and they can be used to construct version comparisons.
;;
;;   (version-< ^semver-version v)
;;
;; Constructs and returns a version constraint which matches only versions less than
;; the given version. There's also `version->=` which matches versions greater than or
;; equal to the given version, `version-<=`, `version->` and `version-!= with the 
;; semantics you'd expect.

(define-package (package-version "org.oxlang" "demo" (semver-version 0 0 0))
   ;; requirements are either pinned or are constraints
   {(package-identifier "org.oxlang" "ox") [(semver-version 0 1 0)]
    (package-identifier "com.foo" "bar")   [(version-< (semver-version 2 0 0))
                                            (version->= (semver-version 1 2 0))]})))
```

The requirements of an Ox package form a dependency list which may be resolved against a set
of package sources or "resolvers" to choose concrete versions of packages satisfying the
package's version constraints.

**aside:** When package dependencies are constraint solved, Ox may make an effort to only choose a single
version of a package, so that loading multiple versions is not required when there also exist legal
multiple version solutions.

Here, the package `org.oxlang/demo@0.0.0` requires `org.oxlang/ox` at precisely the version `0.1.0`,
and the package `com.foo/bar` at any version greater than `1.2.0` and less than `2.0.0`.

### Repeatable Builds

While dependency package versions may be resolved against constraints, Ox's default behavior is to
resolve constraints only once (the first time a package is built) and generate a `package.lock` file.
The `package.lock` file contains a generated `define-package` for the exact same package, except that
all package constraints are replaced with precise constraints which match only one version of the
required package.

This ensures that all future builds and runs of a package occur against the same set of dependencies
and dependency versions, so that all builds and test runs will run the same way every time instead
of resolving a possibly different version of a package and producing different behavior.

It is possible to force Ox to resolve a package's dependencies again, but doing so will generate a
new `package.lock` file and all builds will run against the newly resolved dependencies.

## Dependencies & Versioning

Ox provides support for using semantic versioning, but this isn't always appropriate. Git commit
hashes, SHA (and other checksums) of sources as well as other content aware hashes can be used to
identify a revision of a package. Ox provides an API by which users can define their own package
version types, and provides a API by which users can define their own package resolvers which support
mapping such custom versions to implementations.

When deploying a package with Ox, the Ox compiler may be used to ensure that versioning semantics are preserved.
For instance, the Semantic Versioning specification requires that a major version bump be used whenever a deletion
occurs. Yet frequently package maintainers in other languages release minor and patch version releases which contain
deletions because their ecosystem(s) provide no way to check the compatibility of changes.
