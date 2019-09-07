package io.oxlang

import io.lacuna.bifurcan.Lists
import io.lacuna.bifurcan.Maps
import io.lacuna.bifurcan.Sets
import org.junit.Test
import kotlin.test.assertEquals

class ReaderTest {
  fun read(input: String, exId: String): Any? {
    return Readers.read(input, exId)
  }

  @Test fun testReadList() {
    assertEquals(Lists.EMPTY, read("()", "test-0"))
    assertEquals(List.of(null), read("(null)", "test-0"))
    assertEquals(List.of(Keywords.of("foo")), read("(:foo\n; inline comment\n)", "test-0"))
    assertEquals(List.of(Lists.EMPTY, Lists.EMPTY, Lists.EMPTY), read("(()()())", "test-0"))
  }

  @Test fun testReadSet() {
    assertEquals(Sets.EMPTY, read("#{}", "test-1"))
    assertEquals(Set.of(1, 2, 3), read("#{1, 2, 3}", "test-1"))
  }

  @Test fun testReadMap() {
    assertEquals(Maps.EMPTY, read("{}", "test-2"))
    assertEquals(Maps.EMPTY
      .put(Keywords.of("foo"), 1)
      .put(Keywords.of("bar"), 2),
      read("{:foo 1, :bar 2\n; inline comment\n}", "test-2"))
  }

  @Test fun testReadSymbol() {
    assertEquals(Symbols.of("foo"), read("foo", "test-3"))
    assertEquals(Symbols.of("foo.bar/baz"), read("foo.bar/baz", "test-3"))
    assertEquals(Symbols.of("foo bar/baz"), read("|foo bar/baz|", "test-3"))
    assertEquals(Symbols.of("+foo-bar-baz+"), read("+foo-bar-baz+", "test-3"))
  }

  @Test fun testReadKeyword() {
    assertEquals(Keywords.of("foo"), read("::foo", "test-4"))
    assertEquals(Keywords.of("foo.bar/baz"), read("::foo.bar/baz", "test-4"))
    assertEquals(Keywords.of("foo bar/baz"), read("::|foo bar/baz|", "test-4"))
    assertEquals(Keywords.of("+foo-bar-baz+"), read("::+foo-bar-baz+", "test-4"))
  }

  @Test fun testReadNumber() {
    assertEquals(1, read("1", "test-5"))
  }

  @Test fun testReadString() {
    assertEquals("foo bar baz", read("\"foo bar baz\"", "test-6"))
    assertEquals("\n\r\t\b\\", read("\"\n\r\t\b\\\\\"", "test-6"))
    assertEquals("\n\r\t\b\\", read("\"\\n\\r\\t\\b\\\\\"", "test-6"))
  }
}
