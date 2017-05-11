package org.oxlang.lang;

import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
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
  public void testLexNumber() throws Exception {
    assertEquals(lexTokenType("1"),
        OxlangLexer.NUMBER);

    assertEquals(lexTokenType("-2"),
        OxlangLexer.NUMBER);

    assertEquals(lexTokenType("1.1"),
        OxlangLexer.NUMBER);
  }
}
