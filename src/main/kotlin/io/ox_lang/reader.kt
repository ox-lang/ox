package io.ox_lang

import java.io.PushbackReader
import java.io.StringReader
import java.io.Reader as IOReader

object Reader {
  @JvmStatic public fun <T> read(stream: IOReader, streamIdentifier: T): Iterator<Token<T>> {
    // yo dawg I heard u leik streams
    val iter = Scanner.scan(PushbackReader(stream), streamIdentifier)
  }

  @JvmStatic public fun <T> readStr(buff: String, streamIdentifier: T): Iterator<Token<T>> {
    return read<T>(StringReader(buff), streamIdentifier)
  }

  @JvmStatic public fun <T> readStrEager(buff: String, streamIdentifier: T): Iterable<Token<T>> {
    val l = java.util.ArrayList<Token<T>>()
    val iter = readStr<T>(buff, streamIdentifier)
    while (iter.hasNext()) {
      l.add(iter.next())
    }
    return l
  }

  @JvmStatic public fun main(args: Array<String>) {
    for ((index, arg) in args.withIndex()) {
      val reader = readStr(arg, String.format("Arg %d", index))
      while (reader.hasNext()) {
        println(reader.next())
      }
    }
  }

}
