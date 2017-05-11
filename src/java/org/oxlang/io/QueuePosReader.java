package org.oxlang.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

public class QueuePosReader
  extends PositionReader {

  private boolean valid = false;
  private int res = -1;
  
  public QueuePosReader(Reader r) {
    super(r);
  }

  public QueuePosReader(File f)
    throws FileNotFoundException {
    super(f);
  }

  public int read() throws IOException {
    valid = false;
    res = -1;
    return super.read();
  }

  public int peek() throws IOException {
    if(valid) {
      return res;
    } else {
      res = super.read();
      valid = true;
      return res;
    }
  }

  public void pop() {
    valid = false;
  }
}
