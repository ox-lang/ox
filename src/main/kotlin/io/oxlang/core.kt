/**
 * @author Reid 'arrdem' McKenzie 2019-9-31
 *
 * The Ox 'core'.
 * Really just a pile of small bits and bats that didn't deserve their own files.
 */

package io.oxlang

/**
 * Type aliases that win by default so we just use Zach's fine datastructures everywhere.
 */
typealias Map<A, B> = io.lacuna.bifurcan.Map<A, B>
typealias List<V> = io.lacuna.bifurcan.List<V>
typealias Set<V> = io.lacuna.bifurcan.Set<V>

/**
 * A less fine datastructure
 */
data class Cons<T>(public val car: T, public val cdr: Cons<T>? = null);

/**
 * Symbol - and the Symbols helper class.
 */
data class Symbol(private val name: String,
                  private val namespace: Symbol? = null):
  Namespaced<Symbol>
{
  override fun name(): String {
    return this.name
  }

  override fun namespace(): Symbol? {
    return this.namespace
  }
}

object Symbols {
  @JvmStatic
  fun of(s: String): Symbol {
    var name: String? = null
    var segments = List<String>()
    val chunks = s.split(Regex("/"), 1)
    if (chunks.count() == 2) {
      name = chunks[1]
      for (segment in chunks[0].split(".")) {
        segments.addLast(segment)
      }
      var root = Symbol(segments.first(), null)
      segments = segments.removeFirst()
      for (segment in segments) {
        root = Symbol(segment, root)
      }
      return Symbol(name, root)
    } else {
      return Symbol(s, null)
    }
  }
}

/**
 * Keyword - and the Keywords helper class
 *
 * Basically the same as symbols. Should really figure out how to factor this better.
 */
data class Keyword(private val name: String,
                   private val namespace: Keyword? = null):
  Namespaced<Keyword>
{
  override fun name(): String {
    return this.name
  }

  override fun namespace(): Keyword? {
    return this.namespace
  }
}

object Keywords {
  @JvmStatic
  fun of(s: String): Keyword {
    var name: String? = null
    var segments = List<String>()
    val chunks = s.split(Regex("/"), 1)
    if (chunks.count() == 2) {
      name = chunks[1]
      for (segment in chunks[0].split(".")) {
        segments.addLast(segment)
      }
      var root = Keyword(segments.first(), null)
      segments = segments.removeFirst()
      for (segment in segments) {
        root = Keyword(segment, root)
      }
      return Keyword(name, root)
    } else {
      return Keyword(s, null)
    }
  }
}

/**
 * A container for "meta" tag syntax objects.
 */
data class Meta(public val tag: Any, public val expr: Any);
