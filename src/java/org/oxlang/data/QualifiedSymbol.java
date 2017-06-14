package org.oxlang.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Captures the invariant of a "qualified" symbol which has a namespace.
 */
public class QualifiedSymbol extends Symbol {
  private final String name, namespace;

  QualifiedSymbol(@Nullable String ns,
                  @NotNull String n)
  {
    super();
    this.name = n;
    this.namespace = ns;
  }

  @Override
  @NotNull
  public String getName() {
    return name;
  }

  @Override
  @Nullable
  public String getNamespace() {
    return namespace;
  }
}
