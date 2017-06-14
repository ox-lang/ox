package org.oxlang.data;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Created by arrdem on 5/23/17.
 *
 * Encodes the possibility space of having a non-nil reference to an A, or to a B. One of the two must be present.
 */
public class Either<A, B> {
  public final Optional<A> left;
  public final Optional<B> right;

  public Either(@NotNull Optional<A> left,
                @NotNull Optional<B> right)
      throws IllegalArgumentException
  {
    if (left == null)
      throw new IllegalArgumentException("Got illegal `nil` argument for `a`!");

    if (right == null)
      throw new IllegalArgumentException("Got illegal `nil` argument for `b`!");

    if (left.isPresent() ^ right.isPresent()) { // xor FTW
      this.left = left;
      this.right = right;
    } else {
      throw new IllegalArgumentException(
          String.format("Only one of `a` or `b` may be present! got (%s, %s)",
              left.isPresent(), right.isPresent()));
    }
  }

  /**
   * @return an Either with the left and right swapped.
   */
  public Either<B, A> swap() {
    return new Either<>(right, left);
  }

  public A right() {
    return (A) right.get();
  }

  public B left() {
    return (B) left.get();
  }
}
