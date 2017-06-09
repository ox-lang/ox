package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 6/8/17.
 *
 * Value type for an Ox group, ala a Maven package group. Groups are the bottom-most element of the Ox package
 * resolution and naming higherarchy.
 */
public class GroupIdentifier {
  @NotNull public final String name;

  public GroupIdentifier(@NotNull String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GroupIdentifier that = (GroupIdentifier) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
