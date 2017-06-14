package org.oxlang.io;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.util.zip.ZipInputStream;

/**
 * Created by arrdem on 6/11/17.
 *
 * Provides a reader which will only read a slice of some other reader. For instance the zip file reader uses the same
 * InputStream (Reader) instance for scanning by entries as for reading data from the currently-selected entry. If you
 * advance that Reader too far (past the end of the current entry) Bad Things may happen. This Reader makes it possible
 * to wrap the ZipFileInputStream up in another Reader which will only read the currently-selected entry.
 */
public class BoundedReader extends Reader {
  private final Reader rdr;
  private long limit;

  public BoundedReader(Reader rdr, long limit) {
    this.rdr = rdr;
    this.limit = limit;
  }

  @Override
  public int read(@NotNull char[] chars, int off, int len) throws IOException {
    int read = rdr.read(chars, off, (int) Math.min(limit, (long)len));
    this.limit -= read;
    return read;
  }

  @Override
  public void close() throws IOException {

  }
}
