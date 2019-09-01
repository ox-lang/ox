package io.oxlang

import java.util.Iterator

interface ICurrentIterator<T>: Iterator<T> {
  fun current(): T
}

class CurrentIterator<T>(private val iter: Iterator<T>,
                        private var current: T? = if (iter.hasNext()) iter.next() else null):
  ICurrentIterator<T>
{

  override fun current(): T {
    return current as T
  }

  override fun hasNext(): Boolean {
    return iter.hasNext()
  }

  override fun next(): T {
    current = iter.next()
    return current as T
  }
}
