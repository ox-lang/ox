package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 5/23/17.
 */
public class NamespaceIdentifier {
  public final PackageVersionIdentifier pvid;
  public final String name;

  public NamespaceIdentifier(@NotNull PackageVersionIdentifier pvid,
                             @NotNull String name) {
    this.pvid = pvid;
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("<NamespaceIdentifier %s '%s'>", pvid.toString(), name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NamespaceIdentifier that = (NamespaceIdentifier) o;

    if (!pvid.equals(that.pvid)) return false;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    int result = pvid.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }
}
