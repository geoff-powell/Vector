package com.greenmist.vector.svgkit.css.parser

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/18/17.
 */
class CssFunction(val indent: String, val params: Array<CssValue>) : CssValue() {

    override fun serialize(outWriter: PrintWriter) {
        outWriter.print(indent)
        outWriter.print('(')
        serializeIteratorWithSeparator(outWriter, params.iterator(), ", ")
        outWriter.print(')')
    }
}