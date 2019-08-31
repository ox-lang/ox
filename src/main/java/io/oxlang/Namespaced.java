package io.oxlang;

import java.util.Optional;

public interface Namespaced<T extends Namespaced> extends Named {
  public Optional<T> namespace();

  default boolean isPiped() {
    if (Named.isNamePiped(this.name())) {
      return Boolean.TRUE;
    } else if(this.namespace().isPresent()) {
      return this.namespace().get().isPiped();
    } else {
      return Boolean.FALSE;
    }
  }
}
