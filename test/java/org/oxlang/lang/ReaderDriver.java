package org.oxlang.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import static org.oxlang.lang.OxlangReader.*;

/**
 * Created by rmckenzie on 5/13/17.
 */
public class ReaderDriver {
  static String readLine(BufferedReader rdr) throws IOException {
    System.out.print("牛 reader test driver> ");
    System.out.flush();
    return rdr.readLine();
  }

  public static void main(String[] args) {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      for(String expr = readLine(br); expr != null; expr = readLine(br)) {
        try {
          Object val = OxlangReader.read(expr + "\n");
          System.out.println(val);
        } catch (Exception e) {
          System.out.println(e.getClass());
          e.printStackTrace(System.out);
          continue;
        }
      }
    } catch (IOException e) {
      e.printStackTrace(System.out);
      return;
    }
  }
}
