package org.oxlang.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class NameTest {
  @Test
  public void testIsValidName() throws Exception {
    assertEquals(true, Named.isValidName("foo"));
    assertEquals(true, Named.isValidName("a16z"));
    assertEquals(true, Named.isValidName("foo/bar"));

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
  }

  @Test
  public void testIsValidNamespace() throws Exception {
    assertEquals(true, Named.isValidNamespace("foo"));
    assertEquals(true, Named.isValidNamespace("a16z"));
    assertEquals(true, Named.isValidNamespace("foo.bar"));

    assertEquals(false, Named.isValidNamespace("foo/bar"));
    assertEquals(false, Named.isValidNamespace("foo.bar/baz"));
  }

}
