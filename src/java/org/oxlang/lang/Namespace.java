package org.oxlang.lang;

import io.lacuna.bifurcan.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.oxlang.data.SimpleSymbol;

/**
 * Created by arrdem on 5/15/17.
 *
 * Namespaces are mappings from simple symbols to definitions.
 */
class Namespace
    implements
        IDefinition,
        IForkable<IMap<SimpleSymbol, IDefinition>>,
        ILinearizable<IMap<SimpleSymbol, IDefinition>>,
        IMap<SimpleSymbol, IDefinition>
{
  public final NamespaceIdentifier id;
  public final IMap<SimpleSymbol, IDefinition> contents;
  public final IMap<SimpleSymbol, NamespaceIdentifier> aliases;
  public final String documentation;

  public Namespace(@NotNull NamespaceIdentifier id,
                   @Nullable IMap<SimpleSymbol, IDefinition> contents,
                   @Nullable IMap<SimpleSymbol, NamespaceIdentifier> aliases,
                   @Nullable String documentation) {
    if (id == null)
      throw new IllegalArgumentException("Got nil `id`!");

    if (contents == null) {
      contents = (Map<SimpleSymbol, IDefinition>) Maps.EMPTY;
    }

    if (aliases == null) {
      aliases = (Map<SimpleSymbol, NamespaceIdentifier>) Maps.EMPTY;
    }

    this.id = id;
    this.contents = contents;
    this.aliases = aliases;
    this.documentation = documentation;
  }

  @Override
  public IDefinition get(@NotNull SimpleSymbol name, IDefinition defaultValue) {
    return contents.get(name).orElse(defaultValue);
  }

  @Override
  public boolean contains(SimpleSymbol key) {
    return contents.contains(key);
  }

  @Override
  public IList<IEntry<SimpleSymbol, IDefinition>> entries() {
    return contents.entries();
  }

  @Override
  public long size() {
    return contents.size();
  }

  @Override
  public Namespace put(@NotNull SimpleSymbol name, IDefinition def) {
    return new Namespace(id, contents.put(name, def), aliases, documentation);
  }

  @Override
  public String getDocumentation() {
    return documentation;
  }

  @Override
  public Namespace withDocumentation(String documentation) {
    return new Namespace(id, contents, aliases, documentation);
  }

  @Override
  public IMap<SimpleSymbol, IDefinition> forked() {
    return new Namespace(id, contents.forked(), aliases, documentation);
  }

  @Override
  public IMap<SimpleSymbol, IDefinition> linear() {
    return new Namespace(id, contents.linear(), aliases, documentation);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Namespace iEntries = (Namespace) o;

    if (!id.equals(iEntries.id)) return false;
    if (!contents.equals(iEntries.contents)) return false;
    return getDocumentation() != null ? getDocumentation().equals(iEntries.getDocumentation()) : iEntries.getDocumentation() == null;
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + contents.hashCode();
    result = 31 * result + (getDocumentation() != null ? getDocumentation().hashCode() : 0);
    return result;
  }
}
