package org.oxlang.lang;

/**
 * Created by arrdem on 6/8/17.
 */
public abstract class PackageVersionConstraint {
  public abstract boolean matches(ConcretePackageVersion v);
}
