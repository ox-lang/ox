package org.oxlang.lang;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import io.lacuna.bifurcan.Lists;
import org.jetbrains.annotations.NotNull;

import io.lacuna.bifurcan.List;

/**
 * Created by arrdem on 6/8/17.
 */
public class LocalPackageStoreResolver extends PackageResolver {
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

  private Path _pathOf(PackageIdentifier id) {
    return _pathOf(id.group).resolve(id.name);
  }

  @Override
  public boolean exists(PackageIdentifier pid) {
    return Files.isDirectory(_pathOf(pid));
  }

  public Path pathOf(PackageIdentifier id) throws NoSuchGroupException, NoSuchPackageException {
    Path p = _pathOf(id);
    if (!Files.isDirectory(p))
      throw new NoSuchPackageException(
          String.format("Unable to resolve artifact '%s' in group '%s'",
              id.name, id.group.name));

    return p;
  }

  private Path _pathOf(PackageVersionIdentifier pvid) {
    return _pathOf(pvid.pkg).resolve(pvid.version.toString());
  }

  @Override
  public boolean exists(PackageVersionIdentifier pvid) {
    return Files.isDirectory(_pathOf(pvid));
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

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(_pathOf(id))) {
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

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(_pathOf(pid))) {
      for (Path p : stream) {
        l = l.addFirst(new PackageVersionIdentifier(pid, SemVersion.of(p.getFileName().toString())));
      }
    } catch (IOException e) {
      return (List<PackageVersionIdentifier>) Lists.EMPTY;
    }

    return l;
  }

  @NotNull
  @Override
  public PrePackage resolveContents(PackageVersionIdentifier pvid) throws NoSuchGroupException, NoSuchPackageException {
    return null;
  }
}
