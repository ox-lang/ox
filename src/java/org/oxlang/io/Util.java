package org.oxlang.io;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by arrdem on 6/11/17.
 */
public class Util {
  public static String stringFromReader(Reader rdr) throws IOException {
    char[] buffer = new char[2048];
    StringBuilder sb = new StringBuilder();
    int read = 0;
    while ((read = rdr.read(buffer, 0, buffer.length)) != 0) {
      sb.append(buffer, 0, read);
    }
    return sb.toString();
  }
}
