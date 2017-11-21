package com.greenmist.vector.svgkit.css.rules

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class ImportRule(val url: String, val mediaList: Set<Any>?) : BaseRule() {

    override fun serialize(outWriter: PrintWriter) {
        outWriter.print("@import url(")
        outWriter.print(url)
        outWriter.print(")")
        mediaList?.let {
            serializeIteratorWithSeparator(outWriter, mediaList.iterator(), ", ")
        }
        outWriter.println(";")
    }
}