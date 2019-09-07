package io.oxlang

import io.lacuna.bifurcan.List.of
import io.lacuna.bifurcan.Lists
import io.lacuna.bifurcan.Sets
import kotlin.test.assertEquals
import org.junit.Test

class ReaderTest {
  fun read(input: String, exId: String): Any? {
    return Readers.read(input, exId)
  }

  @Test fun testReadList() {
    assertEquals(Lists.EMPTY, read("()", "test-0"))
    assertEquals(List.of(null), read("(null)", "test-0"))
    assertEquals(List.of(Keywords.of("foo")), read("(:foo)", "test-0"))
    assertEquals(List.of(Lists.EMPTY, Lists.EMPTY, Lists.EMPTY), read("(()()())", "test-0"))
  }

  @Test fun testReadSet() {
    assertEquals(Sets.EMPTY, read("#{}", "test-1"))
    assertEquals(Set.of(1, 2, 3), read("#{1, 2, 3}", "test-1"))
  }

  @Test fun testReadSymbol() {
    assertEquals(Symbols.of("foo"), read("foo", "test-1"))
    assertEquals(Symbols.of("foo.bar/baz"), read("foo.bar/baz", "test-1"))
    assertEquals(Symbols.of("foo bar/baz"), read("|foo bar/baz|", "test-1"))
    assertEquals(Symbols.of("+foo-bar-baz+"), read("+foo-bar-baz+", "test-1"))
  }
}
