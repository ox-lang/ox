// Input/Output helpers (java.io complement) for Ox

package io.ox_lang.io

import java.io.InputStream
import java.io.OutputStream

public class PushbackInputStream(
  private val stream: InputStream,
  private val length: Int = 64
) : InputStream() {

  private var idx = length
  private val buff = IntArray(length)

  override fun available(): Int {
    return (this.buff.size - this.idx) + this.stream.available()
  }

  override fun close() {
    return this.stream.close()
  }

  override fun mark(readlimit: Int) {
    throw UnsupportedOperationException()
  }

  override fun markSupported(): Boolean {
    return false
  }

  override fun read(): Int {
    if (this.idx < this.buff.size) {
      return this.buff[this.idx++]
    } else {
      return this.stream.read()
    }
  }

  fun unread(c: Int) {
    // will throw an indexoutofbounds
    this.buff[this.idx--] = c
  }

  fun read(b: Array<Byte>): Int {
    return this.read(b, 0, b.size)
  }

  fun read(b: Array<Byte>, off: Int, len: Int): Int {
    var idx = 0
    while ((idx + off) < len) {
      val c = this.read()
      if (c == -1) {
        break
      } else {
        b[off + (idx++)] = c.toByte()
      }
    }

    return idx
  }

  override fun readAllBytes(): ByteArray {
    throw UnsupportedOperationException()
  }

  // Seems to be for buffered streams for which a user may wish to force the buffer to be broken
  fun readNBytes(b: Array<Byte>, off: Int, len: Int): Int {
    return this.read(b, off, len)
  }

  override fun reset() {
    throw UnsupportedOperationException()
  }

  override fun skip(n: Long): Long {
    var k: Long = 0
    while (n > 0 && k++ < n) {
      if (this.read() == -1) {
        break
      }
    }

    return k
  }

  override fun transferTo(out: OutputStream): Long {
    var k: Long = 0
    while (true) {
      val c = this.read()
      if (c == -1) {
        break
      } else {
        out.write(c)
        k++
      }
    }

    return k
  }
}
