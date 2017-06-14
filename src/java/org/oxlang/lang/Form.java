package org.oxlang.lang;

import io.lacuna.bifurcan.Set;
import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 6/3/17.
 */
public class Form {
  @NotNull
  public final Object expr;

  @NotNull
  public final Set<DefinitionIdentifier> provides;

  public Form(@NotNull Object expr, @NotNull Set<DefinitionIdentifier> provides) {
    if (expr == null)
      throw new IllegalArgumentException("Got null `expr`!");

    if (provides == null)
      throw new IllegalArgumentException("Got null `provides`!");

    this.expr = expr;
    this.provides = provides;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Form form = (Form) o;

    if (!expr.equals(form.expr)) return false;
    return provides.equals(form.provides);
  }

  @Override
  public int hashCode() {
    int result = expr.hashCode();
    result = 31 * result + provides.hashCode();
    return result;
  }
}
