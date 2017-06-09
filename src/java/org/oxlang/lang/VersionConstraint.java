package org.oxlang.lang;

/**
 * Created by rmckenzie on 6/8/17.
 */
public abstract class VersionConstraint {
  public abstract boolean matches(ConcreteVersion v);
}
