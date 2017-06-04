package org.oxlang.lang;

import io.lacuna.bifurcan.IMap;
import io.lacuna.bifurcan.List;
import io.lacuna.bifurcan.Map;
import io.lacuna.bifurcan.Maps;
import org.jetbrains.annotations.NotNull;
import org.oxlang.data.SimpleSymbol;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * Created by arrdem on 6/3/17.
 *
 * Provides a way to map package identifiers to resources (whether files or buffers) which may be loaded.
 *
 * Examples will (presumably) include resolving packages from an FTP/HTTP(S) service, and from filesystem caches.
 */
public abstract class PackageResolver {

  public static class NoSuchPackageException extends Exception {};

  @NotNull
  public abstract Map<NamespaceIdentifier, Reader> resolveContents(PackageIdentifier id)
      throws NoSuchPackageException;
}
