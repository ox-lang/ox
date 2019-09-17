package me.arrdem.ox

import org.junit.Test
import java.math.BigInteger
import kotlin.random.Random
import kotlin.test.assertEquals

class NaturalTest {
  val workFactor: Int = 1000

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
    for (i in 0..workFactor)
      assertEquals(Nat.of(i.toLong()), degenerateOf(i.toLong()))

    val random = Random(System.currentTimeMillis())
    for (i in 1..workFactor) {
      val v = rint(random)
      assertEquals(Nat.of(v), degenerateOf(v))
      assertEquals(degenerateOf(v), Nat.of(v))
    }
  }

  @Test
  fun testDegenerateAddition() {
    val random = Random(System.currentTimeMillis())
    for (i in 1..workFactor) {
      val a = rint(random)
      val b = rint(random)
      val sum = a + b

      println("$a + $b = $sum")

      assertEquals(Nat.of(a).add(Nat.of(b)), Nat.of(sum));
      assertEquals(degenerateOf(a).add(Nat.of(b)), Nat.of(sum));
      assertEquals(Nat.of(a).add(degenerateOf(b)), Nat.of(sum));
      assertEquals(degenerateOf(a).add(degenerateOf(b)), Nat.of(sum));
    }
  }

  @Test
  fun testDegenerateSubtraction() {
    val random = Random(System.currentTimeMillis())
    for (i in 1..workFactor) {
      var a = rint(random)
      var b = rint(random)

      // Ensure that A is greater
      if(a < b) {
        val t = a
        a = b
        b = t
      }
      val difference = a.subtract(b)

      println("$a - $b = $difference")

      assertEquals(Nat.of(a).subtract(Nat.of(b)), Nat.of(difference));
      assertEquals(Nat.of(a).subtract(degenerateOf(b)), Nat.of(difference));
      assertEquals(degenerateOf(a).subtract(Nat.of(b)), Nat.of(difference));
      assertEquals(degenerateOf(a).subtract(degenerateOf(b)), Nat.of(difference));
    }
  }

  @Test
  fun testDegenerateMultiplication() {
    val random = Random(System.currentTimeMillis())
    for (i in 1..workFactor) {
      var a = rint(random, 100)
      var b = rint(random, 100)
      val product = a.multiply(b)

      println("$a * $b = $product")

      assertEquals(Nat.of(a).multiply(Nat.of(b)), Nat.of(product))
      assertEquals(degenerateOf(a).multiply(Nat.of(b)).get(), product)
      assertEquals(Nat.of(a).multiply(degenerateOf(b)), Nat.of(product))
      assertEquals(degenerateOf(a).multiply(degenerateOf(b)), Nat.of(product))
    }
  }

  @Test
  fun testDegenerateDivision() {
    val random = Random(System.currentTimeMillis())
    for (i in 1..workFactor) {
      var a = rint(random).inc()
      var b = rint(random).inc()

      // Ensure that A is greater
      if(a < b) {
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

  @Test
  fun testDegenerateRemainder() {
    val random = Random(System.currentTimeMillis())
    for (i in 1..workFactor) {
      var a = rint(random).inc()
      var b = rint(random).inc()

      // Ensure that A is greater
      if(a < b) {
        val t = a
        a = b
        b = t
      }
      val rem = a.remainder(b)

      println("$a % $b = $rem")

      assertEquals(Nat.of(a).remainder(Nat.of(b)), Nat.of(rem));
      assertEquals(Nat.of(a).remainder(degenerateOf(b)), Nat.of(rem));
      assertEquals(degenerateOf(a).remainder(Nat.of(b)), Nat.of(rem));
      assertEquals(degenerateOf(a).remainder(degenerateOf(b)), Nat.of(rem));
    }
  }
}
