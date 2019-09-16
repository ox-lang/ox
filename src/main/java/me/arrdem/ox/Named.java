package me.arrdem.ox;

public interface Named {
  public String name();

  static boolean isNamePiped(String name) {
    for (int chr: name.chars().toArray()) {
      if (Character.isWhitespace(chr)) {
        return true;
      }
    }
    return false;
  }

  default boolean isPiped() {
    return isNamePiped(this.name());
  }
}
