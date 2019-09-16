/**
 * @author Reid 'arrdem' McKenzie 2019-9-13
 *
 * A memoizing function.
 * Calls the parent thunk once, caches its value and always returns that value.
 * Also re-throws any exception raised on the first call every time.
 */

package me.arrdem.ox

class MemFn0<R> internal constructor(private val fn: Function0<R>) : Function0<R> {
  private var `val`: R? = null
  private var ex: Exception? = null
  private var valid = false

  override fun invoke(): R {
    if (!this.valid) {
      this.valid = true
      try {
        this.`val` = fn.invoke()
      } catch (e: Exception) {
        this.ex = e
        throw e
      }

    }
    if (this.ex != null)
      sneakyThrow<Throwable>(this.ex!!)
    return this.`val`!!
  }
}

class MemFn1<A, R> internal constructor(private val fn: Function1<A, R>) : Function1<A, R> {
  private var `val`: R? = null
  private var ex: Exception? = null
  private var valid = false

  override fun invoke(o0: A): R {
    if (!this.valid) {
      this.valid = true
      try {
        this.`val` = fn.invoke(o0)
      } catch (e: Exception) {
        this.ex = e
        throw e
      }

    }
    if (this.ex != null)
      sneakyThrow<Throwable>(this.ex!!)
    return this.`val`!!
  }
}

class MemFn2<A, B, R> internal constructor(private val fn: Function2<A, B, R>) : Function2<A, B, R> {
  private var `val`: R? = null
  private var ex: Exception? = null
  private var valid = false

  override fun invoke(o0: A, o1: B): R {
    if (!this.valid) {
      this.valid = true
      try {
        this.`val` = fn.invoke(o0, o1)
      } catch (e: Exception) {
        this.ex = e
        throw e
      }

    }
    if (this.ex != null)
      sneakyThrow<Throwable>(this.ex!!)
    return this.`val`!!
  }
}

object MemFns {
  @JvmStatic
  fun <R> of(fn: Function0<R>): MemFn0<R> {
    return MemFn0(fn)
  }

  @JvmStatic
  fun <A, R> of(fn: Function1<A, R>): MemFn1<A, R> {
    return MemFn1(fn)
  }

  @JvmStatic
  fun <A, B, R> of(fn: Function2<A, B, R>): MemFn2<A, B, R> {
    return MemFn2(fn)
  }
}
