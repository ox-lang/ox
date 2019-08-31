package io.ox_lang

import java.util.*

data class Keyword(private val namespace: Keyword?, private val name: String!): Namespaced<Keyword> {
  override fun name(): String {
    return this.name
  }

  override fun namespace(): Optional<Keyword> {
    Optional.ofNullable(this.namespace)
  }
}
