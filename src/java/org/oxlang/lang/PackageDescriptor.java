package org.oxlang.lang;

/**
 * Created by arrdem on 6/11/17.
 *
 * A package.ox file is of the form
 *
 *     (define-package (package-version $GROUP $NAME (semver-version M N R))
 *       ;; requirements are either pinned or are constraints
 *       [(package-requirement (package-identifier $GROUP $NAME)
 *           [(semver-version M N R)])
 *        (package-requirement (package-identifier $GROUP $NAME)
 *           [(version-lt (semver-version 2 0 0))
 *            (version-gte (semver-version 1 2 0))])])
 *
 * A package.lock file is of the form
 *
 *     (define-package (package-version $GROUP $NAME (semver-version M N R))
 *       ;; requirements are pinned rather than being constraints
 *       [(package-version ...)
 *        (package-version ...)
 *        (package-version ...)])
 *
 * A package.lock can be generated from a package.ox when the dependencies are resolved. This is in the same style as
 * the NPM, Gem, Cabal and Cargo infrastructure wherein dependencies can be solved for by constraints, but for most
 * purposes are locked to ensure strict build reproducibility.
 */
public class PackageDescriptor {
}
