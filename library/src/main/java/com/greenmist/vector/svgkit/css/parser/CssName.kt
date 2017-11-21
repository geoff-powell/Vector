package com.greenmist.vector.svgkit.css.parser

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/18/17.
 */
class CssName(val name: String) : CssValue() {

    override fun serialize(outWriter: PrintWriter) {
        outWriter.print(name)
    }

    override fun toString(): String = name
}