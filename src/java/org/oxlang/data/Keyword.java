package org.oxlang.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by arrdem on 9/19/15.
 * <p>
 * A Keyword must have a non-null, legal getName.
 * A Keyword may have a namespace, but it may be null.
 */
public class Keyword
    implements Named<Keyword> {
  private static final WeakHashMap<String, Keyword> internMap = new WeakHashMap<String, Keyword>();

  private final Symbol symbol;

  private Keyword(@NotNull Symbol symbol) {
    this.symbol = symbol;
  }

  /**
   * Parses a String, which could constitute a valid Keyword, and returns the parsed Keyword or
   * throws IllegalArgumentException.
   *
   * @param text
   * @return
   */
  @NotNull
  public static synchronized Keyword of(@NotNull String text)
      throws IllegalArgumentException
  {
    if (text == null)
      throw new NullArgumentException("text cannot be null!");

    Keyword val = internMap.get(text);
    if (val != null) {
      return val;
    } else {
      if (!text.startsWith(":"))
        throw new IllegalArgumentException("Keywords are all ':' prefixed!");

      val = new Keyword(Symbol.of(text.substring(1)));
      internMap.put(text, val);
      return val;
    }
  }

  /**
   *
   * @param namespace
   * @param name
   * @return
   */
  @NotNull
  public static Keyword of(String namespace,
                           @NotNull String name)
      throws IllegalArgumentException
  {
    if (name == null)
      throw new NullArgumentException("name cannot be null!");

    String text = String.format(":%s%s%s",
        namespace == null ? "" : namespace,
        namespace == null ? "" : "/",
        name);

    return of(text);
  }

  /**
   * Named
   */
  @Override
  @NotNull
  public String getName() { return symbol.getName(); }

  /**
   * Named
   */
  @Override
  @Nullable
  public String getNamespace() {
    return symbol.getNamespace();
  }

  /**
   * Named
   */
  @Override
  @NotNull
  public Symbol asSymbol() {
    return symbol;
  }

  /**
   * Object
   */
  @NotNull
  public String toString() {
    if (getNamespace() != null) {
      return String.format("<Keyword '%s' '%s'>", getNamespace(), getName());
    } else {
      return String.format("<Keyword '%s'>", getName());
    }
  }

  /**
   * Object equality :|
   */
  @Override
  public boolean equals(Object other) {
    if (other == null)
      return false;

    if (other instanceof Keyword) {
      Keyword otherT = (Keyword) other;
      return otherT == this || namespacesEqual(otherT)  && namesEqual(otherT);
    } else {
      return false;
    }
  }
}
