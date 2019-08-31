package io.oxlang

import java.util.*

data class Keyword(private val namespace: Keyword?,
                   private val name: String): Namespaced<Keyword> {
  override fun name(): String {
    return this.name
  }

  override fun namespace(): Optional<Keyword> {
    return Optional.ofNullable(this.namespace)
  }
}
