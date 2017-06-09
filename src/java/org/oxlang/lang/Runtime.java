package org.oxlang.lang;

import io.lacuna.bifurcan.*;
import org.jetbrains.annotations.NotNull;
import org.oxlang.data.SimpleSymbol;
import org.oxlang.data.Symbol;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * Created by arrdem on 5/13/17.
 */
public class Runtime {
  static final Symbol NS_SYM = Symbol.of("ns");
  static final Symbol REQUIRE_SYM = Symbol.of("require");
  static final Symbol AS_SYM = Symbol.of("as");
  static final Symbol REFER_SYM = Symbol.of("refer");
  static final Symbol RENAME_SYM = Symbol.of("rename");

  /**
   * A superclass for errors which occur during analysis time.
   */
  public static class AnalysisException extends Exception {
    public AnalysisException(String message) {
      super(message);
    }

    public AnalysisException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * Exception thrown when the analyzer doesn't get a leading NS, or the NS's name was wrong.
   */
  public static class IllegalNsException extends AnalysisException {
    public IllegalNsException(String message) {
      super(message);
    }

    public IllegalNsException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  public static class IllegalRequireException extends IllegalNsException {
    public IllegalRequireException(String message) {
      super(message);
    }

    public IllegalRequireException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  public static class IllegalRequireManipulationException extends IllegalRequireException {
    public IllegalRequireManipulationException(String message) {
      super(message);
    }

    public IllegalRequireManipulationException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  private final PackageResolver resolver;
  private final Map<PackageIdentifier, Package> packages;

  public Runtime(PackageResolver resolver, Map<PackageIdentifier, Package> packages) {
    this.packages = packages;
    this.resolver = resolver;
  }

  public static Runtime of(PackageResolver r, PackageIdentifier ... packages) {
    return null;
  }


}
