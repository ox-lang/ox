package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 5/23/17.
 */
public class NamespaceIdentifier {
  public final PackageIdentifier packageIdentifier;
  public final String name;

  public NamespaceIdentifier(@NotNull PackageIdentifier packageIdentifier,
                             @NotNull String name) {
    this.packageIdentifier = packageIdentifier;
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("<NamespaceIdentifier %s '%s'>", packageIdentifier.toString(), name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NamespaceIdentifier that = (NamespaceIdentifier) o;

    if (!packageIdentifier.equals(that.packageIdentifier)) return false;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    int result = packageIdentifier.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }
}
