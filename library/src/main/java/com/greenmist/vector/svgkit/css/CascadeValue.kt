package com.greenmist.vector.svgkit.css

import com.greenmist.vector.svgkit.css.parser.CssValue
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class CascadeValue(private val cssValue: CssValue, private val specificity: Int, private val importance: Int, private val order: Int) : CssValue() {

    fun compareSpecificity(other: CascadeValue?): Int {
        if (specificity != other?.specificity) {
            return specificity - (other?.specificity ?: 0)
        }
        return if (importance != other.importance) {
            importance - other.importance
        } else {
            order - other.order
        }
    }

    override fun serialize(outWriter: PrintWriter) {
        throw RuntimeException("Not implemented, shouldn't be called")
    }
}