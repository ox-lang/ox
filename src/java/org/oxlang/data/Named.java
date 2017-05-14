package org.oxlang.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by arrdem on 9/19/15.
 * <p>
 * In Ox as in Clojure, things which are named may be namespace qualified. This interface
 * represents that pattern.
 * <p>
 * Named things are by convention written a/b where a is said to be the namespace and b the name.
 * A named thing must always have a non-null name. It may have a non-null namespace, but this is
 * not required or guaranteed.
 */
public interface Named<T extends Named > {
  /**
   * @return A String relating to the textual representation of the Object.
   *
   * Note that this may never be null. Every named thing must have a name.
   */
  @NotNull
  String getName();

  /**
   * @return A String relating to the namespace of the Object, or null.
   *
   * Note that unlike the name, the namespace may be null. Some fully qualified names such as
   * namespaces have no namespace part.
   */
  @Nullable
  String getNamespace();

  /**
   * Convert a Named object to a legal Naming symbol.
   *
   * @return a Symbol with the same Name.
   */
  @NotNull
  default Symbol asSymbol() {
    return Symbol.of(getNamespace(), getName());
  }

  String _SYMBOL_START = "[^;:#\"'\\\\/\\[\\]\\(\\)\\{\\}\\d\\s,]";
  String _SYMBOL_BODY  = "[^;:#\"'\\\\/\\[\\]\\(\\)\\{\\}\\s,]";
  String _SIMPLE_SYMBOL = String.format("(%s)(%s)*", _SYMBOL_START, _SYMBOL_BODY);
  String _SYMBOL_OR_SLASH = String.format("(/|(%s))", _SIMPLE_SYMBOL);
  String QUALIFIED_SYMBOL = String.format(
      "^((?<namespace>%s)/)?(?<name>%s)$", _SIMPLE_SYMBOL, _SYMBOL_OR_SLASH
  );

  /**
   * Validator for Symbol and Keyword names.
   */
  Pattern namePattern =
      Pattern.compile(String.format("^(%s)$", _SYMBOL_OR_SLASH));

  static boolean isValidName(String name) {
    Matcher m = namePattern.matcher(name);
    return m.matches();
  }

  /**
   *  Validator for Symbol and Keyword namespaces.
   */
  Pattern namespacePattern =
      Pattern.compile(String.format("^(%s)$", _SIMPLE_SYMBOL));

  static boolean isValidNamespace(String ns) {
    Matcher m = namespacePattern.matcher(ns);
    return m.matches();
  }

  Pattern symbolPattern =
      Pattern.compile(QUALIFIED_SYMBOL);

  /**
   * Validator for full Name strings.
   */
  static boolean isValidFullName(String name) {
    Matcher m = symbolPattern.matcher(name);
    return m.matches();
  }

  default boolean namesEqual(T other) {
    return getName().equals(other.getName());
  }

  default boolean namespacesEqual(T other) {
    return ((getNamespace() == null && other.getNamespace() == null)
        || (getNamespace().equals(other.getNamespace())));
  }
}
