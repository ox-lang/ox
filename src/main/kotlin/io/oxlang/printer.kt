package io.oxlang

import io.lacuna.bifurcan.Maps
import java.io.OutputStreamWriter
import java.util.*
import io.lacuna.bifurcan.List as BList
import io.lacuna.bifurcan.Map as BMap
import io.lacuna.bifurcan.Set as BSet

// Have to ground out at BMap<O, O> to prevent a recursive type signature >.>
typealias PrintMap = BMap<Object, Object>
typealias PrintFn = ((Printer, PrintMap, OutputStreamWriter, Any) -> Unit)

fun printListy(p: Printer,
               pm: PrintMap,
               w: OutputStreamWriter,
               coll: Iterable<Any>,
               start: String,
               f: PrintFn,
               delim: String?,
               end: String) {
  var isFirst = true
  w.write(start)
  for (e in coll) {
    if (!isFirst && delim != null) {
      w.write(delim)
    } else {
      isFirst = false
    }
    f.invoke(p, pm, w, e)
  }
  w.write(end)
}

fun <T : Namespaced<out Namespaced<*>>?>
  printNamespacy(p: Printer,
                 pm: PrintMap,
                 w: OutputStreamWriter,
                 prefix: String,
                 o: Namespaced<T>,
                 suffix: String)
{
  var segments = BList<String>()
  var parent = o.namespace()
  while (parent.isPresent) {
    segments = segments.addFirst(parent.get()!!.name())
    parent = parent.get()!!.namespace() as Optional<T>
  }

  w.write(prefix)
  if (segments.size() != 0L) {
    printListy(p, pm, w, segments, "", Printer::print, ".", "")
    w.write("/")
  }
  w.write(o.name())
  w.write(suffix)
}

/**
 * The Printer interface.
 *
 * The Printer is implemented as a trivial directly recursive call through a dispatch-by-type table
 * (this may later be refactored to extensible protocol / predicative dispatch) where table entries
 * are handlers which produce formatting for a given type and recursively call the printer with a
 * dispatching table.
 *
 * Callees may manipulate the dispatch table, but as it is immutable changes will not propagate
 * beyond the scope of a single print handler.
 *
 * The PrintMap is regrettably erased, but must be a map from Class<T> instances to KFunction<>
 * instances.
 *
 * Each entry handles a concrete class (unlike a protocol which handles interfaces).
 * The handler convention is that the printer and the print map are arguments, and calless
 * are expected to recurse through the printer and print map rather than hijacking control.
 *
 * For instance, we know when printing a BMap<A, B> that the elements will be Map.Entry<A, B> and
 * so one could be tempted to directly call the appropriate function, but this leads to a loss of
 * extension capability as a user who isn't aware of this direct linking must discover it the hard
 * way and provide two overloads, not one.
 */
class Printer() {
  fun printMapEntry(pm: PrintMap, w: OutputStreamWriter, o: Maps.Entry<Object, Object>) {
    this.print(pm, w, o.key())
    w.write(" ")
    this.print(pm, w, o.value())
  }

  fun printMap(pm: PrintMap, w: OutputStreamWriter, o: BMap<Object, Object>) {
    printListy(this, pm, w, o, "{", Printer::print, ", ", "}")
  }

  fun printList(pm: PrintMap, w: OutputStreamWriter, o: BList<Object>) {
    printListy(this, pm, w, o, "{", Printer::print, ", ", "}")
  }

  fun printSet(pm: PrintMap, w: OutputStreamWriter, o: BSet<Object>) {
    printListy(this, pm, w, o, "#{", Printer::print, " ", "}")
  }

  fun printSymbol(pm: PrintMap, w: OutputStreamWriter, o: Symbol) {
    val prefix = if (o.isPiped) "|" else ""
    printNamespacy(this, pm, w, prefix, o, prefix)
  }

  fun printKeyword(pm: PrintMap, w: OutputStreamWriter, o: Keyword) {
    val prefix = if (o.isPiped) "|" else ""
    printNamespacy(this, pm, w, ":$prefix", o, prefix)
  }

  fun printDefault(pm: PrintMap, s: OutputStreamWriter, o: Object) {
    s.write(o.toString())
  }

  fun print(pm: PrintMap, w: OutputStreamWriter, o: Any): Unit {
    (pm.get(o.javaClass as Object, Printer::printDefault as Object) as PrintFn).invoke(this, pm, w, o)
  }
}

val BASE_PRINT_MAP = (
  PrintMap()
    // Print Symbol
    .put(Symbol::class.java as Object,
      { p: Printer, pm: PrintMap, w: OutputStreamWriter, o: Any ->
        p.printSymbol(pm, w, o as Symbol)
      } as Object)

    // Print keyword
    .put(Keyword::class.java as Object,
      { p: Printer, pm: PrintMap, w: OutputStreamWriter, o: Any ->
        p.printKeyword(pm, w, o as Keyword)
      } as Object)

    // Print List
    .put(BList::class.java as Object,
      { p: Printer, pm: PrintMap, w: OutputStreamWriter, o: Any ->
        p.printList(pm, w, o as BList<Object>)
      } as Object)

    // Print Map.Entry
    .put(Maps.Entry::class.java as Object,
      { p: Printer, pm: PrintMap, w: OutputStreamWriter, o: Any ->
        p.printMapEntry(pm, w, o as Maps.Entry<Object, Object>)
      } as Object)

    // Print Map
    .put(BMap::class.java as Object,
      { p: Printer, pm: PrintMap, w: OutputStreamWriter, o: Any ->
        p.printMap(pm, w, o as BMap<Object, Object>)
      } as Object)

    // Print Set
    .put(BSet::class.java as Object,
      { p: Printer, pm: PrintMap, w: OutputStreamWriter, o: Any ->
        p.printSet(pm, w, o as BSet<Object>)
      } as Object)
  )

fun main(args: Array<String>) {
  val p = Printer()
  val ow = System.out.writer()
  val pm = BASE_PRINT_MAP
  print("The print map ")
  for (kv in pm) {
    val key = kv.key()
    val value = kv.value()
    println(" $key => $value")
  }

  // symbols
  p.print(pm, ow, Symbol(null, "test"))
  ow.write("\n")
  ow.flush()

  p.print(pm, ow, Symbol(Symbol(Symbol(null, "me"), "arrdem"), "test"))
  ow.write("\n")
  ow.flush()

  p.print(pm, ow, Symbol(null, "piped test"))
  ow.write("\n")
  ow.flush()

  // keywords
  p.print(pm, ow, Keyword(null, "test2"))
  ow.write("\n")
  ow.flush()

  p.print(pm, ow, Keyword(Keyword(Keyword(null, "io"), "ox_lang"), "test2"))
  ow.write("\n")
  ow.flush()

  p.print(pm, ow, Keyword(null, "piped test"))
  ow.write("\n")
  ow.flush()
  // maps
  p.print(pm, ow, BMap<String, Long>().put("a", 1).put("b", 2).put("c", 3))
  ow.write("\n")
  ow.flush()

  // lists
  p.print(pm, ow, BList.of(1L, 2L, 3L, 4L))
  ow.write("\n")
  ow.flush()

  // sets
  p.print(pm, ow, BSet.of(1L, 2L, 3L, 4L))
  ow.write("\n")
  ow.flush()
}
