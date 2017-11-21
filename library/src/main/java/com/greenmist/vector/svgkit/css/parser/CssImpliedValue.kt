package com.greenmist.vector.svgkit.css.parser

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/18/17.
 */
class CssImpliedValue(val cssValue: CssValue) : CssValue() {

    override fun serialize(outWriter: PrintWriter) {
        cssValue.serialize(outWriter)
    }
}