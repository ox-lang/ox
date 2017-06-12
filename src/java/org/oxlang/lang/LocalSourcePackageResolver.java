package org.oxlang.lang;

import io.lacuna.bifurcan.IMap;
import io.lacuna.bifurcan.Lists;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Created by arrdem on 6/12/17.
 *
 * A class representing a single package loaded from a known filesystem location.
 */
public class LocalSourcePackageResolver extends PackageResolver {
  @NotNull
  private final Path root;
  @NotNull
  private final PackageVersionIdentifier id;
  private PrePackage pkg = null;

  public LocalSourcePackageResolver(@NotNull Path root,
                                    @NotNull PackageVersionIdentifier id) {
    this.root = root;
    this.id = id;
  }

  @Override
  public boolean exists(GroupIdentifier gid) {
    return gid.equals(this.id.pkg.group);
  }

  @Override
  public boolean exists(PackageIdentifier pid) {
    return pid.equals(this.id.pkg);
  }

  @Override
  public boolean exists(PackageVersionIdentifier pvid) {
    return pvid.equals(this.id);
  }

  @NotNull
  @Override
  public Stream<GroupIdentifier> groups() {
    return Lists.EMPTY.addFirst(this.id.pkg.group).stream();
  }

  @NotNull
  @Override
  public Stream<PackageIdentifier> groupPackages(GroupIdentifier id) throws PackageResolverException {
    if (exists(id)) {
      return Lists.EMPTY.addFirst(this.id.pkg).stream();
    } else {
      return Lists.EMPTY.stream();
    }
  }

  @NotNull
  @Override
  public Stream<PackageVersionIdentifier> packageVersions(PackageIdentifier pid) throws PackageResolverException {
    if (exists(pid)) {
      return Lists.EMPTY.addFirst(this.id).stream();
    } else {
      return Lists.EMPTY.stream();
    }
  }

  @NotNull
  @Override
  public PrePackage getPackage(PackageVersionIdentifier pvid) throws PackageResolverException {
    if (!exists(pvid))
      throw new NoSuchVersionException(
          String.format("Unable to resolve version '%s' of package '%s'",
              pvid.version, pvid.pkg));

    if (this.pkg == null) {
      this.pkg = new SourcePrePackage(this.id, this.root);
    }

    return this.pkg;
  }

  // FIXME (arrdem 6/12/2017) Stopping here, bedtime. Needs the same PackageDescriptor logic as LocalPackageStore.
  private static class SourcePrePackage extends PrePackage {
    private final Path root;
    private PackageDescriptor descriptor = null;

    public SourcePrePackage(PackageVersionIdentifier id, Path root) {
      super(id);
      this.root = root;
    }

    @Override
    public IMap<PackageIdentifier, Iterable<PackageVersionConstraint>> getDependencies() throws IOException {
      return null;
    }

    @Override
    public Iterable<NamespaceIdentifier> getNamespaces() throws IOException {
      return null;
    }

    @Override
    public Readable getNamespaceSource(NamespaceIdentifier id) throws PackageResolverException, IOException {
      return null;
    }
  }
}
