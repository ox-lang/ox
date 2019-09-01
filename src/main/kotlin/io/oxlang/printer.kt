/**
 * @author Reid 'arrdem' McKenzie 2019-9-31
 *
 * The Ox printer.
 * Dual to the scanner/reader in theory.
 */

package io.oxlang

import io.lacuna.bifurcan.Maps
import java.io.OutputStreamWriter
import java.util.*
import io.lacuna.bifurcan.List as BList
import io.lacuna.bifurcan.Map as BMap
import io.lacuna.bifurcan.Set as BSet

/**
 * Sadly in Kotlin, type aliases are not recursive.
 * They're just macros it seems.
 *
 * This means that I can't actually capture the recursive type of PrintFn in Kotlin.
 * So this whole package has to play some games.
 * Define EPM (Erased Print Map) - the ground type we'll have to upcast from
 * Define EFN (Erased Print FuNction) - the erased form of the individual print methods
 * Define the "Real"(est) PrintMap type
 * Define the "Real"(est) PrintFn type
 *
 * When we call PrintFns, we'll have ALMOST the fully recursive type signature to hand.
 * But when we go through the dispatch table, we'll have to "promote" the erased functions we get
 * out of the dispatch table to "real" functions which have a near-full signature.
 */
typealias EPM = BMap<Any, Function<*>>
typealias EFN = ((Printer, EPM, OutputStreamWriter, Any) -> Unit)
typealias PrintMap = BMap<Any, EFN>
typealias PrintFn = ((Printer, PrintMap, OutputStreamWriter, Any) -> Unit)

/**
 * Helper for printing sequential types, the grammar for which is "$start(<>($delim<>)+)$end"
 * where <> is taken to mean recursive printing.
 */
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

/**
 * Helper for printing named types, the grammar for which is
 * "$prefix(<>(.<>)+)/$name)$suffix"
 * where <> is taken to be a name segment pulled from a parent namespace.
 */
fun <T : Namespaced<out Namespaced<*>>?>
  printNamespacy(p: Printer,
                 pm: PrintMap,
                 w: OutputStreamWriter,
                 prefix: String,
                 o: Namespaced<T>,
                 suffix: String) {
  var segments = BList<String>()
  var parent = o.namespace()
  while (parent != null) {
    segments = segments.addFirst(parent.name())
    parent = parent.namespace() as T
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
class Printer(val defaultPrinter: PrintFn = Printer::printDefault as PrintFn) {
  fun printMapEntry(pm: PrintMap, w: OutputStreamWriter, o: Maps.Entry<Any, Any>) {
    this.print(pm, w, o.key())
    w.write(" ")
    this.print(pm, w, o.value())
  }

  fun printMap(pm: PrintMap, w: OutputStreamWriter, o: BMap<Any, Any>) {
    printListy(this, pm, w, o, "{", Printer::print, ", ", "}")
  }

  fun printList(pm: PrintMap, w: OutputStreamWriter, o: BList<Any>) {
    printListy(this, pm, w, o, "(", Printer::print, " ", ")")
  }

  fun printSet(pm: PrintMap, w: OutputStreamWriter, o: BSet<Any>) {
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

  fun printDefault(pm: PrintMap, s: OutputStreamWriter, o: Any) {
    s.write(o.toString())
  }

  fun print(pm: PrintMap, w: OutputStreamWriter, o: Any): Unit {
    val fn = pm.get(o.javaClass as Any, this.defaultPrinter as EFN) as PrintFn
    fn(this, pm, w, o)
  }
}

object Printers {
  /**
   * Note that upcasts from EPM to PrintMap are presumed to be safe, as this is hwo we're breaking
   * type-level recursion which Kotlin doesn't support see the comment at the top of the file
   * which explains the type aliases.
   *
   * Note that upcasts FROM OBJECT TO THE KEY TYPE must be safe by definition of print().
   *
   * So we suppress all unchecked (generic to generic) casts.
   */
  @JvmStatic
  @Suppress("UNCHECKED_CAST")
  val BASE_PRINT_MAP: PrintMap = (
    PrintMap()
      // Print Symbol
      .put(Symbol::class.java as Any
      ) { p: Printer, pm: EPM, w: OutputStreamWriter, o: Any ->
        p.printSymbol(pm as PrintMap, w, o as Symbol)
      }

      // Print keyword
      .put(Keyword::class.java as Any
      ) { p: Printer, pm: EPM, w: OutputStreamWriter, o: Any ->
        p.printKeyword(pm as PrintMap, w, o as Keyword)
      }

      // Print List
      .put(BList::class.java as Any
      ) { p: Printer, pm: EPM, w: OutputStreamWriter, o: Any ->
        p.printList(pm as PrintMap, w, o as BList<Any>)
      }

      // Print Map.Entry
      .put(Maps.Entry::class.java as Any
      ) { p: Printer, pm: EPM, w: OutputStreamWriter, o: Any ->
        p.printMapEntry(pm as PrintMap, w, o as Maps.Entry<Any, Any>)
      }

      // Print Map
      .put(BMap::class.java as Any
      ) { p: Printer, pm: EPM, w: OutputStreamWriter, o: Any ->
        p.printMap(pm as PrintMap, w, o as BMap<Any, Any>)
      }

      // Print Set
      .put(BSet::class.java as Any
      ) { p: Printer, pm: EPM, w: OutputStreamWriter, o: Any ->
        p.printSet(pm as PrintMap, w, o as BSet<Any>)
      }
    )

    @JvmStatic
    public fun println(o: Any) {
      val w = System.out.writer()
      Printer().print(BASE_PRINT_MAP, w, o)
      w.write("\n")
      w.flush()
    }
}

object PrinterTest {
  @JvmStatic
  fun main(args: Array<String>) {
    val p = Printer()
    val pm = Printers.BASE_PRINT_MAP
    val ow = System.out.writer()

    println("The print map ")
    for (kv in pm) {
      val key = kv.key()
      val value = kv.value()
      println(" $key => $value")
    }

    // symbols
    p.print(pm, ow, Symbols.of("test"))
    ow.write("\n")
    ow.flush()

    p.print(pm, ow, Symbols.of("me.arrdem/test"))
    ow.write("\n")
    ow.flush()

    p.print(pm, ow, Symbols.of("piped test"))
    ow.write("\n")
    ow.flush()

    // keywords
    p.print(pm, ow, Keywords.of("test2"))
    ow.write("\n")
    ow.flush()

    p.print(pm, ow, Keywords.of("io.oxlang/test2"))
    ow.write("\n")
    ow.flush()

    p.print(pm, ow, Keywords.of("piped test"))
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
}
