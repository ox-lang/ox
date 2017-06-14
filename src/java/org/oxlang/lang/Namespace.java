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
        IDefinition
{
  @NotNull public final NamespaceIdentifier id;
  @NotNull public final Map metadada;
  @NotNull public final Map<FormIdentifier, Form> forms;
  @NotNull public final Map<SimpleSymbol, IDefinition> contents;
  @NotNull public final Map<SimpleSymbol, SimpleSymbol> aliases;
  @NotNull public final String documentation;

  public Namespace(@NotNull NamespaceIdentifier id,
                   @Nullable Map metadata,
                   @Nullable Map<FormIdentifier, Form> forms,
                   @Nullable Map<SimpleSymbol, IDefinition> contents,
                   @Nullable Map<SimpleSymbol, SimpleSymbol> aliases,
                   @Nullable String documentation) {
    if (id == null)
      throw new IllegalArgumentException("Got nil `id`!");

    if (forms == null) {
      forms = (Map<FormIdentifier, Form>) Maps.EMPTY;
    }

    if (contents == null) {
      contents = (Map<SimpleSymbol, IDefinition>) Maps.EMPTY;
    }

    if (aliases == null) {
      aliases = (Map<SimpleSymbol, SimpleSymbol>) Maps.EMPTY;
    }

    this.id = id;
    this.forms = forms;
    this.contents = contents;
    this.aliases = aliases;
    this.documentation = documentation;
    this.metadada = metadata;
  }

  @Override
  public String getDocumentation() {
    return documentation;
  }

  @Override
  public Namespace withDocumentation(String documentation) {
    return new Namespace(id, metadada, forms, contents, aliases, documentation);
  }

  public Namespace withForm(Form f) {
    return new Namespace(id, metadada, forms.put(new FormIdentifier((int) forms.size()), f), contents, aliases, documentation);
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
