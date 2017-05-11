package org.oxlang.lang;

import java.util.List;

import org.antlr.v4.runtime.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LexerTest {

  static List<Token> lexString(String text) {
    OxlangLexer l = new OxlangLexer(CharStreams.fromString(text));
    CommonTokenStream t = new CommonTokenStream(l);
    return t.getTokens();
  }

  static Token lexToken(String text) {
    return lexString(text).get(0);
  }

  static int lexTokenType(String text) {
    return lexToken(text).getType();
  }

  @Test
  public void testLexSymbol() throws Exception {
    assertEquals(lexTokenType("f"),
        OxlangLexer.SYMBOL);

    assertEquals(lexTokenType("foo"),
        OxlangLexer.SYMBOL);

    assertEquals(lexTokenType("foo/bar"),
        OxlangLexer.SYMBOL);

    assertEquals(lexTokenType("/"),
        OxlangLexer.SYMBOL);

    assertEquals(lexTokenType("+"),
        OxlangLexer.SYMBOL);

    assertEquals(lexTokenType("-"),
        OxlangLexer.SYMBOL);
  }

  @Test
  public void testLexInteger() throws Exception {
    assertEquals(lexTokenType("1"),
        OxlangLexer.INTEGER);

    assertEquals(lexTokenType("-2"),
        OxlangLexer.INTEGER);

    assertEquals(lexTokenType("100_000_000"),
        OxlangLexer.INTEGER);
  }

  @Test
  public void testLexFloat() throws Exception {
    assertEquals(lexTokenType("1.1"),
        OxlangLexer.FLOAT);

    assertEquals(lexTokenType("-1.0"),
        OxlangLexer.FLOAT);

    assertEquals(lexTokenType("+3.14159"),
        OxlangLexer.FLOAT);

    assertEquals(lexTokenType("1e0"),
        OxlangLexer.FLOAT);

    assertEquals(lexTokenType("-1e0"),
        OxlangLexer.FLOAT);

    assertEquals(lexTokenType("1e0"),
        OxlangLexer.FLOAT);

    assertEquals( lexTokenType("2e-15"),
        OxlangLexer.FLOAT);
  }
}
