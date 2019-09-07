package io.oxlang

import io.lacuna.bifurcan.Lists
import io.lacuna.bifurcan.Maps
import io.lacuna.bifurcan.Sets
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class PrinterTest {
  fun readPrintReadTest(text: String) {
    val v = Readers.read(text, "in")
    assertEquals(Readers.read(Printers.pr(v), "in2"), v)
  }

  @Test fun testRprNum() {
    readPrintReadTest(Long.MAX_VALUE.toString())
    readPrintReadTest(Long.MIN_VALUE.toString())
  }

  @Test fun testRprSymbol() {
    readPrintReadTest("foo.bar/baz")
    readPrintReadTest("|foo bar/baz|")
  }

  @Test fun testRprKw() {
    readPrintReadTest(":foo.bar/baz")
    readPrintReadTest(":|foo bar/baz|")
  }

  @Test fun testRprList() {
    readPrintReadTest("(() (() ()))")
  }

  @Test fun testRprMap() {
    readPrintReadTest("{:foo 1, :bar 2}")
  }

  @Test fun testRprSet() {
    readPrintReadTest("#{1, 2, 3, 4, 5}")
  }

  @Test fun testRprString() {
    readPrintReadTest("\"foo bar \n \r \t \\f \\b \\\\ \\\" \"")
  }
}
