package org.oxlang.lang;

/* Generated source files from antlr4 */
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/* Import the antlr runtime */

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
  public static Object read(Reader rdr, OxlangBaseVisitor v) throws IOException {
    ParseTree t = parseReader(rdr).sexpr();
    return v.visit(t);
  }

  /**
   * By default we read to datastructures, although other readers could be used.
   *
   * @param rdr
   * @return
   * @throws IOException
   */
  public static Object read(Reader rdr) throws IOException {
    return read(rdr, new OxlangDatomReader());
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

  /**
   * Parses an entire reader on a streaming basis, returning a streaming iterator of parsed forms.
   *
   * @param rdr
   * @param v
   * @return
   * @throws IOException
   */
  public static Iterator readAll(Reader rdr, OxlangBaseVisitor v) throws IOException {
    OxlangParser p = parseReader(rdr);
    return p.file().sexpr().stream().map(ctx -> (Object)v.visit(ctx)).iterator();
  }

  /**
   * A partial apply of readAll(rdr, visitor) using OxlangDatomReader
   *
   * @param rdr
   * @return
   * @throws IOException
   */
  public static Iterator readAll(Reader rdr) throws IOException {
    return readAll(rdr, new OxlangDatomReader());
  }
}
