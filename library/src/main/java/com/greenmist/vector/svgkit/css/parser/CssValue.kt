package com.greenmist.vector.svgkit.css.parser

import com.greenmist.vector.svgkit.css.Serializer
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
abstract class CssValue : Serializer() {

    fun toCssString() : String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        serialize(printWriter)
        printWriter.flush()
        return stringWriter.toString()
    }

    override fun toString() = toCssString()
}