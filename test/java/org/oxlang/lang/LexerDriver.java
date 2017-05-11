package org.oxlang.lang;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

/**
 * Created by rmckenzie on 5/11/17.
 */
public class LexerDriver {
  public static void main(String[] args) {
    try {
      OxlangLexer l = new OxlangLexer(CharStreams.fromStream(System.in));
      CommonTokenStream ts = new CommonTokenStream(l);
      for (Token t: new TokenSequence(ts))
        System.out.println(t);

      System.out.println(String.format("Got %d tokens", ts.size()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
