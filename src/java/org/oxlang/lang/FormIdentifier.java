package org.oxlang.lang;

/**
 * Created by arrdem on 6/3/17.
 */
public class FormIdentifier {
  public final int id;

  public FormIdentifier(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FormIdentifier that = (FormIdentifier) o;

    return id == that.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
