package com.greenmist.vector.svgkit.css.parser

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/18/17.
 */
class CssQuotedString(val text: String) : CssValue() {

    override fun serialize(outWriter: PrintWriter) {
        outWriter.print("'")
        outWriter.print(text.replace("'".toRegex(), "\\'"))
        outWriter.print("'")
    }
}