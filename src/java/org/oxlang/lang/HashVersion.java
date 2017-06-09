package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;

/**
 * Hash versions, for things like git commit IDs or UUIDs. Hash versions cannot be meaningfully
 * compared to one-annother except for equality, because they derive their ordering from other
 * sources.
 */
public class HashVersion extends ConcreteVersion implements Comparable<HashVersion> {
  @NotNull
  public final String hash;

  public HashVersion(@NotNull String hash) {
    this.hash = hash;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    HashVersion that = (HashVersion) o;

    return hash.equals(that.hash);
  }

  @Override
  public int hashCode() {
    return hash.hashCode();
  }

  @Override
  public int compareTo(@NotNull HashVersion o) {
    return 0;
  }

  @Override
  public boolean matches(ConcreteVersion v) {
    return this.equals(v);
  }

  @Override
  public String toString() {
    return this.hash;
  }
}
