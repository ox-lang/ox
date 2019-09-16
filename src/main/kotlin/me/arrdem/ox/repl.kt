/**
 * @author Reid 'arrdem' McKenzie 2019-09-07
 *
 * A sketch driver for a command line REPL.
 *
 * When complete, this'll be the "bare" REPL entry point.
 */

package me.arrdem.ox

import java.io.IOError
import java.io.Reader
import java.lang.Exception

object ConsoleRepl {
  @JvmStatic
  fun main(args: Array<String>) {
    val rdr = System.`in`.reader()
    var environment = null
    var input = 0

    while(true) {
      try {
        print("=> ")
        val expr = Readers.read(rdr as Reader, "STDIN: $input")
        val res = expr // FIXME: evaluate
        Printers.println(res)
      } catch (e: IOError) {
        break
      } catch (e: Exception) {

      } finally {
        input += 1
      }
    }
  }
}
