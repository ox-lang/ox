package org.oxlang.lang;

import io.lacuna.bifurcan.List;
import io.lacuna.bifurcan.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 6/11/17.
 *
 * A package.ox file is of the form
 *
 *     (cond-expand
 *        (package
 *         (define-package (package-version $GROUP $NAME (semver-version M N R))
 *            ;; requirements are either pinned or are constraints
 *            {(package-identifier $GROUP $NAME)  [(semver-version M N R)]
 *             (package-identifier $GROUP $NAME)  [(version-lt (semver-version 2 0 0))
 *                                                 (version-gte (semver-version 1 2 0))]})))
 *
 * A package.lock file is of the form
 *
 *     (cond-expand
 *        (package
 *         (define-package (package-version $GROUP $NAME (semver-version M N R))
 *            ;; requirements are pinned rather than being constraints
 *            {(package-identifier $GROUP $NAME) [(semver-version ...)]
 *             (package-identifier $GROUP $NAME) [(semver-version ...)]
 *             (package-identifier $GROUP $NAME) [(semver-version ...)]})))
 *
 * A package.lock can be generated from a package.ox when the dependencies are resolved. This is in the same style as
 * the NPM, Gem, Cabal and Cargo infrastructure wherein dependencies can be solved for by constraints, but for most
 * purposes are locked to ensure strict build reproducibility.
 */
public class PackageDescriptor {
  // FIXME (arrdem 6/11/2017) we need a package context to analyze define-package expressions in.
  static final Runtime PACKAGE_CONTEXT = null;

  @NotNull
  public final PackageVersionIdentifier id;

  @NotNull
  public final Map<PackageIdentifier, List<PackageVersionConstraint>> requirements;

  public PackageDescriptor(@NotNull PackageVersionIdentifier id,
                           @NotNull Map<PackageIdentifier, List<PackageVersionConstraint>> requirements) {
    this.id = id;
    this.requirements = requirements;
  }

  public static PackageDescriptor of(Object sexpr) {
    /**
     * FIXME (rmckenzie 6/11/2017) implementing this is gonna be Capital H Hard. It requires that I've stood up the
     * entire macro system, and an evaluator using some strategy because the package.ox files are actually totally
     * legitimate Ox source code making use of SRFI-0 cond-expand macros. That in and of itself may be a feature which
     * gets dropped, but it's in here for now and so here it shall stay.
     */
    return null;
  }
}
