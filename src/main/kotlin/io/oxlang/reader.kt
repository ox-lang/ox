/**
 * @author Reid 'arrdem' McKenzie 2019-8-31
 *
 * The Ox reader.
 * Consumes the Scanners to implement reading syntax or data (they aren't entirely the same).
 *
 * The data reader - DataReader - produces a tree of straight values which makes no attempt to
 * retain syntax information for later analysis.
 *
 * The syntax reader - SyntaxReader - produces a tree of datums wrapped in SyntaxObjects which
 * record start/end token positions. This provides some tools for the compiler to provide errors
 * and warnings without having to be tightly coupled to the precise machinery of the reader or
 * sharing any state between the two.
 */

package io.oxlang

import io.lacuna.bifurcan.Maps
import java.io.ByteArrayInputStream
import java.util.Iterator

typealias ERM = Map<TokenType, Function<*>>
typealias TokenIter = ICurrentIterator<Token<*>>
typealias ReadFn = (Reader, ERM, Any, TokenIter) -> Any?
typealias ReadMap = Map<TokenType, ReadFn>

/**
 * The Reader.
 *
 * Contains shared machinery between the data and syntax readers. In fact the data reader is
 * expected to be pretty much "just" a shim to this reader.
 *
 * As a nod to the printer, which supports
 */

data class SyntaxObject(
  val obj: Any?,
  val meta: Any? = null,
  val start: Token<*>? = null):
  Tagged
{
  override fun tag(): Symbol {
    return Symbols.of("ox.reader/syntax")
  }

  override fun value(): Any {
    return List.of(this.obj, this.start)
  }
}

class ParseException(val location: StreamLocation<*>?,
                     override val message: String?,
                     override val cause: Throwable? = null) :
  Exception(message, cause) {
  override fun toString(): String {
    return "Parsing at $location: $message"
  }
}

open class Reader(private val notFound: ReadFn = Reader::readError as ReadFn) {
  open fun readListy(startType: TokenType,
                     endType: TokenType,
                     msg: String,
                     ctor: ((Iterable<Any>) -> Any),
                     rm: ReadMap,
                     i: TokenIter): Any {
    val start = i.current().location
    val actualStartType = i.current().tokenType
    if (actualStartType != startType) {
      throw ParseException(start, "$msg: Expected open token $startType, got $actualStartType instead!")
    }
    try {
      i.next() // discard the start token
      var v = List<Any>()
      val sentinel = Object()
      val srm = rm.put(endType) { _: Any, _: Any, _: Any, _: Any -> sentinel } as ReadMap
      while (i.current().tokenType != endType) {
        val el = this.read(srm, i)
        if (el != sentinel)
          v = v.addLast(el)
      }
      i.next() // discard the end token
      return ctor(v)
    } catch (e: Exception) {
      throw ParseException(start, msg, e)
    }
  }

  fun readError(_rm: ERM, discard: Any, i: TokenIter): Any? {
    val t = i.current()
    val ttype = t.tokenType
    throw ParseException(t.location, "Unmapped token type: $ttype")
  }

  fun readNothing(_rm: ERM, discard: Any, i: TokenIter): Any? {
    i.next();
    return discard
  }

  fun readValue(_rm: ERM, discard: Any, i: TokenIter): Any? {
    val token = i.current()
    i.next()
    return token.value
  }

  fun readList(rm: ERM, discard: Any, i: TokenIter): Any? {
    return readListy(
      TokenType.LPAREN,
      TokenType.RPAREN,
      "While parsing () list, an exception occurred",
      { i: Iterable<Any> -> List.from(i) },
      rm as ReadMap,
      i
    )
  }

  fun readSqList(rm: ERM, discard: Any, i: TokenIter): Any? {
    return readListy(
      TokenType.LBRACKET,
      TokenType.RBRACKET,
      "While parsing [] list, an exception occurred",
      { i: Iterable<Any> -> List.from(i) },
      rm as ReadMap,
      i
    )
  }

  fun readSet(rm: ERM, discard: Any, i: TokenIter): Any {
    return readListy(
      TokenType.HASH_LBRACE,
      TokenType.RBRACE,
      "While parsing #{} set, an exception occurred",
      { i: Iterable<Any> -> Set.from(i) },
      rm as ReadMap,
      i
    )
  }

  fun readMap(rm: ERM, discard: Any, i: TokenIter): Any {
    val startLoc = i.current().location
    val kvs: Iterator<Any> = readListy(
      TokenType.LBRACE,
      TokenType.RBRACE,
      "While parsing {} map, an exception occurred",
      { i: Iterable<Any> -> i.iterator() },
      rm as ReadMap,
      i) as Iterator<Any>

    val m = Maps.EMPTY.linear()
    while (kvs.hasNext()) {
      val k = kvs.next()
      if (!kvs.hasNext())
        throw ParseException(startLoc, "Unmatched key $k")
      val v = kvs.next()
      m.put(k, v)
    }
    return m.forked()
  }

  fun readTag(rm: ERM, discard: Any, i: TokenIter): Any? {
    i.next()
    val tag = this.read(rm as ReadMap, i)
    val obj = this.read(rm as ReadMap, i)
    return Tag(tag!!, obj!!)
  }

  fun readQuote(rm: ERM, discard: Any, i: TokenIter): Any? {
    i.next()
    val obj = this.read(rm as ReadMap, i)
    return List.of(Symbols.of("quote"), obj!!)
  }

  fun readMeta(rm: ERM, discard: Any, i: TokenIter): Any? {
    val startToc = i.current()
    i.next()
    val meta = this.read(rm as ReadMap, i)
    val obj = this.read(rm as ReadMap, i)
    return Meta(meta, obj)
  }

