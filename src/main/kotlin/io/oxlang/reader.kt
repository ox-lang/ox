/**
 * @author Reid 'arrdem' McKenzie 2019-8-31
 *
 * The Ox reader.
 * Consumes the Scanner to implement reading syntax or data (they aren't entirely the same).
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

import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.Iterator

typealias ERM = Map<TokenType, Function<*>>
typealias TokenIter = ICurrentIterator<Token<*>>
typealias ReadFn = (Reader, ERM, TokenIter) -> Any?
typealias ReadMap = Map<TokenType, ReadFn>

/**
 * The Reader.
 *
 * Contains shared machinery between the data and syntax readers. In fact the data reader is
 * expected to be pretty much "just" a shim to this reader.
 *
 * As a nod to the printer, which supports
 */

val Nothing = Object()

data class ReadObject<T>(
  val obj: Any,
  val start: StreamLocation<T>? = null) {
}

class ParseException(val location: StreamLocation<*>?,
                     override val message: String?,
                     override val cause: Throwable? = null) :
  Exception(message, cause) {
  override fun toString(): String {
    return "Parsing at $location: $message"
  }
}

class Reader(private val notFound: ReadFn = Reader::readError as ReadFn) {
  fun readError(rm: ERM, i: TokenIter): Any? {
    val t = i.current()
    val ttype = t.tokenType
    throw ParseException(t.location, "Unmapped token type: $ttype")
  }

  fun readNothing(rm: ERM, i: TokenIter): Any? {
    i.next();
    return null
  }

  fun readValue(rm: ERM, i: TokenIter): Any? {
    val token = i.current()
    i.next()
    return token.value
  }

  fun readList(rm: ERM, i: TokenIter): Any? {
    val start = i.current().location
    try {
      i.next() // discard the lparen
      var v = List<Any>()
      val sentinel = Object()
      val srm = rm.put(TokenType.RPAREN, { _: Any, _: Any, _: Any -> sentinel}) as ReadMap
      while (i.current().tokenType != TokenType.RPAREN) {
        val el = this.read(srm, i)
        if (el != sentinel) v = v.addLast(el)
      }
      i.next()
      return v
    } catch (e: Exception) {
      throw ParseException(start, "While parsing () list, an exception occurred", e)
    }
  }

  fun readSqList(rm: ERM, i: TokenIter): Any? {
    val start = i.current().location
    try {
      i.next() // discard the lparen
      var v = List<Any>()
      val sentinel = Object()
      val srm = rm.put(TokenType.RBRACE, { _: Any, _: Any, _: Any -> sentinel}) as ReadMap
      while (i.current().tokenType != TokenType.RBRACKET) {
        val el = this.read(srm, i)
        if (el != sentinel) v = v.addLast(el)
      }
      i.next()
      return v
    } catch (e: Exception) {
      throw ParseException(start, "While parsing [] list, an exception occurred", e)
    }
  }

  fun readSet(rm: ERM, i: TokenIter): Any {
    val start = i.current().location
    try {
      i.next()
      var v = Set<Any>()
      val sentinel = Object()
      val srm = rm.put(TokenType.RBRACE, { _: Any, _: Any, _: Any -> sentinel}) as ReadMap
      while (i.current().tokenType != TokenType.RBRACE) {
        val el = this.read(srm, i)
        if (el != sentinel) v = v.add(el)
      }
      i.next()
      return v
    } catch (e: Exception) {
      throw ParseException(start, "While parsing #{} set, an exception occurred", e)
    }
  }

  fun readMap(rm: ERM, i: TokenIter): Any {
    val start = i.current().location
    try {
      i.next()
      var v = Map<Any, Any>()
      val sentinel = Object()
      val srm = rm.put(TokenType.RBRACE, { _: Any, _: Any, _: Any -> sentinel}) as ReadMap
      while (i.current().tokenType != TokenType.RBRACE) {
        val key = this.read(srm, i)
        if (key == sentinel)
          break

        val value = this.read(srm, i)
        if (value == sentinel)
          throw ParseException(start, "While parsing map, got uneven number of kv pairs!")

        v = v.put(key, value)
      }
      i.next()
      return v
    } catch (e: Exception) {
      throw ParseException(start, "While parsing {} map, an exception occurred", e)
    }
  }

  fun readTag(rm: ERM, i: TokenIter): Any? {
    val pos = i.current().location
    i.next()
    val tag = this.read(rm as ReadMap, i)
    val obj = this.read(rm as ReadMap, i)
    return Meta(tag!!, obj!!)
  }

  fun read(rm: ReadMap, i: TokenIter): Any? {
    // yo dawg I heard u leik streams
    while (true) {
      val current: Token<*> = i.current()
      if (current == null) {
        throw ParseException(null, "Ran out of input while reading")
      }
      val handler = rm.get(current.tokenType, notFound) as ReadFn
      val start: StreamLocation<*> = i.current().location
      val res = handler(this, rm as ERM, i)
      if (res != null)
        return res
    }
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
      .put(TokenType.SYMBOL, Reader::readValue)

      // Lists
      .put(TokenType.LPAREN, Reader::readList)
      .put(TokenType.LBRACKET, Reader::readSqList)
      .put(TokenType.LBRACE, Reader::readMap)
      .put(TokenType.HASH_LBRACE, Reader::readSet)

      .put(TokenType.META, Reader::readTag)

      // RHS stuff that is an error by default
      .put(TokenType.RBRACE, Reader::readError)
      .put(TokenType.RBRACKET, Reader::readError)
      .put(TokenType.RPAREN, Reader::readError)
    )

  fun read(rm: ReadMap, reader: java.io.Reader, streamIdentifier: Any): Any? {
    return Reader().read(rm, CurrentIterator(Scanner.scan(reader, streamIdentifier) as Iterator<Token<*>>))
  }

  fun read(rm: ReadMap, buff: String, streamIdentifier: Any): Any? {
    return read(rm, ByteArrayInputStream(buff.toByteArray()).reader(), streamIdentifier)
  }
}

object ReaderTest {
  @JvmStatic
  fun main(args: Array<String>) {
    println("Read map:")
    for (item in Readers.BASE_READ_MAP) {
      val key = item.key()
      val value = item.value()
      println("  $key => $value")
    }

    val iter = CurrentIterator(Scanner.scan(System.`in`.reader(), "STDIN") as Iterator<Token<*>>)

    while (iter.hasNext()) {
      val obj = Reader().read(Readers.BASE_READ_MAP, iter)
      if(obj != null) {
        Printers.println(obj)
      }
    }
  }
}
