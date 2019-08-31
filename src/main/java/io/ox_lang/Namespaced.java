package io.ox_lang;

import io.ox_lang.Named;

import java.util.Optional;

public interface Namespaced<T> extends Named {
  public Optional<T> namespace();
}
