package org.oxlang.lang;

import io.lacuna.bifurcan.*;
import org.jetbrains.annotations.NotNull;
import org.oxlang.data.SimpleSymbol;
import org.oxlang.data.Symbol;
import sun.security.krb5.internal.PAData;

/**
 * Created by arrdem on 5/15/17.
 *
 * A Package is a versioned, named set of namespaces which contain definitions.
 * Packages are the unit at which code is considered to be versioned & distributed.
 */
class Package {
  public final IMap<SimpleSymbol, Namespace> contents;
  @NotNull public final PackageDescriptor descriptor;

  public Package(@NotNull PackageDescriptor descriptor,
                 @NotNull IMap<SimpleSymbol, Namespace> contents)
  {
    this.descriptor = descriptor;
    this.contents = contents;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Package aPackage = (Package) o;

    if (contents != null ? !contents.equals(aPackage.contents) : aPackage.contents != null) return false;
    return descriptor.equals(aPackage.descriptor);
  }

  @Override
  public int hashCode() {
    int result = contents != null ? contents.hashCode() : 0;
    result = 31 * result + descriptor.hashCode();
    return result;
  }
}
