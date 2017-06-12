package org.oxlang.io;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by arrdem on 6/11/17.
 *
 * Helper for reading a single entry out of a ZipInputStream. Provides the appropriate logic for bounding the read to
 * the data affiliated with the given ZipInputStream's current record.
 */
public class ZipEntryReader extends Reader {
  private final ZipInputStream stream;
  private long limit;

  public ZipEntryReader(ZipInputStream stream, ZipEntry entry) {
    this.stream = stream;
    this.limit = entry.getSize();
  }

  @Override
  public int read(@NotNull char[] chars, int off, int len) throws IOException {
    len = (int) Math.min(len, this.limit);
    byte[] buffer = new byte[len];
    int read = stream.read(buffer, off, len);

    this.limit -= read;

    // Decode the bytes conveniently zero-copying
    CharsetDecoder utf8 = Charset.forName("UTF-8").newDecoder();
    utf8.decode(ByteBuffer.wrap(buffer),
        CharBuffer.wrap(chars),
        true);

    // FIXME (arrdem 6/11/2017) should this be return read or return buffer2.length
    return read;
  }

  @Override
  public void close() throws IOException {
  }
}
