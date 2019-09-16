/**
 * @author Reid 'arrdem' McKenzie 2019-9-13
 *
 * The natural numbers.
 *
 * Inspired by 'What about the natural numbers' ~ Runciman 1989
 *   https://dl.acm.org/citation.cfm?id=66989
 *   http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.56.3442&rep=rep1&type=pdf
 *
 * With thanks to José Manuel Calderón Trilla who presented this paper at PWLConf 2019.
 *   https://pwlconf.org/2019/jose-trilla/
 *
 * Provides an implementation of REALLY BIG NUMBERS modeled as a pair (BigInteger, [thunk]) where the
 * BigInt holds the realized portion of the number, and the thunks are continuations which
 * will produce addends when called. This allows the value of the Nat to be incrementally realized.
 *
 * Nat implements the usual operations - addition, subtraction (saturating at zero), multiplication
 * and division (with div/0 defined to be infinity per Runciman).
 */

package me.arrdem.ox;

import io.lacuna.bifurcan.IList;
import io.lacuna.bifurcan.Lists;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public final class Nat implements Number<Nat> {
  private static IList<Function0<Nat>> FEL = (IList<Function0<Nat>>) Lists.EMPTY;

  private BigInteger value;
  private IList<Function0<Nat>> thunks;

  public static Nat ZERO = Nat.of(BigInteger.ZERO);
  public static Nat ONE = Nat.of(BigInteger.ONE);
  public static Nat TWO = Nat.of(BigInteger.TWO);
  public static Nat TEN = Nat.of(BigInteger.TEN);
  public static Nat INFINITY = Nat.of(BigInteger.valueOf(Long.MAX_VALUE), () -> Nat.INFINITY);

  private Nat(BigInteger value, IList<Function0<Nat>> thunks) {
    assert value.compareTo(BigInteger.ZERO) >= 0;
    this.value = value;
    this.thunks = thunks;
  }

  public static Nat of(long value) {
    return new Nat(BigInteger.valueOf(value), FEL);
  }

  public static Nat of(BigInteger value) {
    return new Nat(value, FEL);
  }

  public static Nat of(long value, Function0<Nat> thunk) {
    if (!(thunk instanceof MemFn0))
      thunk = MemFns.of(thunk);

    Function0<Nat> finalThunk = thunk;
    return new Nat(BigInteger.valueOf(value), FEL.addLast(() -> {
      Object v = finalThunk.invoke();
      assert v instanceof Nat : "Contract violation! Thunk did not return nat!";
      return (Nat) v;
    }));
  }

  public static Nat of(BigInteger value, Function0<Nat> thunk) {
    if (!(thunk instanceof MemFn0))
      thunk = MemFns.of(thunk);

    Function0<Nat> finalThunk = thunk;
    return new Nat(value, FEL.addLast(() -> {
      Object v = finalThunk.invoke();
      assert v instanceof Nat : "Contract violation! Thunk did not return nat!";
      return (Nat) v;
    }));
  }

  public Nat succ() {
    return new Nat(BigInteger.ONE, FEL.addLast(() -> this));
  }

  public Nat add(int other) {
    if (other == 0) return this;
    else return new Nat(BigInteger.valueOf(other), FEL.addLast(() -> this));
  }

  public Nat add(long other) {
    return new Nat(BigInteger.valueOf(other), FEL.addLast(() -> this));
  }

  public Nat add(BigInteger other) {
    return new Nat(other, FEL.addLast(() -> this));
  }

  public Nat add(Nat other) {
    return new Nat(this.value.add(other.value), Lists.concat(this.thunks, other.thunks));
  }

  private boolean maybeStep() {
    if(!this.thunks.equals(FEL)) {
      Function0<Nat> thunk = this.thunks.first();
      this.thunks = this.thunks.removeFirst();

      Nat next = thunk.invoke();
      if (next != null) {
        this.value = this.value.add(next.value);
        if(!next.thunks.equals(FEL))
          this.thunks = Lists.concat(this.thunks, next.thunks);

        return true;
      }
    }
    return false;
  }

  public Nat decs() {
    return this.subtract(ONE);
  }

  public Nat subtract(int other) {
    return this.subtract(Nat.of(other));
  }

  public Nat subtract(long other) {
    return this.subtract(Nat.of(other));
  }

  public Nat subtract(BigInteger other) {
    return this.subtract(Nat.of(other));
  }

  /**
   * Subtraction is implemented by LAZILY evaluating both ths and other until either other has been
   * fully evaluated, or the subtrand has been reduced to zero.
   *
   * Note that this is LAZY subtraction, so it WILL loop given infinite structures.
   */
  public Nat subtract(Nat other) {
    while(true) {
      // If the other side is smaller and it can be stepped, step it.
      if(!other.thunks.equals(Lists.EMPTY) && other.value.compareTo(this.value) <= 0)
        other.maybeStep();

        // If our side is smaller and it can be stepped, step it
      else if (!this.thunks.equals(Lists.EMPTY) && this.value.compareTo(other.value) <= 0)
        this.maybeStep();

        // If we've grounded out and one side is fully evaluated, do the math.
      else if (this.thunks.equals(Lists.EMPTY) || other.thunks.equals(Lists.EMPTY)) {
        if (this.value.compareTo(other.value) > 0)
          return new Nat(this.value.subtract(other.value), this.thunks);
        else
          return ZERO;
      }
    }
  }

  public Nat multiply(int other) {
    return this.multiply(Nat.of(other));
  }

  public Nat multiply(long other) {
    return this.multiply(Nat.of(value));
  }

  public Nat multiply(BigInteger other) {
    return this.multiply(Nat.of(value));
  }

  /**
   * Multiplication is tricky. Product of sums is sum of products, so one possible coding here is
   * to maintain a sequence of factors against which the Nat has been multiplied much like the
   * sequence of unrealized addends. Under this coding the tricky bit is as usual when does it
   * make sense to step what terms. Multiplying infinities for instance is a really essential case
   * which should be possible in a small amount of time - not a divergent computation.
   *
   * So the coding we use here is lazy iterated addition. We construct a new nat defined by
   * recursively (lazily!) adding this to itself other times. This strategy provides the incremental
   * realization behavior we want although perhaps not the performance one may wish.
   */
  public Nat multiply(Nat other) {
    // Multiplying by zero is the base case and zero
    if(this.equals(ZERO) || (other.thunks.equals(FEL) && other.value.equals(0)))
      return ZERO;

    else if(other.equals(ONE))
      return this;

    // Multiplying realized numbers is done eagerly
    else if (other.thunks.equals(FEL) && this.thunks.equals(FEL))
      return Nat.of(this.value.multiply(other.value));

    // Otherwise we use the recursive lazy coding
    else
      return this.add(this).add(Nat.of(0, () -> this.multiply(other.subtract(TWO))));
  }

  /**
   * Division is slightly less tricky - Division by zero is well defined (to be infinity). The
   * machine number case of division is just delegation, and the general case is iterated
   * subtraction. That is - since quotient * divisor <= dividend we can compute the dividend by
   * counting the number of times we can subtract the divisor from the dividend.
   *
   * Consequently this implementation uses a helper function which constructs a thunk which tracks
   * the (remaining) dividend (as current) and the divisor (as other). Each thunk call produces a 1
   * with a new thunk reduced by the divisor - so this way we're incrementally counting down. Not
   * ideal - but correct for deeply thunked structures eg the minimal infinity.
   */
  private static final Function2<Nat, Nat, Function0<Nat>> f = ((Nat current, Nat other) -> () -> {
    Nat difference = current.subtract(other);
    if (difference.compareTo(ZERO) > 0)
      return Nat.f.invoke(difference, other).invoke();

    else
      return ZERO;
  });

  public Nat divide(Nat other) {
    if(other.equals(ZERO))
      return INFINITY;

    else if(this.equals(ZERO))
      return ZERO;

    else if(this.thunks.equals(FEL) && other.thunks.equals(FEL))
      return Nat.of(this.value.divide(other.value));

    else
      return f.invoke(this, other).invoke();
  }

  /**
   * Compute the remainder (modulus) of the division of this by other.
   */
  public Nat remainder(Nat other) {
    return ZERO;
  }

  /**
   * A way to force the entire (potentially infinite!) value of the current Nat.
   */
  public BigInteger get() {
    while(this.maybeStep()) {}
    return this.value;
  }

  /**
   * Comparison. Which is hard because we want to realize as little of either side  as we can
   * possibly get away with.
   */
  @Override
  public int compareTo(@NotNull Nat other) {
    if(this == other)
      return 0;

    while(true) {
      // We've exhausted this value, other has more AND is gt -> lt
      if (this.thunks.equals(Lists.EMPTY) &&
          this.value.compareTo(other.value) < 0)
        return -1;

      // We've exhausted the other, and other's lt -> we're gt
      else if (other.thunks.equals(Lists.EMPTY) &&
               this.value.compareTo(other.value) > 0)
        return 1;

      // We've exhausted both
      else if (this.thunks.equals(Lists.EMPTY) &&
               other.thunks.equals(Lists.EMPTY))
        return this.value.compareTo(other.value);

      else {
        // If the other side is smaller and it can be stepped, step it.
        if (!other.thunks.equals(Lists.EMPTY) &&
            other.value.compareTo(this.value) <= 0)
          other.maybeStep();

          // If our side is smaller and it can be stepped, step it
        else if (!this.thunks.equals(Lists.EMPTY) &&
                 this.value.compareTo(other.value) <= 0)
          this.maybeStep();
      }
    }
  }

  public boolean equals(Object other) {
    if (this == other)
      return true;

    if (!(other instanceof Nat))
      return false;

    Nat o = (Nat) other;
    return this.compareTo(o) == 0;
  }

  public String toString() {
    return String.format("<Nat %s, more?=%s>",
      this.value.toString(10),
      !this.thunks.equals(Lists.EMPTY));
  }
}
