/**
 * @author Reid 'arrdem' McKenzie 2019-5-20
 *
 * The Ox scanner.
 * Used to implement the reader.
 */

package io.oxlang

import java.io.PushbackReader
import java.io.Reader
import java.io.StringReader
import java.lang.IllegalStateException
import java.nio.charset.Charset
import java.util.Iterator

/**
 * Tokens are read from streams, which are identified by a
 * StreamIdentifer of type T, defined by the user.
 */
data class StreamLocation<T>(
  val streamIdentifer: T,
  val offset: Long,
  val lineNumber: Long,
  val columnNumber: Long
): Tagged {
  override fun tag(): Symbol {
    return Symbols.of("ox.scanner/streamloc")
  }

  override fun value(): Any {
    return List.of(streamIdentifer, offset, lineNumber, columnNumber)
  }
}

// Tokens are of a type
public enum class TokenType {
  // lists are ()
  LPAREN,
  RPAREN,

  // lists are also []
  LBRACKET,
  RBRACKET,

  // mappings and sets use {}
  LBRACE,
  RBRACE,

  // Whitespace
  NEWLINE,
  WHITESPACE,

  // reader macros
  HASH,          // Used for #foo <expr> reader macros
  HASH_LPAREN,   // Used for #()
  HASH_LBRACE,   // Used for #{}
  HASH_LBRACKET, // Used for #[]

  // More weirdness
  QUOTE,
  META,
  COMMENT,

  // atoms
  STRING,
  NUMBER, SYMBOL, KEYWORD

  // FIXME:
  // - NaN
  // - Inf
  // - Nil / Null
  // True, False
}

// Tokens themselves

public data class Token<T>(
  val tokenType: TokenType,
  val location: StreamLocation<T>,
  val value: Any
): Tagged {
  override fun tag(): Symbol {
    return Symbols.of("ox/token")
  }

  override fun value(): Any {
    return List.of(tokenType, location, value)
  }
}

// Before we can define the scanner, we need the exception it throws

public class ScannerException(
  val location: StreamLocation<Object>,
  message: String,
  cause: Throwable? = null
) : Exception(message, cause)

val ESCAPE_MAP = Map<Char, Char>()
  .put('n', '\n')
  .put('t', '\t')
  .put('r', '\r')
  .put('"', '"')
  .put('\\', '\\')
  .put('f', 12.toChar())
  .put('b', '\b')

