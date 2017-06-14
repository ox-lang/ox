package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 5/15/17.
 *
 * This class represents a fully qualified name for a package within the Ox ecosystem. So long as
 * the version is repeatable, this is a globally unique identifier for a given group of definitions.
 */
public class PackageIdentifier {
  @NotNull
  public final GroupIdentifier group;
  @NotNull
  public final String name;

  public PackageIdentifier(@NotNull GroupIdentifier group,
                           @NotNull String name) {
    this.group = group;
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("<PackageIdentifier %s '%s'>", group, name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PackageIdentifier that = (PackageIdentifier) o;

    if (!group.equals(that.group)) return false;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    int result = group.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }
}
