package org.oxlang.lang;

import io.lacuna.bifurcan.IMap;
import io.lacuna.bifurcan.Lists;
import io.lacuna.bifurcan.Map;
import io.lacuna.bifurcan.Maps;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.Queue;
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
    private Map<NamespaceIdentifier, Path> namespaces = null;

    public SourcePrePackage(PackageVersionIdentifier id, Path root) {
      super(id);
      this.root = root;
    }

    private PackageDescriptor getDescriptor() throws IOException {
      if (this.descriptor == null) {
        // FIXME (arrdem 6/12/2017) turn off respecting lockfiles?
        Path packageFilePath = this.root.resolve(PackageResolver.PACKAGE_LOCK_FILE_NAME);
        if (!Files.exists(packageFilePath)) {
          packageFilePath = this.root.resolve(PackageResolver.PACKAGE_FILE_NAME);
        }

        if (!Files.exists(packageFilePath)) {
          throw new NoSuchFileException(
              String.format("Unable to resolve either package file or package lock file in directory %s for package %s",
                            this.root, this.id));
        }

        try {
          Object sexpr = OxlangReader.read(packageFilePath);
          this.descriptor = PackageDescriptor.of(sexpr);
        } catch (IOException e) {
          throw new IOException(
              String.format("Unable to load package file '%s' for package '%s'",
                            packageFilePath.toFile().getAbsolutePath().toString(), this.id),
              e);
        }
      }

      return this.descriptor;
    }

    @Override
    public IMap<PackageIdentifier, Iterable<PackageVersionConstraint>> getDependencies() throws IOException {
      return (IMap) getDescriptor().requirements;
    }

    @Override
    public Iterable<NamespaceIdentifier> getNamespaces() throws IOException {
      if (this.namespaces == null) {
        PathMatcher m = root.getFileSystem().getPathMatcher("glob:*.ox");

        Files.walk(this.root, new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
            if (m.matches(file)) {
              namespaces = namespaces.put(
                  new NamespaceIdentifier(id, file.relativize(root)
                                               .toString()
                                               .replace(".ox", "")
                                               .replace("/", ".")),
                  file);
            }
            return FileVisitResult.CONTINUE;
          }
        });
      }

      return this.namespaces.keys();
    }

    @Override
    public Readable getNamespaceSource(NamespaceIdentifier id) throws PackageResolverException, IOException {
      return null;
    }
  }
}
