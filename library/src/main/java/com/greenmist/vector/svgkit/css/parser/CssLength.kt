package com.greenmist.vector.svgkit.css.parser

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/18/17.
 */
class CssLength(val value: Double, val unit: String) : CssValue() {

    override fun serialize(outWriter: PrintWriter) {
        val sv = Math.round(value * 1000) / 1000.0
        if (sv == sv.toInt().toDouble()) {
            outWriter.print(sv.toInt())
       } else {
            outWriter.print(sv)
        }
        outWriter.print(unit)
    }

    override fun toString(): String {
        val sv = Math.round(value * 1000) / 1000.0
        return if (sv == sv.toInt().toDouble()) {
            sv.toInt().toString() + unit
        } else {
            sv.toString() + unit
        }
    }
}