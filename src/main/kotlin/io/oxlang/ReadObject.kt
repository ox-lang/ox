package io.oxlang

/** ReadObject
 *
 * Syntax objects are an intermediary result produced by the reader, which serve to box up a read
 * value with syntax information about the value's textual extent.
 */
data class ReadObject<T>(val obj: Object,
                         val start: StreamLocation<T>,
                         val end: StreamLocation<T>) {
}
