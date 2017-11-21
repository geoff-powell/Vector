package com.greenmist.vector.svgkit.css.parser

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/18/17.
 */
class CssValueList(val separator: Char, val values: Array<CssValue>) : CssValue() {

    fun length(): Int = values.size

    fun item(i: Int): CssValue = values[i]

    override fun serialize(outWriter: PrintWriter) {
        var sep = ""
        values.forEach {
            outWriter.print(sep)
            it.serialize(outWriter)
            sep = if (separator == ' ') {
                " "
            } else {
                separator + " "
            }
        }
    }

    fun valueCount(value: Any, op: Char): Int {
        if (value is CssValueList) {
            if (value.separator == op) {
                return value.values.size
            }
        }
        return 1
    }

    fun valueAt(value: Any, index: Int, op: Char): Any {
        if (value is CssValueList) {
            if (value.separator == op) {
                return value.values[index]
            }
        }
        if (index == 0) {
            return value
        }
        throw ArrayIndexOutOfBoundsException(index)
    }
}