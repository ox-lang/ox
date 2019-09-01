package io.oxlang;

import kotlin.test.assertEquals;
import org.junit.Test;

class ScannerTest {
  fun scanTypes(input: String, exId: String): kotlin.collections.List<TokenType> {
    return Scanner.scanStrEager(input, exId).map { t: Token<String> -> t.tokenType}
  }

  fun scanOne(input: String, exId: String): Token<String> {
    return Scanner.scanStrEager(input, exId).first()
  }

  @Test fun testScanParens(): Unit {
    assertEquals(
      listOf<TokenType>(
        TokenType.LPAREN,
        TokenType.RPAREN
      ),
      scanTypes("()", "test-1")
    );
  }

  @Test fun testScanBrackets(): Unit {
    assertEquals(
      listOf<TokenType>(
        TokenType.LBRACKET,
        TokenType.RBRACKET
      ),
      scanTypes("[]", "test-2")
    );
  }

  @Test fun testScanBraces(): Unit {
    assertEquals(
      listOf<TokenType>(
        TokenType.LBRACE,
        TokenType.RBRACE
      ),
      scanTypes("{}", "test-3")
    );
  }

  @Test fun testScanSymbols(): Unit {
    assertEquals(
      listOf<TokenType>(
        TokenType.SYMBOL,
        TokenType.WHITESPACE,
        TokenType.SYMBOL,
        TokenType.WHITESPACE,
        TokenType.SYMBOL
      ),
      // Simple symbols
      scanTypes("foo-bar +foo-bar+ |baz qux|", "test-4")
    )
  }

  @Test fun testPuncBreaksSymbols(): Unit {
    assertEquals(
      listOf<TokenType>(
        TokenType.SYMBOL,

        TokenType.LPAREN,
        TokenType.SYMBOL,
        TokenType.RPAREN,

        TokenType.SYMBOL,

        TokenType.LBRACKET,
        TokenType.SYMBOL,
        TokenType.RBRACKET,

        TokenType.SYMBOL,

        TokenType.LBRACE,
        TokenType.SYMBOL,
        TokenType.RBRACE,

        TokenType.SYMBOL,
        TokenType.HASH,
        TokenType.SYMBOL,
        TokenType.QUOTE,
        TokenType.SYMBOL,
        TokenType.META,
        TokenType.SYMBOL
      ),
      // This isn't one long symbol, it's a whole pile of them
      scanTypes("f(o)o[b]a{r}b#a'z^q", "test-5")
    )
  }

  @Test fun testScanKeyword(): Unit {
    assertEquals(
      listOf<TokenType>(
        TokenType.KEYWORD,
        TokenType.WHITESPACE,
        TokenType.KEYWORD,
        TokenType.WHITESPACE,
        TokenType.KEYWORD,
        TokenType.WHITESPACE,
        TokenType.KEYWORD
      ),
      scanTypes(":foo-bar :|foo bar| ::foo-bar ::|foo bar|", "test-6")
    )
  }

  @Test fun testScanNumber(): Unit {
    assertEquals(
      Token(TokenType.NUMBER, StreamLocation("test-7", 0, 0, 0), -13),
      scanOne("-13", "test-7")
    )

    assertEquals(
      Token(TokenType.NUMBER, StreamLocation("test-7", 0, 0, 0), 123),
      scanOne("+123", "test-7")
    )

    assertEquals(
      Token(TokenType.NUMBER, StreamLocation("test-7", 0, 0, 0), 6),
      scanOne("6", "test-7")
    )
  }

  @Test fun testScanComment(): Unit {
    assertEquals(
      Token(TokenType.COMMENT, StreamLocation("test-8", 0, 0, 0), "; foo"),
      scanOne("; foo", "test-8")
    )

    assertEquals(
      listOf<TokenType>(
        TokenType.SYMBOL,
        TokenType.WHITESPACE,
        TokenType.COMMENT,
        TokenType.NEWLINE
      ),
      scanTypes("foo ;bar\n", "test-9")
    )
  }
}
