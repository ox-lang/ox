package ox.lang.parser;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;

public class PositionReader
  extends BufferedReader {

  private Position current, next;
  private File f;

  public PositionReader(Reader r) {
    super(r);

    f = null;
    current = new Position(f, 0, 0, 0);
    next = current;
  }

  public PositionReader(File f)
    throws FileNotFoundException {
    super(new FileReader(f));

    this.f = f;
    current = new Position(f, 0, 0, 0);
    next = current;
  }

  public Position getPos() {
    if(current == null)
        throw new IllegalStateException("Position cannot be null!");
    return current;
  }

  public int read() throws IOException {
    int chr = super.read();
    current = next;

    int
      noffset = current.offset + 1,
      nline   = current.line,
      ncol    = current.column;

    if(chr == '\n' || chr == 12) {
      nline++;
      ncol = 0;
    } else {
      ncol++;
    }

    next = new Position(f, nline, ncol, noffset);

    return chr;
  }
}
