package org.oxlang.lang;

import io.lacuna.bifurcan.List;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by arrdem on 6/11/17.
 *
 * Describes a mechanism for identifying and loading packages from more than one location. Conflicts (multiple sources)
 * in loading a package are not allowed, and are cause for PackageResolutionExceptions.
 *
 * This whole thing is basically just flatmap.
 */
public class PackageChainResolver extends PackageResolver {
  public static final class PackageResolverConflictException extends PackageResolverException {
    public PackageResolverConflictException(String message) {
      super(message);
    }

    public PackageResolverConflictException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  private final PackageResolver[] resolvers;

  public PackageChainResolver(PackageResolver ... resolvers) {
    this.resolvers = resolvers;
  }

  @Override
  public boolean exists(GroupIdentifier gid) {
    return Stream.of(resolvers)
            .anyMatch(r -> r.exists(gid));
  }

  @Override
  public boolean exists(PackageIdentifier pid) {
    return Stream.of(resolvers)
            .anyMatch(r -> r.exists(pid));
  }

  @Override
  public boolean exists(PackageVersionIdentifier pvid) {
    return Stream.of(resolvers)
            .anyMatch(r -> r.exists(pvid));
  }

  @NotNull
  @Override
  public Stream<GroupIdentifier> groups() {
    return Stream.of(resolvers)
            .flatMap(r -> r.groups())
            .distinct();
  }

  @NotNull
  @Override
  public Stream<PackageIdentifier> groupPackages(GroupIdentifier id) throws PackageResolverException {
    return Stream.of(resolvers)
            .flatMap(r -> r.safeGroupPackages(id))
            .distinct();
  }

  @NotNull
  @Override
  public Stream<PackageVersionIdentifier> packageVersions(PackageIdentifier pid) throws PackageResolverException {
    return Stream.of(resolvers)
            .flatMap(r -> r.safePackageVersions(pid))
            .distinct();
  }

  @NotNull
  @Override
  public PrePackage getPackage(PackageVersionIdentifier pvid) throws PackageResolverException {
    PrePackage[] packages = (PrePackage[]) Stream.of(resolvers)
            .map(r -> {
              try {
                return Optional.of(r.getPackage(pvid));
              } catch (PackageResolverException e) {
                return Optional.empty();
              }
            })
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toArray();

    if (packages.length != 1)
      throw new PackageResolverConflictException(
          String.format("Found %d sources of package %s!", packages.length, pvid));

    return packages[0];
  }
}
