package org.oxlang.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class NameTest {
  @Test
  public void testIsValidName() throws Exception {
    assertEquals(true, Named.isValidName("foo"));
    assertEquals(true, Named.isValidName("a16z"));

    // Fully qualified names are not legal names
    assertEquals(false, Named.isValidName("foo/bar"));

    assertEquals(false, Named.isValidName("("));
    assertEquals(false, Named.isValidName("aaaaaa("));

    assertEquals(false, Named.isValidName(")"));
    assertEquals(false, Named.isValidName("aaaaaa)"));

    assertEquals(false, Named.isValidName("}"));
    assertEquals(false, Named.isValidName("aaaaaaa}"));

    assertEquals(false, Named.isValidName("{"));
    assertEquals(false, Named.isValidName("aaaaaaa{"));

    assertEquals(false, Named.isValidName("["));
    assertEquals(false, Named.isValidName("aaaaaaa["));

    assertEquals(false, Named.isValidName("]"));
    assertEquals(false, Named.isValidName("aaaaaaa]"));

    assertEquals(false, Named.isValidName(":foo"));

    assertEquals(false, Named.isValidName("#"));

    assertEquals(false, Named.isValidName(";"));

    assertEquals(false, Named.isValidName("something with\twhitespace\n"));
  }

  @Test
  public void testIsValidNamespace() throws Exception {
    assertEquals(true, Named.isValidNamespace("foo"));
    assertEquals(true, Named.isValidNamespace("a16z"));
    assertEquals(true, Named.isValidNamespace("foo.bar"));

    assertEquals(false, Named.isValidNamespace("foo/bar"));
    assertEquals(false, Named.isValidNamespace("foo.bar/baz"));
  }

  @Test
  public void testIsValidFullName() throws Exception {
    assertEquals(true, Named.isValidFullName("foo"));
    assertEquals(true, Named.isValidFullName("foo1"));
    assertEquals(true, Named.isValidFullName("fo@o")); // For now @ is a legal sym chr.
    assertEquals(true, Named.isValidFullName("foo/bar")); // legal full name
    assertEquals(true, Named.isValidFullName("foo//")); // '/ is the name, 'foo' is the ns
    assertEquals(false, Named.isValidFullName("foo///")); // '//' is not a legal sym
    assertEquals(true, Named.isValidFullName("/")); // '/' alone is legal
  }
}
