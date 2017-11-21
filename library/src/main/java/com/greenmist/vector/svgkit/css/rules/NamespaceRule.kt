package com.greenmist.vector.svgkit.css.rules

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class NamespaceRule(val prefix: String?, val namespace: String) : BaseRule() {

    override fun serialize(outWriter: PrintWriter) {
        outWriter.print("@namespace ")
        prefix?.let {
            outWriter.print(prefix)
            outWriter.print(" ")
        }
        outWriter.print("\"")
        outWriter.print(namespace)
        outWriter.println("\";")
    }
}