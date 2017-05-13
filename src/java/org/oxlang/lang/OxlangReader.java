package org.oxlang.lang;

/* Generated source files from antlr4 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.oxlang.lang.OxlangLexer;
import org.oxlang.lang.OxlangParser;
import org.oxlang.lang.OxlangBaseListener;
import org.oxlang.lang.OxlangBaseVisitor;

/* Import the antlr runtime */
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class OxlangReader {

  /**
   * Builds an Ox parser for the given OxlangReader
   *
   * @param rdr
   * @return OxlangParser
   * @throws IOException
   */
  public static OxlangParser parseReader(Reader rdr) throws IOException {
    OxlangLexer l = new OxlangLexer(CharStreams.fromReader(rdr));
    CommonTokenStream t = new CommonTokenStream(l);
    return new OxlangParser(t);
  }

  /**
   * Parses a single s-expression from the given reader
   *
   * @param rdr
   * @return The parsed s-expression
   * @throws IOException
   */
  public static Object read(Reader rdr) throws IOException {
    return parseReader(rdr).sexpr();
  }

  /**
   * Parses a single s-expression from the given text
   *
   * @param text
   * @return The parsed s-expression
   * @throws IOException
   */
  public static Object read(String text) throws IOException {
    return read(new StringReader(text));
  }

  /**
   * Parses a single s-expression from the given file
   *
   * @param f
   * @return The parsed s-expression
   * @throws IOException
   */
  public static Object read(File f) throws IOException {
    return read(new FileReader(f));
  }
}
