package org.oxlang.lang;

import io.lacuna.bifurcan.*;
import org.jetbrains.annotations.NotNull;
import org.oxlang.data.SimpleSymbol;
import org.oxlang.data.Symbol;
import sun.security.krb5.internal.PAData;

/**
 * Created by arrdem on 5/15/17.
 */
class Package<T extends Comparable>
    implements
        IMap<SimpleSymbol, Namespace>
{
  public final Set<PackageIdentifier> dependencies;
  public final IMap<SimpleSymbol, Namespace> contents;
  public final PackageIdentifier<T> id;

  public Package(@NotNull PackageIdentifier<T> id,
                 @NotNull Set<PackageIdentifier> dependencies,
                 @NotNull IMap<SimpleSymbol, Namespace> contents)
  {
    this.dependencies = dependencies;
    this.contents = contents;
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Package<?> aPackage = (Package<?>) o;

    if (!dependencies.equals(aPackage.dependencies)) return false;
    if (!contents.equals(aPackage.contents)) return false;
    return id.equals(aPackage.id);
  }

  @Override
  public int hashCode() {
    int result = dependencies.hashCode();
    result = 31 * result + contents.hashCode();
    result = 31 * result + id.hashCode();
    return result;
  }

  @Override
  public Namespace get(SimpleSymbol key, Namespace defaultValue) {
    return contents.get(key, defaultValue);
  }

  @Override
  public boolean contains(SimpleSymbol key) {
    return contents.contains(key);
  }

  @Override
  public IList<IEntry<SimpleSymbol, Namespace>> entries() {
    return contents.entries();
  }

  @Override
  public long size() {
    return contents.size();
  }

  @Override
  public Package<T> forked() {
    if (!dependencies.isLinear() && !contents.isLinear()) {
      return this;
    } else {
      return new Package<>(id, dependencies.forked(), contents.forked());
    }
  }

  @Override
  public Package<T> linear() {
    if (dependencies.isLinear() && contents.isLinear()) {
      return this;
    } else {
      return new Package(id, dependencies.linear(), contents.linear());
    }
  }
}
