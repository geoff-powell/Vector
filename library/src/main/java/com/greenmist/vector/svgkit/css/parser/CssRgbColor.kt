package com.greenmist.vector.svgkit.css.parser

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/18/17.
 */
class CssRgbColor(val rgbColor: Int) : CssValue() {

    private fun getHexString() : String = Integer.toHexString(rgbColor + 0x1000000).substring(1)

    override fun serialize(outWriter: PrintWriter) {
        outWriter.print("#")
        outWriter.print(getHexString())
    }

    override fun toString(): String = "#${getHexString()}"
}