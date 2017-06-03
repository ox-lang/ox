package org.oxlang.lang;

/**
 * Created by arrdem on 5/23/17.
 */
public class DefinitionIdentifier {
  public final NamespaceIdentifier namespaceIdentifier;
  public final String name;

  public DefinitionIdentifier(NamespaceIdentifier namespaceIdentifier, String name) {
    this.namespaceIdentifier = namespaceIdentifier;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DefinitionIdentifier that = (DefinitionIdentifier) o;

    if (!namespaceIdentifier.equals(that.namespaceIdentifier)) return false;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    int result = namespaceIdentifier.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }
}
