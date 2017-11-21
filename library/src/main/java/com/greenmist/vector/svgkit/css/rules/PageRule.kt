package com.greenmist.vector.svgkit.css.rules

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class PageRule(val pseudo: String?) : BaseRule() {

    override fun serialize(outWriter: PrintWriter) {
        outWriter.println("@page")
        pseudo?.let {
            outWriter.print(" :")
            outWriter.print(pseudo)
        }
        outWriter.println(" {")
        serializeProperties(outWriter, true)
        outWriter.println("}")
    }
}