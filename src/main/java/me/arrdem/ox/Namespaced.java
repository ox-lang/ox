package me.arrdem.ox;

public interface Namespaced<T extends Namespaced> extends Named {
  public T namespace();

  default boolean isPiped() {
    if (Named.isNamePiped(this.name())) {
      return Boolean.TRUE;
    } else if(this.namespace() != null) {
      return this.namespace().isPiped();
    } else {
      return Boolean.FALSE;
    }
  }
}
