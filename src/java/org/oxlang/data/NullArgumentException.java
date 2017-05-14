package org.oxlang.data;

/**
 * Created by rmckenzie on 5/13/17.
 *
 * Special case of IllegalArgumentException which represents an illegal null.
 */
public class NullArgumentException extends IllegalArgumentException {
  public NullArgumentException(String s) {
    super(s);
  }
}
