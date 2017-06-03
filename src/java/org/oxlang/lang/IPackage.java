package org.oxlang.lang;

import io.lacuna.bifurcan.IForkable;
import io.lacuna.bifurcan.ILinearizable;
import io.lacuna.bifurcan.IMap;
import io.lacuna.bifurcan.ISet;
import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 5/20/17.
 */
public interface IPackage<T extends Comparable>
    extends ILinearizable<IPackage<T>>,
            IForkable<IPackage <T>> {
  /**
   * Returns an IPackage which has the given identifier.
   *
   * @param newId
   * @return
   */
  @NotNull
  IPackage<T> withPackageIdentifier(@NotNull PackageIdentifier<T> newId);

  /**
   * Returns an IPackage which has an additional dependency.
   *
   * @param newDependency
   * @return
   */
  @NotNull
  IPackage<T> withDependency(@NotNull PackageIdentifier<T> newDependency);

  /**
   * @return A mapping of package qualified symbols to the namespaces in this package.
   */
  @NotNull
  IMap<PackageSymbol, Namespace> getNamespaces();

  /**
   * @return A set of all the packages on which this package depends.
   */
  @NotNull
  ISet<PackageIdentifier<T>> getDependencies();

  /**
   * @return The identifier for this package.
   */
  @NotNull
  PackageIdentifier<T> getIdentifier();
}
