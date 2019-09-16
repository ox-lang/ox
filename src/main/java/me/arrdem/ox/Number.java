package me.arrdem.ox;

public interface Number<T extends Number> extends Comparable<T> {
  T add(T other);
  T subtract(T other);
  T multiply(T other);
  T divide(T other);
}
