package io.oxlang

import java.util.Iterator

interface IPeekIterator<T>: ICurrentIterator<T> {
  fun peek(): T
}

class PeekIterator<T>(private val iter: Iterator<T>,
                      private var current: T? = if (iter.hasNext()) iter.next() else null,
                      private var next: T? = if (iter.hasNext()) iter.next() else null):
  IPeekIterator<T>
{
  override fun peek(): T {
    return next as T
  }

  override fun current(): T {
    return current as T
  }

  override fun hasNext(): Boolean {
    return next != null
  }

  override fun next(): T {
    current = next
    if (iter.hasNext()) {
      next = iter.next()
    } else {
      next = null
    }
    return current as T
  }
}
