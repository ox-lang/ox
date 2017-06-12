package org.oxlang.lang;

import io.lacuna.bifurcan.IMap;
import io.lacuna.bifurcan.List;
import io.lacuna.bifurcan.Map;
import io.lacuna.bifurcan.Maps;
import org.jetbrains.annotations.NotNull;
import org.oxlang.data.SimpleSymbol;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Created by arrdem on 6/3/17.
 * <p>
 * Provides a way to map package identifiers to resources (whether files or buffers) which may be
 * loaded.
 * <p>
 * Examples will (presumably) include resolving packages from an FTP/HTTP(S) service, and from
 * filesystem caches.
 */
public abstract class PackageResolver {

  /**
   * Base exception instance for things that go wrong in package resolution.
   */
  public static class PackageResolverException extends Exception {
    public PackageResolverException(String message) {
      super(message);
    }

    public PackageResolverException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * Exception thrown when the group of either a group, package or package version cannot be found.
   */
  public static class NoSuchGroupException extends PackageResolverException {
    public NoSuchGroupException(String message) {
      super(message);
    }

    public NoSuchGroupException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * Exception thrown when the package of a package or package version cannot be found.
   */
  public static class NoSuchPackageException extends PackageResolverException {
    public NoSuchPackageException(String message) {
      super(message);
    }

    public NoSuchPackageException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * Exception thrown when a version of a package cannot be found.
   */
  public static class NoSuchVersionException extends PackageResolverException {
    public NoSuchVersionException(String message) {
      super(message);
    }

    public NoSuchVersionException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  public static class NoSuchNamespaceException extends PackageResolverException {
    public NoSuchNamespaceException(String message) {
      super(message);
    }

    public NoSuchNamespaceException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * A base class for logical handles to packages. Provides the requisite machinery for reading source from whatever
   * the archive implementation may be, and for listing the namespaces and dependencies of the archive.
   *
   * The goal of this class is to provide an interface which sufficiently generic to describe both package stores
   * backed by straight filesystem source trees as well as archive files and network resources.
   */
  public static abstract class PrePackage {
    public final PackageVersionIdentifier id;

    public PrePackage(PackageVersionIdentifier id) {
      this.id = id;
    }

    public abstract Iterable<PackageIdentifier> getDependencies() throws IOException;
    public abstract Iterable<NamespaceIdentifier> getNamespaces() throws IOException;
    public abstract Readable getNamespaceSource() throws PackageResolverException, IOException;
  }

  public abstract boolean exists(GroupIdentifier gid);

  public abstract boolean exists(PackageIdentifier pid);

  public abstract boolean exists(PackageVersionIdentifier pvid);

  @NotNull
  public abstract List<GroupIdentifier> listGroups();

  @NotNull
  public abstract List<PackageIdentifier> listGroup(GroupIdentifier id)
      throws NoSuchGroupException;

  @NotNull
  public abstract List<PackageVersionIdentifier> listVersions(PackageIdentifier pid)
      throws NoSuchGroupException, NoSuchPackageException;

  @NotNull
  public abstract PrePackage getPackage(PackageVersionIdentifier pvid)
      throws NoSuchGroupException, NoSuchPackageException, NoSuchVersionException;
}
