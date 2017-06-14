package org.oxlang.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.WeakHashMap;
import java.util.regex.Matcher;

/**
 * Created by arrdem on 9/19/15.
 * <p>
 * A Symbol must have a non-null, legal getName.
 * A Symbol may have a namespace, but it may be null.
 * <p>
 * Symbols are used to represent literal textual symbols, and to store the
 * textual getName to which values are bound during evaluation. The symbol class
 * itself is just a holder for metadata, a getName, possibly a namespace and
 * possibly metadata.
 */
public abstract class Symbol
    implements Named<Symbol> {
  public static final WeakHashMap<String, Symbol> internMap = new WeakHashMap<String, Symbol>();

  /**
   * Handles using the intern map, validated source text and fragments to return or build a Symbol.
   */
  private static synchronized Symbol ofLegalParsedParts(@NotNull String text,
                                                        @Nullable String namespace,
                                                        @NotNull String name) {
    Symbol val = namespace == null ? new SimpleSymbol(name) : new QualifiedSymbol(namespace, name);
    internMap.put(text, val);
    return val;
  }

  /**
   * Factors out most of the validation logic.
   */
  private static Symbol ofParts(@NotNull String text,
                                @Nullable String namespace,
                                @NotNull String name) throws IllegalArgumentException {

    if (!Named.isValidName(name))
      throw new IllegalArgumentException(
          String.format(
              "String '%s' is not a legal Symbol name", name));

    if (namespace != null && !Named.isValidNamespace(namespace))
      throw new IllegalArgumentException(
          String.format(
              "String '%s' is not a legal Symbol namespace", namespace));

    if (!Named.isValidFullName(text))
      throw new IllegalArgumentException(
          String.format("String '%s' is not a legal fully formed Symbol", text));

    return ofLegalParsedParts(text, namespace, name);
  }

  /**
   * Parses a String, which could be a valid Symbol (qualified or not), or garbage.
   * <p>
   * Uses a WeakHashMap to cache the mapping of previously parsed Strings to Symbols.
   *
   * @param text the text to parse.
   * @return a parsed Symbol if the text was legal.
   * @throws IllegalArgumentException if the text was illegal.
   */
  @NotNull
  public static synchronized Symbol of(@NotNull String text)
      throws IllegalArgumentException
  {
    if (text == null)
      throw new NullArgumentException("text cannot be null!");

    Symbol val = internMap.get(text);
    if (val != null) {
      return val;
    } else {
      String name = null, namespace = null;

      Matcher m = Named.symbolPattern.matcher(text);
      if (!m.matches())
        throw new IllegalArgumentException(
            String.format("String '%s' is not a legal Symbol", text));

      name = m.group("name");

      try {
        namespace = m.group("namespace");
      } catch (IllegalStateException e) {
      }

      return ofParts(text, namespace, name);
    }
  }

  /**
   * Parses a pair of Strings which could constitute a valid fully qualified Symbol.
   * <p>
   * Uses a WeakHashMap to cache the mapping of previously parsed Strings to Symbols.
   * <p>
   * This method should only be used when the source data is a pair of strings. Given a single
   * source string, calling .of(String) is more efficient than attempting to do partial parsing.
   *
   * @param namespace A nullable String constituting a legal namespace.
   * @param name A String constituting a legal name.
   * @return a parsed Symbol if the namespace and name were both legal.
   * @throws IllegalArgumentException if the namespace, name or the whole symbol was illegal.
   */
  @NotNull
  public static synchronized Symbol of(@Nullable String namespace,
                                       @NotNull String name)
    throws IllegalArgumentException
  {
    if (name == null)
      throw new NullArgumentException("name cannot be null!");

    String text = namespace != null ? String.format("%s/%s", namespace, name) : name;
    Symbol val = internMap.get(text);
    if (val != null) {
      return val;
    } else {
      return ofParts(text, namespace, name);
    }
  }

  /**
   * Object
   */
  @NotNull
  public String toString() {
    if (getNamespace() != null) {
      return String.format("<Symbol '%s', '%s'>", getNamespace(), getName());
    } else {
      return String.format("<Symbol '%s'>", getName());
    }
  }

  /**
   * Named
   */
  public Symbol asSymbol() {
    return this;
  }

  /**
   * Object equality :|
   */
  @Override
  public boolean equals(Object other) {
    if (other == null)
      return false;

    if (other instanceof Symbol) {
      Symbol otherT = (Symbol) other;
      return otherT == this || namespacesEqual(otherT)  && namesEqual(otherT);
    } else {
      return false;
    }
  }
}
