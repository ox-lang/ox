package org.oxlang.lang;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by arrdem on 6/12/17.
 */
public class Util {
  public static <T> Stream<T> fromIterator(Iterator<T> it) {
    return StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED),
        false);
  }

  public static <T> Stream<T> fromSpliterator(Spliterator<T> split) {
    return StreamSupport.stream(split, false);
  }
}
