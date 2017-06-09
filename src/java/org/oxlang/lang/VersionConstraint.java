package org.oxlang.lang;

/**
 * Created by arrdem on 6/8/17.
 */
public abstract class VersionConstraint {
  public abstract boolean matches(ConcreteVersion v);
}
