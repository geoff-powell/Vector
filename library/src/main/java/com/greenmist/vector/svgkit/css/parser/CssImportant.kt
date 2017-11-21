package com.greenmist.vector.svgkit.css.parser

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class CssImportant(private val value: CssValue) : CssValue() {

    override fun serialize(outWriter: PrintWriter) {
        value.serialize(outWriter)
        outWriter.print(" !important")
    }

    fun getValue(): Any = value
}