  /**
   * FIXME (arrdem 9/7/2019)
   *   Can this be factored out as a reader parameter?
   */
  fun readSymbol(_rm: ERM, discard: Any, i: TokenIter): Any? {
    val token = i.current()
    i.next()
    return when (token.value as Symbol) {
      Symbols.of("null") -> null
      /**
       * FIXME (arrdem 9/7/2019)
       *   Given a warning(s) / messages framework, warn on these or make 'em pluggable.
       */
      Symbols.of("nil") -> null
      Symbols.of("none") -> null

      Symbols.of("+nan"),
      Symbols.of("+NaN"),
      Symbols.of("NaN"),
      Symbols.of("nan") -> Double.NaN

      Symbols.of("-NaN"),
      Symbols.of("-nan") -> -1 * Double.NaN

      else -> token.value
    }
  }

  open fun read(rm: ReadMap, i: TokenIter): Any? {
    // yo dawg I heard u leik streams
    val startPos = i.current().location
    val discard: Any = Object()
    var read: Any? = discard

    while (i.current() != null && read == discard) {
      val handler = rm.get(i.current().tokenType, notFound) as ReadFn
      read = handler(this, rm as ERM, discard, i)
    }

    if (read != discard) {
      return read
    } else {
      throw ParseException(startPos, "Got end of file while reading!")
    }
  }
}

class SyntaxReader(private val notFound: ReadFn = Reader::readError as ReadFn):
  Reader(notFound = notFound) {
  override fun readListy(startType: TokenType,
                         endType: TokenType,
                         msg: String,
                         ctor: ((Iterable<Any>) -> Any),
                         rm: ReadMap,
                         i: TokenIter): Any {
    val start = i.current().location
    val actualStartType = i.current().tokenType
    if (actualStartType != startType) {
      throw ParseException(start, "$msg: Expected open token $startType, got $actualStartType instead!")
    }
    try {
      i.next() // discard the start token
      var v = List<Any>()
      val sentinel = Object()
      val srm = rm.put(endType) { _: Any, _: Any, _: Any, _: Any -> sentinel } as ReadMap
      while (i.current().tokenType != endType) {
        val el: SyntaxObject? = this.read(srm, i)
        if (el!!.obj != sentinel)
          v = v.addLast(el)
      }
      i.next() // discard the end token
      return ctor(v)
    } catch (e: Exception) {
      throw ParseException(start, msg, e)
    }
  }

  override fun read(rm: ReadMap, i: TokenIter): SyntaxObject? {
    // yo dawg I heard u leik streams
    val current: Token<*> = i.current()
    return SyntaxObject(super.read(rm, i), current)
  }
}

object Readers {
  @JvmStatic
  val BASE_READ_MAP: ReadMap = (
    ReadMap()
      // Things we discard by default
      .put(TokenType.COMMENT, Reader::readNothing)
      .put(TokenType.WHITESPACE, Reader::readNothing)
      .put(TokenType.NEWLINE, Reader::readNothing)

      // Atoms
      .put(TokenType.NUMBER, Reader::readValue)
      .put(TokenType.KEYWORD, Reader::readValue)
      .put(TokenType.SYMBOL, Reader::readSymbol)
      .put(TokenType.STRING, Reader::readValue)

      // Lists
      .put(TokenType.LPAREN, Reader::readList)
      .put(TokenType.LBRACKET, Reader::readSqList)
      .put(TokenType.LBRACE, Reader::readMap)
      .put(TokenType.HASH_LBRACE, Reader::readSet)

      .put(TokenType.META, Reader::readMeta)
      .put(TokenType.HASH, Reader::readTag)
      .put(TokenType.QUOTE, Reader::readQuote)

      // RHS stuff that is an error by default
      .put(TokenType.RBRACE, Reader::readError)
      .put(TokenType.RBRACKET, Reader::readError)
      .put(TokenType.RPAREN, Reader::readError)
    )

  @JvmStatic
  val SYNTAX_READ_MAP: ReadMap = (
    BASE_READ_MAP
      .put(TokenType.COMMENT, Reader::readValue)
      .put(TokenType.WHITESPACE, Reader::readValue)
      .put(TokenType.NEWLINE, Reader::readValue)
    )

  fun read(rm: ReadMap, reader: java.io.Reader, streamIdentifier: Any): Any? {
    return Reader().read(rm, CurrentIterator(Scanners.scan(reader, streamIdentifier) as Iterator<Token<*>>))
  }

  fun read(rm: ReadMap, buff: String, streamIdentifier: Any): Any? {
    return read(rm, ByteArrayInputStream(buff.toByteArray()).reader(), streamIdentifier)
  }

  fun read(buff: String, streamIdentifier: Any): Any? {
    return read(BASE_READ_MAP, buff, streamIdentifier)
  }
}

object ReaderTest {
  @JvmStatic
  fun main(args: Array<String>) {
    val iter = CurrentIterator(Scanners.scan(System.`in`.reader(), "STDIN") as Iterator<Token<*>>)
    val rdr = Reader()

    while (iter.hasNext()) {
      val obj = rdr.read(Readers.BASE_READ_MAP, iter)
      if (obj != null) {
        Printers.println(obj)
      }
    }
  }
}

object SyntaxReaderTest {
  @JvmStatic
  fun main(args: Array<String>) {
    val iter = CurrentIterator(Scanners.scan(System.`in`.reader(), "STDIN") as Iterator<Token<*>>)
    val rdr = SyntaxReader()

    while (iter.hasNext()) {
      val obj = rdr.read(Readers.BASE_READ_MAP, iter)
      if (obj != null) {
        Printers.println(obj)
      }
    }
  }
}
