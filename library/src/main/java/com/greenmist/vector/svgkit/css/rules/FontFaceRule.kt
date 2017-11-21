package com.greenmist.vector.svgkit.css.rules

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class FontFaceRule : BaseRule() {

    override fun serialize(outWriter: PrintWriter) {
        outWriter.println("@font-face {")
        serializeProperties(outWriter, true)
        outWriter.println("}")
    }
}