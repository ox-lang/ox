package org.oxlang.lang;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import static org.oxlang.lang.OxlangReader.*;

/**
 * Created by rmckenzie on 5/13/17.
 */
public class ReaderDriver {
  public static void main(String[] args) {
    try {
      for (Iterator it = OxlangReader.readAll(new InputStreamReader(System.in)); it.hasNext(); ) {
        Object o = it.next();
        System.out.println(String.format("%s", o));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
