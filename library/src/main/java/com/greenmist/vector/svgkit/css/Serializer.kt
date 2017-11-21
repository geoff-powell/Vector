package com.greenmist.vector.svgkit.css

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/18/17.
 */
abstract class Serializer {

    abstract fun serialize(outWriter: PrintWriter)

    fun serializeIteratorWithSeparator(outWriter: PrintWriter, iterator: Iterator<Any>, separator: String) {
        var currentSeparator = ""
        iterator.forEach {
            outWriter.print(currentSeparator)
            outWriter.print(it.toString())
            currentSeparator = separator
        }
    }
}