// And now for the scanner
private class TokenScanner<T>(
  val stream: PushbackReader,
  val streamIdentifer: T,
  val offset: Long = 0,
  val lineNumber: Long = 0,
  val columnNumber: Long = 0,
  var firstColumnIndex: Long = 0
) : Iterator<Token<T>> {
  // HACK (arrdem 2019-05-20):
  //   Newlines and every other character is a part of its own line.
  //   If we were to compute the "next" position each time we read(), we'd be off by one.
  //   The computed position is after all ONE AHEAD of the current position, which is 0-indexed.
  //   So we do this game where we keep two locations, the "current" and "next" locations.
  private var curLoc: StreamLocation<T> =
    StreamLocation<T>(streamIdentifer, 0, 0, 0)

  private var nextLoc: StreamLocation<T> =
    StreamLocation<T>(streamIdentifer, offset, lineNumber, columnNumber)

  private fun read(): Int {
    val c: Int = this.stream.read()

    if (c == -1) {
      return c
    } else {
      // Maintain location bookkeeping

      // FIXME (arrdem 2019-05-20):
      //   This isn't strictly correct - since we're using a UTF-8 backed
      //   reader, each char we read is quite possibly multibyte. Oh well.
      this.curLoc = this.nextLoc

      if (c == 10) {
        this.nextLoc = StreamLocation<T>(
          streamIdentifer,
          this.curLoc.offset + 1,
          this.curLoc.lineNumber + 1,
          firstColumnIndex
        )
      } else {
        // Yes this is broken for tabs, no I don't care, tabs are 1spc
        // Variable width characters too >.>
        this.nextLoc = StreamLocation<T>(
          streamIdentifer,
          this.curLoc.offset + 1,
          this.curLoc.lineNumber,
          this.curLoc.columnNumber + 1
        )
      }

      return c
    }
  }

  private fun unread(c: Int) {
    this.stream.unread(c)
    this.nextLoc = this.curLoc
  }

  private fun scanString(tt: TokenType, _c: Char): Token<T> {
    val start = curLoc
    val buff = StringBuilder()

    var escaped = false
    while (true) {
      val i = this.read()
      if (i == -1) {
        throw ScannerException(
          start as StreamLocation<Object>,
          "Reached end of stream while scanning a string!")
      } else {
        val c = i.toChar()
        if (escaped) {
          when (val escapedChar: Char? = ESCAPE_MAP.get(c, null)) {
            null -> throw ScannerException(
              start as StreamLocation<Object>,
              String.format("Encountered illegal escaped character %c while scanning a string!", c))
            else -> {
              escaped = false; buff.append(escapedChar)
            }
          }
        } else if (c == '\\') {
          escaped = true
        } else if (c == '\"') {
          break
        } else {
          buff.append(c)
        }
      }
    }
    return Token(tt, curLoc, buff.toString())
  }

  private fun scanSimpleSymbol(buff: StringBuilder) {
    read@ while (true) {
      val i = this.read()
      if (i == -1) {
        return
      } else {
        val c = i.toChar()
        when (c) {
          '(', ')', '[', ']', '{', '}', ';', '#', '\'', ',', '^' -> {
            this.stream.unread(i)
            break@read
          }
          else -> when {
            Character.isWhitespace(i) -> {
              this.stream.unread(i); break@read
            }
            else -> buff.append(c)
          }
        }
      }
    }
  }

  private fun scanPipedSymbol(start: StreamLocation<T>, buff: StringBuilder) {
    while (true) {
      val i = this.read()
      if (i == -1) {
        throw ScannerException(
          start as StreamLocation<Object>,
          String.format("Encountered end of stream while scanning a piped symbol!")
        )
      } else {
        val c = i.toChar()
        if (c == '|') {
          break
        } else {
          buff.append(c)
        }
      }
    }
  }

  private fun scanSymbolKw(
    tt: TokenType,
    startChar: Char,
    start: StreamLocation<T> = curLoc
  ): Token<T> {
    val buff = StringBuilder()
    when (startChar) {
      '|' -> scanPipedSymbol(start, buff)
      ':' -> return scanSymbolKw(TokenType.KEYWORD, this.read().toChar(), curLoc)
      else -> {
        buff.append(startChar); scanSimpleSymbol(buff)
      }
    }

    val res = when (tt) {
      TokenType.SYMBOL -> Symbols.of(buff.toString())
      TokenType.KEYWORD -> Keywords.of(buff.toString())
      else -> throw IllegalStateException("Got tokentype $tt while processing symbol/kw!")
    }
    return Token(tt, start, res)
  }

  private fun scanComment(
    tt: TokenType,
    startChar: Char,
    start: StreamLocation<T> = curLoc
  ): Token<T> {
    val buff = StringBuilder()
    buff.append(startChar)

    while (true) {
      val i = this.read()
      if (i == -1) {
        break
      } else {
        val c = i.toChar()
        if (c == '\n') {
          this.unread(i)
          break
        } else {
          buff.append(c)
        }
      }
    }

    return Token(tt, start, buff.toString())
  }

  private fun scanNumber(
    tt: TokenType,
    startChar: Char,
    start: StreamLocation<T> = curLoc
  ): Token<T> {
    /* Problems here:
     * - Doesn't do bases other than 10
     * - Doesn't do decimal
     * - Doesn't do exponents
     * - Doesn't do fractions/rationals
     *
     * It does however do signs which is at least something
     */
    var value = when (startChar) {
      '-', '+' -> 0
      else -> startChar.toInt() - 48
    }
    val negated: Boolean = when (startChar) {
      '-' -> true
      else -> false
    }

    while (true) {
      val i = this.read()
      if (i == -1) {
        break
      } else if (Character.isDigit(i)) {
        value = value * 10 + (i - 48) // 48 is ord('0')
      } else {
        // We're done. For now. This will have to be more involved later.
        this.unread(i)
        break
      }
    }

    return Token(tt, start, if (negated) -1 * value else value)
  }

  override fun hasNext(): Boolean {
    // I think this is correct - the iterator has
    // SOMETHING as long as the underlying PBR has
    // SOMETHING.
    val i = this.stream.read()
    try {
      return i != -1
    } finally {
      this.stream.unread(i)
    }
  }

  public override fun next(): Token<T>? {
    val c: Int = this.read()

    if (c == -1) {
      return null
    }

    var ch = Character.valueOf(c.toChar())

    val tt = when (c.toChar()) {
      '(' -> TokenType.LPAREN
      ')' -> TokenType.RPAREN
      '[' -> TokenType.LBRACKET
      ']' -> TokenType.RBRACKET
      '{' -> TokenType.LBRACE
      '}' -> TokenType.RBRACE
      ';' -> TokenType.COMMENT
      '\'' -> TokenType.QUOTE
      '\"' -> TokenType.STRING
      ',' -> TokenType.WHITESPACE
      '^' -> TokenType.META
      '\n' -> TokenType.NEWLINE

      // Note that - is ambiguous without lookahead - so look ahead and cheat
      '-', '+' -> {
        val next: Int = this.stream.read()
        try {
          when {
            next == -1 -> TokenType.SYMBOL
            Character.isDigit(next) -> TokenType.NUMBER
            else -> TokenType.SYMBOL
          }
        } finally {
          this.stream.unread(next)
        }
      }

      '#' -> {
        val next: Int = this.stream.read()
        ch = Character.valueOf(next.toChar())
        val tt = when (ch.toChar()) {
          '{' -> TokenType.HASH_LBRACE
          '[' -> TokenType.HASH_LBRACKET
          '(' -> TokenType.HASH_LPAREN
          else -> {
            this.stream.unread(next)
            TokenType.HASH
          }
        }
        return Token(tt, curLoc, "#$ch")
      }

      else -> when {
        // really getting fancy here, gonna need some more logic
        Character.isWhitespace(c) -> TokenType.WHITESPACE
        Character.isDigit(c) -> TokenType.NUMBER
        else -> TokenType.SYMBOL
      }
    }

    // String scanning
    when (tt) {
      TokenType.STRING -> return scanString(tt, ch)
      TokenType.SYMBOL -> return scanSymbolKw(tt, ch)
      TokenType.COMMENT -> return scanComment(tt, ch)
      TokenType.NUMBER -> return scanNumber(tt, ch)
      else -> return Token(tt, curLoc, "$ch")
    }
  }

  public override fun remove() {
    throw UnsupportedOperationException()
  }
}

// Forcing the generated class name
object Scanners {
  @JvmStatic
  public fun <T> scan(stream: Reader, streamIdentifier: T): Iterator<Token<T>> {
    // yo dawg I heard u leik streams
    return TokenScanner<T>(PushbackReader(stream), streamIdentifier)
  }

  @JvmStatic
  public fun <T> scanStr(buff: String, streamIdentifier: T): Iterator<Token<T>> {
    return scan<T>(StringReader(buff), streamIdentifier)
  }

  @JvmStatic
  public fun <T> scanStrEager(buff: String, streamIdentifier: T): Iterable<Token<T>> {
    var l = List<Token<T>>()
    val iter = scanStr<T>(buff, streamIdentifier)
    while (iter.hasNext()) {
      l = l.addLast(iter.next())
    }
    return l
  }
}

object ScannerTest {
  @JvmStatic
  public fun main(args: Array<String>) {
    val scanner = Scanners.scan(System.`in`.reader(), String.format("stdin"))
    while (scanner.hasNext()) {
      val obj = scanner.next()
      if (obj != null) {
        Printers.println(obj)
      }
    }
  }
}
