package org.oxlang.lang;

import io.lacuna.bifurcan.*;
import org.jetbrains.annotations.NotNull;
import org.oxlang.io.ZipEntryReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by arrdem on 6/8/17.
 *
 * A package store representing an m2-like tree of packages.
 * This store doesn't support package signatures
 *
 * $CACHE_ROOT
 * $CACHE_ROOT/$GROUPID
 * $CACHE_ROOT/$GROUPID/$ARTIFACTID
 * $CACHE_ROOT/$GROUPID/$ARTIFACTID/$VERSION
 * $CACHE_ROOT/$GROUPID/$ARTIFACTID/$VERSION/$GROUPID-$ARTIFACTID-$VERSION.odn   package metadata
 * $CACHE_ROOT/$GROUPID/$ARTIFACTID/$VERSION/$GROUPID-$ARTIFACTID-$VERSION.oxpkg (.zip) package contents
 *
 * The package archive format itself is as such...
 * /package.ox       package contents, specifically version constraints on depended packages
 * /package.lock     (optional) package contents, specifically pinned versions this deploy was done against
 *
 */
public class LocalPackageStoreResolver extends PackageResolver {
  static final String PACKAGE_ARCHIVE_EXTENSION = "oxpkg";
  static final String PACKAGE_METADATA_EXTENSION = "odn";
  static final String PACKAGE_FILE_NAME = "package." + PACKAGE_METADATA_EXTENSION;
  static final String PACKAGE_LOCK_FILE_NAME = "package.lock";

  public final Path root;

  public LocalPackageStoreResolver(Path root) {
    this.root = root;
  }

  private Path _pathOf(GroupIdentifier id) {
    return root.resolve(id.name);
  }

  @Override
  public boolean exists(GroupIdentifier id) {
    return Files.isDirectory(_pathOf(id));
  }

  public Path pathOf(GroupIdentifier id) throws NoSuchGroupException {
    Path p = _pathOf(id);
    if (!Files.isDirectory(p))
      throw new NoSuchGroupException(
          String.format("Unable to resolve package group '%s'",
              id.name));

    return p;
  }

  private Path _pathOf(PackageIdentifier id) throws NoSuchGroupException {
    return pathOf(id.group).resolve(id.name);
  }

  @Override
  public boolean exists(PackageIdentifier pid) {
    try {
      return Files.isDirectory(_pathOf(pid));
    } catch (NoSuchGroupException e) {
      return false;
    }
  }

  public Path pathOf(PackageIdentifier id) throws NoSuchGroupException, NoSuchPackageException {
    Path p = _pathOf(id);
    if (!Files.isDirectory(p))
      throw new NoSuchPackageException(
          String.format("Unable to resolve artifact '%s' in group '%s'",
              id.name, id.group.name));

    return p;
  }

  private Path _pathOf(PackageVersionIdentifier pvid) throws NoSuchPackageException, NoSuchGroupException {
    return pathOf(pvid.pkg).resolve(pvid.version.toString());
  }

  @Override
  public boolean exists(PackageVersionIdentifier pvid) {
    try {
      return Files.isDirectory(_pathOf(pvid));
    } catch (NoSuchPackageException e) {
      return false;
    } catch (NoSuchGroupException e) {
      return false;
    }
  }

  public Path pathOf(PackageVersionIdentifier pvid) throws NoSuchVersionException, NoSuchPackageException, NoSuchGroupException {
    Path p = _pathOf(pvid);
    if (!Files.isDirectory(p))
      throw new NoSuchVersionException(
          String.format("Unable to resolve version '%s' of artifact '%s' in group '%s'",
              pvid.version, pvid.pkg.name, pvid.pkg.group.name));

    return p;
  }

  private Path storePathOf(PackageVersionIdentifier pvid, String ext) throws NoSuchVersionException, NoSuchPackageException, NoSuchGroupException {
    return pathOf(pvid)
        .resolve(
            String.format("%s-%s-%s.%s",
              pvid.pkg.group.name,
              pvid.pkg.name,
              pvid.version.toString(),
              ext));
  }

  @NotNull
  @Override
  public List<GroupIdentifier> listGroups() {
    List<GroupIdentifier> l = (List<GroupIdentifier>) Lists.EMPTY;

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(root)) {
      for (Path p : stream) {
        l = l.addFirst(new GroupIdentifier(p.getFileName().toString()));
      }
    } catch (IOException e) {
      return (List<GroupIdentifier>) Lists.EMPTY;
    }

