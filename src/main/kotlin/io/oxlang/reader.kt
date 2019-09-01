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
import java.lang.Exception
import java.util.Iterator
import javax.swing.UIManager.put

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
                     override val cause: Throwable? = null):
  Exception(message, cause)
{
    override fun toString(): String {
      return "Parsing at $location: $message"
    }
}

class Reader(private val notFound: ReadFn = Reader::readError as ReadFn) {
  fun readError(rm: ERM, i: TokenIter): Any {
    val t = i.current()
    val ttype = t.tokenType
    throw ParseException(t.location, "Unmapped token type: $ttype")
  }

  fun readNothing(rm: ERM, i: TokenIter): Any? {
    return null
  }

  fun readValue(rm: ERM, i: TokenIter): Any {
    val token = i.current()
    return token.value
  }

  fun read(rm: ReadMap, i: TokenIter): Any {
    // yo dawg I heard u leik streams
    while (true) {
      var current: Token<*> = i.current()
      if (current == null) {
        throw ParseException(null, "Ran out of input while reading")
      }
      val handler = rm.get(current.tokenType, notFound) as ReadFn
      val start: StreamLocation<*> = i.current().location
      val res = handler(this, rm as ERM, i)
      i.next()
      if (res != null)
        return res
    }
  }
}

object Readers {
  val META_READ_MAP: ReadMap = (
    ReadMap()
      .put(TokenType.LBRACE, {r: Reader, rm: ERM, i: TokenIter ->
        i.next()
        var v = Set<Any>()
        while(i.current().tokenType != TokenType.RBRACE) {
          val el = r.read(BASE_READ_MAP, i)
          if (el != null) v = v.add(el)
        }
        i.next()
        v
      })
    )

  @JvmStatic
  val BASE_READ_MAP: ReadMap = (
    ReadMap()
      // Things we discard by default
      .put(TokenType.COMMENT,    Reader::readNothing)
      .put(TokenType.WHITESPACE, Reader::readNothing)
      .put(TokenType.NEWLINE,    Reader::readNothing)

      // Atoms
      .put(TokenType.NUMBER,     Reader::readValue)
      .put(TokenType.KEYWORD,    Reader::readValue)
      .put(TokenType.SYMBOL,     Reader::readValue)

      // Lists
      .put(TokenType.LPAREN, {r: Reader, rm: ERM, i: TokenIter ->
        i.next()
        var v = List<Any>()
        while(i.current().tokenType != TokenType.RPAREN) {
          val el = r.read(rm as ReadMap, i)
          if (el != null) v = v.addLast(el)
        }
        i.next()
        v
      })

      // Weird shit
      .put(TokenType.HASH, {
        r: Reader, rm: ERM, i: TokenIter ->
        i.next()
        r.read(META_READ_MAP, i)
      })

      .put(TokenType.META, {
        r: Reader, rm: ERM, i: TokenIter ->
        val pos = i.current().location
        i.next()
        val tag = r.read(rm as ReadMap, i)
        val obj = r.read(rm as ReadMap, i)
        ReadObject(Meta(tag, obj), pos)
      })

      // RHS stuff that is an error by default
      .put(TokenType.RBRACE,   Reader::readError)
      .put(TokenType.RBRACKET, Reader::readError)
      .put(TokenType.RPAREN,   Reader::readError)
    )

  fun read(rm: ReadMap, reader: java.io.Reader, streamIdentifier: Any): Any {
    return Reader().read(rm, CurrentIterator(Scanner.scan(reader, streamIdentifier) as Iterator<Token<*>>))
  }

  fun read(rm: ReadMap, buff: String, streamIdentifier: Any): Any {
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

    while (true) {
      print("=> ")
      Printers.println(Reader().read(Readers.BASE_READ_MAP, iter))
    }
  }
}
