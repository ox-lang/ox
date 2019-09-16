package me.arrdem.ox

import io.lacuna.bifurcan.Lists
import io.lacuna.bifurcan.Maps
import io.lacuna.bifurcan.Sets
import org.junit.Test
import java.math.BigInteger
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class NaturalTest {
  private fun rint(r: Random): BigInteger {
    val l = r.nextLong(0, 10000)
    if(l < 0)
      return BigInteger.valueOf(l * -1);
    else
      return BigInteger.valueOf(l);
  }

  private fun rint(r: Random, max: Long): BigInteger {
    val l = r.nextLong(0, max)
    if(l < 0)
      return BigInteger.valueOf(l * -1);
    else
      return BigInteger.valueOf(l);
  }

  fun degenerateOf(value: Long, step: Long = 1): Nat {
    return if (value <= 0L)
      Nat.ZERO
    else
      Nat.of(1) { degenerateOf(value - step) }
  }

  fun degenerateOf(value: BigInteger): Nat {
    return if (value == BigInteger.ZERO)
      Nat.ZERO
    else
      Nat.of(1) { degenerateOf(value.subtract(BigInteger.ONE)) }
  }


  @Test
  fun testIdentity() {
    for (i in 0..1000)
      assertEquals(Nat.of(i.toLong()), degenerateOf(i.toLong()))

    val random = Random(System.currentTimeMillis())
    for (i in 1..1000) {
      val v = rint(random)
      assertEquals(Nat.of(v), degenerateOf(v))
      assertEquals(degenerateOf(v), Nat.of(v))
    }
  }

  @Test
  fun testDegenerateAddition() {
    val random = Random(System.currentTimeMillis())
    for (i in 1..1000) {
      val a = rint(random)
      val b = rint(random)
      val sum = a + b

      assertEquals(Nat.of(a).add(Nat.of(b)), Nat.of(sum));
      assertEquals(degenerateOf(a).add(Nat.of(b)), Nat.of(sum));
      assertEquals(Nat.of(a).add(degenerateOf(b)), Nat.of(sum));
      assertEquals(degenerateOf(a).add(degenerateOf(b)), Nat.of(sum));
    }
  }

  @Test
  fun testDegenerateSubtraction() {
    val random = Random(System.currentTimeMillis())
    for (i in 1..1000) {
      var a = rint(random)
      var b = rint(random)

      // Ensure that A is greater
      if(a.compareTo(b) < 0) {
        val t = a
        a = b
        b = t
      }
      val difference = a.subtract(b)

      assertEquals(Nat.of(a).subtract(Nat.of(b)), Nat.of(difference));
      assertEquals(Nat.of(a).subtract(degenerateOf(b)), Nat.of(difference));
      assertEquals(degenerateOf(a).subtract(Nat.of(b)), Nat.of(difference));
      assertEquals(degenerateOf(a).subtract(degenerateOf(b)), Nat.of(difference));
    }
  }

  @Test
  fun testDegenerateMultiplication() {
    val random = Random(System.currentTimeMillis())
    for (i in 1..1000) {
      var a = rint(random, 100)
      var b = rint(random, 100)
      val product = a.multiply(b)

      println("$a * $b = $product")

      assertEquals(Nat.of(a).multiply(Nat.of(b)), Nat.of(product))
      //assertEquals(degenerateOf(a).multiply(Nat.of(b)).get(), product)
      assertEquals(Nat.of(a).multiply(degenerateOf(b)), Nat.of(product))
      //assertEquals(degenerateOf(a).multiply(degenerateOf(b)), Nat.of(product))
    }
  }

  @Test
  fun testDegenerateDivision() {
    val random = Random(System.currentTimeMillis())
    for (i in 1..1000) {
      var a = rint(random)
      var b = rint(random)

      // Ensure that A is greater
      if(a.compareTo(b) < 0) {
        val t = a
        a = b
        b = t
      }
      val dividend = a.divide(b)

      println("$a / $b = $dividend")

      assertEquals(Nat.of(a).divide(Nat.of(b)), Nat.of(dividend));
      assertEquals(Nat.of(a).divide(degenerateOf(b)), Nat.of(dividend));
      assertEquals(degenerateOf(a).divide(Nat.of(b)), Nat.of(dividend));
      assertEquals(degenerateOf(a).divide(degenerateOf(b)), Nat.of(dividend));
    }
  }
}
