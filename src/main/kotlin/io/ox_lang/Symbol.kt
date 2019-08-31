package io.ox_lang

import java.util.*

data class Symbol(private val namespace: Symbol?, private val name: String!): Namespaced<Symbol> {
  override fun name(): String {
    return this.name
  }

  override fun namespace(): Optional<Symbol> {
    Optional.ofNullable(this.namespace)
  }
}
