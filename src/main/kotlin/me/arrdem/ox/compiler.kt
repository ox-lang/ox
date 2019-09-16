/**
 * @author Reid 'arrdem' McKenzie 2019-09-07
 *
 * The Ox compiler
 */

package me.arrdem.ox

import io.lacuna.bifurcan.Maps
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.time.Instant
import java.util.*
import javax.naming.Name

class OxClassLoader : ClassLoader() {
  private var classCache = Maps.EMPTY as Map<String, SoftReference<Class<*>>>
  private val rq = ReferenceQueue<Class<*>>()

  @Synchronized
  private fun cleanCache() {
    while (rq.poll() != null)
      for (e in classCache) {
        val v = e.value()
        if (v.get() == null)
          classCache = classCache.remove(e.key())
      }
  }

  @Synchronized
  fun defineClass(name: String, bytes: ByteArray): Class<*> {
    cleanCache()
    val c = defineClass(name, bytes, 0, bytes.size)
    classCache = classCache.put(name, SoftReference(c, rq))
    return c
  }

  override fun findClass(name: String): Class<*> {
    val cr: SoftReference<Class<*>>? = classCache.get(name, null)
    if (cr != null) {
      val c = cr.get()
      if (c != null) return c
      else classCache.remove(name, cr)
    }
    cleanCache()
    return super.findClass(name)
  }
}

class Environment(val loader: OxClassLoader = OxClassLoader()) {

}

object Compiler {
  fun analyze(o: Any) {
  }
}
