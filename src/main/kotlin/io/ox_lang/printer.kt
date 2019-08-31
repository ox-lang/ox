package io.ox_lang

import java.io.OutputStream
import io.lacuna.bifurcan.Map as LMap

class Printer {
  val HANDLERS = LMap<Class, ((Object) -> (java.lang.String))>()
    .put(io.ox_lang.Symbol::class, {s: Object -> ((Symbol)s).toString()})
    .put(io.ox_lang.Keyword::class, {k: Object -> ((Keyword)k).toString()})


  fun print(stream: OutputStream!, o: Object) {
  }
}
