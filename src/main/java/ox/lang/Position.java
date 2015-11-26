package ox.lang;

import java.io.File;
import java.io.IOException;

public class Position {
  public final int line, column, offset;
  public final File file;

  private String filename = null;
  private static String nofile = "*NO_FILE*";

  public Position(File file, int line, int column, int offset) {
    this.file = file;
    this.line = line;
    this.column = column;
    this.offset = offset;
  }

  private String getFileName() {
    if(filename != null) {
      return filename;
    } else if(file != null) {
      try {
        filename = file.getCanonicalPath();
      } catch(IOException e) {
        filename = nofile;
      }
    } else {
        filename = nofile;
    }
    return filename;
  }

  public String toString() {
    return String.format("%s (%d:%d)"
                         , getFileName()
                         , line
                         , column
                         );
  }
}
