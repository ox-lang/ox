package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;

/**
 * Hash versions, for things like git commit IDs or UUIDs. Hash versions cannot be meaningfully
 * compared to one-annother except for equality, because they derive their ordering from other
 * sources.
 */
public class HashPackageVersion extends ConcretePackageVersion implements Comparable<HashPackageVersion> {
  @NotNull
  public final String hash;

  public HashPackageVersion(@NotNull String hash) {
    this.hash = hash;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    HashPackageVersion that = (HashPackageVersion) o;

    return hash.equals(that.hash);
  }

  @Override
  public int hashCode() {
    return hash.hashCode();
  }

  @Override
  public int compareTo(@NotNull HashPackageVersion o) {
    return 0;
  }

  @Override
  public boolean matches(ConcretePackageVersion v) {
    return this.equals(v);
  }

  @Override
  public String toString() {
    return this.hash;
  }
}
