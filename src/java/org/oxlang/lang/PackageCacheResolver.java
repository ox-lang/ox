package org.oxlang.lang;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jetbrains.annotations.NotNull;

import io.lacuna.bifurcan.List;

/**
 * Created by rmckenzie on 6/8/17.
 */
public class PackageCacheResolver extends PackageResolver {
  public final Path root;

  public PackageCacheResolver(Path root) {
    this.root = root;
  }

  public

  public Path pathOf(PackageIdentifier id) throws NoSuchPackageException {
    Path p = root.resolve(id.groupName);
    if (!Files.isDirectory(p))
      throw new NoSuchPackageException(
          String.format("Unable to resolve package group '%s'",
              id.groupName));

    p = root.resolve(id.packageName);
    if (!Files.isDirectory(p))
      throw new NoSuchPackageException(
          String.format("Unable to resolve artifact '%s' in group '%s'",
              id.packageName, id.groupName));

    return p;
  }

  @Override
  public boolean exists(PackageIdentifier pid) {

  }

  @Override
  public boolean exists(PackageVersionIdentifier pvid) {
    return false;
  }

  @NotNull
  public static List<ConcreteVersion> listVersions(PackageIdentifier id) {

  }

  @NotNull
  @Override
  public PrePackage resolveContents(PackageVersionIdentifier id)
      throws NoSuchPackageException {


    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(p)) {
      for (Path dirPath : directoryStream) {
        if (Files.isDirectory(dirPath)) {

        }
      }
    } catch (IOException e) {
      throw new NoSuchPackageException();
    }

    return null;
  }
}
