package org.oxlang.lang;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.v4.runtime.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

  static void assertToken(String text, int type) {
    List<Token> tokens = lexString(text);
    if (tokens != null) {

      boolean flag = tokens.size() == 1;
      assertTrue(
          String.format(
              "Expected '%s' to lex to a single token (%d), got %d (%s)",
              text, type, tokens.size(),
              tokens.stream()
                  .map(token -> Integer.toString(token.getType()))
                  .collect(Collectors.joining(", "))),
          flag);

      if (flag) {
        Token t = tokens.get(0);

        flag = t.getType() == type;
        assertTrue(
            String.format(
              "Expected '%s' to lext to token %d, got %d",
              text, type, t.getType()),
            flag);
      }
    }
  }

  @Test
  public void testLexSymbol() throws Exception {
    String[] examples = {"foo", "foo/bar", "/", "+", "-"};

    for (String s: examples)
      assertToken(s, OxlangLexer.SYMBOL);
  }

  @Test
  public void testLexInteger() throws Exception {
    String[] examples = {"1", "-2", "100_000_000"};

    for (String s: examples)
      assertToken(s, OxlangLexer.SYMBOL);
  }

  @Test
  public void testLexFloat() throws Exception {
    String[] examples = {"1.0", "1.1", "-1.2", "+1.3", "1e0", "-1e15", "2.0e-15"};

    for (String s : examples)
      assertToken(s, OxlangLexer.FLOAT);
  }
}
