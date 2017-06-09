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

  /**
   * A value class containing the name of the package, and the list of Paths
   * which contain code
   */
  public static class PrePackage {
    public final PackageIdentifier name;
    public final Map<NamespaceIdentifier, Path> namespaces;

    public PrePackage(@NotNull PackageIdentifier name,
                      @NotNull Map<NamespaceIdentifier, Path> namespaces) {
      this.name = name;
      this.namespaces = namespaces;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      PrePackage that = (PrePackage) o;

      if (!name.equals(that.name)) return false;
      return namespaces.equals(that.namespaces);
    }

    @Override
    public int hashCode() {
      int result = name.hashCode();
      result = 31 * result + namespaces.hashCode();
      return result;
    }
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
  public abstract PrePackage resolveContents(PackageVersionIdentifier pvid)
      throws NoSuchGroupException, NoSuchPackageException;
}
