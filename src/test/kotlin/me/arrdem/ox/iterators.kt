package me.arrdem.ox

import org.junit.Test
import java.util.Iterator;
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CurrentIteratorTest {
  @Test
  fun easyTest() {
    var iter = CurrentIterator(List.of(1, 2, 3, 4, 5).iterator() as Iterator<Int>)
    assertTrue(iter.hasNext())
    assertEquals(iter.current(), 1)
    assertEquals(iter.next(), 2)
    assertEquals(iter.current(), 2)
    assertEquals(iter.current(), 2)

    while(iter.hasNext()) {
      assertEquals(iter.next(), iter.current())
    }
  }
}

class PeekIteratorTest {
  @Test
  fun easyTest() {
    var iter = PeekIterator(List.of(1, 2, 3, 4, 5).iterator() as Iterator<Int>)
    assertTrue(iter.hasNext())
    while(iter.hasNext()) {
      val pv = iter.peek()
      val nv = iter.next()
      val cv = iter.current()
      assertEquals(pv, nv)
      assertEquals(nv, cv)
      assertEquals(pv, cv)
    }
  }
}
