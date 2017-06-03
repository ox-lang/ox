package org.oxlang.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Captures the invariant of a "simple" symbol which isn't namespace-qualified.
 */
public class SimpleSymbol extends Symbol {
  private final String name;

  SimpleSymbol(@NotNull String n) {
    super();
    this.name = n;
  }

  @Override
  @NotNull
  public String getName() {
    return name;
  }

  @Override
  @Nullable
  public String getNamespace() {
    return null;
  }
}