    return l;
  }

  @NotNull
  @Override
  public List<PackageIdentifier> listGroup(GroupIdentifier id) throws NoSuchGroupException {
    List<PackageIdentifier> l = (List<PackageIdentifier>) Lists.EMPTY;

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(pathOf(id))) {
      for (Path p : stream) {
        l = l.addFirst(new PackageIdentifier(id, p.getFileName().toString()));
      }
    } catch (IOException e) {
      return (List<PackageIdentifier>) Lists.EMPTY;
    }

    return l;
  }

  @NotNull
  @Override
  public List<PackageVersionIdentifier> listVersions(PackageIdentifier pid) throws NoSuchGroupException, NoSuchPackageException {
    List<PackageVersionIdentifier> l = (List<PackageVersionIdentifier>) Lists.EMPTY;

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(pathOf(pid))) {
      for (Path p : stream) {
        // FIXME (arrdem 6/9/2017) should use a ConcreteVersionParser or something instead of hard-coding semver
        l = l.addFirst(new PackageVersionIdentifier(pid, SemPackageVersion.of(p.getFileName().toString())));
      }
    } catch (IOException e) {
      return (List<PackageVersionIdentifier>) Lists.EMPTY;
    }

    return l;
  }

  @NotNull
  @Override
  public PrePackage getPackage(PackageVersionIdentifier pvid) throws NoSuchPackageException, NoSuchVersionException, NoSuchGroupException {
    Path pkgPath = storePathOf(pvid, PACKAGE_ARCHIVE_EXTENSION); // FIXME (arrdem 6/11/2017) shitty file extension is shitty.
    if (!Files.exists(pkgPath))
      throw new NoSuchVersionException(
          String.format("No package archive for version '%s' of artifact '%s' in group '%s'",
                        pvid.version.toString(), pvid.pkg.name, pvid.pkg.group.name));

    return new ArchivePrePackage(pvid, pkgPath);
  }

  private static class ArchivePrePackage extends PrePackage {
    private final Path path;
    private PackageDescriptor descriptor = null;
    private Map<NamespaceIdentifier, String> namespaces = (Map<NamespaceIdentifier, String>) Maps.EMPTY;

    public ArchivePrePackage(PackageVersionIdentifier pvid, Path path) {
      super(pvid);
      this.path = path;
    }

    private PackageDescriptor getDescriptor() throws IOException {
      if (this.descriptor != null) {
        return descriptor;
      } else {
        ZipInputStream stream = new ZipInputStream(new FileInputStream(this.path.toFile()));
        ZipEntry e;
        while ((e = stream.getNextEntry()) != null) {
          if (!e.isDirectory()) {
            if (e.getName() == PACKAGE_FILE_NAME) {
              // Read the file contents to a buffer we can parse as an s-expression
              Object packageForm = OxlangReader.read(new ZipEntryReader(stream, e));

              // FIXME (arrdem 6/11/2017) need to call the interpreter with the package context to get a Package Desc.
              this.descriptor = PackageDescriptor.of(packageForm);
              break;
            } else if (e.getName().endsWith(".ox")) {
              NamespaceIdentifier nsid = new NamespaceIdentifier(this.id, e.getName()
                  .replace(".ox", "")
                  .replace("/", "."));
              namespaces = namespaces.put(nsid, e.getName());
            }
          }
        }
        return this.descriptor;
      }
    }

    @Override
    public IMap<PackageIdentifier, Iterable<PackageVersionConstraint>> getDependencies() throws IOException {
      return (IMap) getDescriptor().requirements;
    }

    @Override
    public Iterable<NamespaceIdentifier> getNamespaces() throws IOException {
      // Call for effect so that namespaces is populated. May throw.
      getDescriptor();

      // We just need the keyset.
      return namespaces.keys();
    }

    @Override
    public Readable getNamespaceSource(NamespaceIdentifier id) throws PackageResolverException, IOException {
      // Call for effect so that the namespaces map gets populated. May throw.
      getDescriptor();

      if (!namespaces.contains(id))
        throw new NoSuchNamespaceException(
            String.format("No such namespace '%s' in package '%s'", id.name, this.id));

      // This can't throw because we already checked
      String entryName = namespaces.get(id).get();

      ZipInputStream stream = new ZipInputStream(new FileInputStream(this.path.toFile()));
      ZipEntry e;
      while ((e = stream.getNextEntry()) != null) {
        if (e.getName().equals(entryName)) {
          return new ZipEntryReader(stream, e);
        }
      }

      throw new IllegalStateException(
          String.format("Searching for source of registerd namespace '%s' (%s) didn't find it in the backing archive!",
              id, entryName));
    }
  }
}
