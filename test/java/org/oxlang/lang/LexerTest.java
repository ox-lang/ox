package org.oxlang.lang;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LexerTest {

  static List<Token> lexString(String text) {
    OxlangLexer l = new OxlangLexer(CharStreams.fromString(text));
    CommonTokenStream t = new CommonTokenStream(l);
    t.fill();
    return t.getTokens();
  }

  static void assertToken(String text, int type) {
    List<Token> tokens = lexString(text);
    if (tokens != null) {

      // Either we got one token, or we got two tokens, one of which is EOF.
      boolean flag = (tokens.size() == 1
                      || (tokens.size() == 2
                          && tokens.get(1).getType() == OxlangLexer.EOF));
      assertTrue(
          String.format(
              "Expected '%s' to lex to a single token (%d), got %d (%s)",
              text, type, tokens.size(),
              tokens.stream()
                  .map(token -> token.toString())
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
  public void testLexSymbol() {
    String[] examples = {"foo", "bar13", "foo/bar", "/", "+", "-", "_", "_foo", "_13"};

    for (String s : examples)
      assertToken(s, OxlangLexer.SYMBOL);
  }

  @Test
  public void testLexInteger() {
    String[] examples = {"1", "-2", "100_000_000"};

    for (String s : examples)
      assertToken(s, OxlangLexer.INTEGER);
  }

  @Test
  public void testLexFloat() {
    String[] examples = {"100_000.0", "1.1", "-1.2", "+1.3", "1e0", "-1e15", "2.0e-15_000"};

    for (String s : examples)
      assertToken(s, OxlangLexer.FLOAT);
  }

  @Test
  public void testLexString() {
    String[] examples = {
        "\"foo\"",
        "\"foo \\\"bar baz\"",
        "\"\"",
        "\"\\\"\\\"\\\"\\\"\"",
    };

    for (String s : examples)
      assertToken(s, OxlangLexer.STRING);
  }
}
