package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 6/8/17.
 */
public class PackageVersionIdentifier {
  @NotNull
  public final PackageIdentifier pkg;
  @NotNull
  public final ConcreteVersion version;

  public PackageVersionIdentifier(@NotNull PackageIdentifier pkg,
                                  @NotNull ConcreteVersion version)
  {
    this.pkg = pkg;
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PackageVersionIdentifier that = (PackageVersionIdentifier) o;

    if (!pkg.equals(that.pkg)) return false;
    return version.equals(that.version);
  }

  @Override
  public int hashCode() {
    int result = pkg.hashCode();
    result = 31 * result + version.hashCode();
    return result;
  }
}
