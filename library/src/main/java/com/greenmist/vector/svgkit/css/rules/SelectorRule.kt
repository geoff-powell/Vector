package com.greenmist.vector.svgkit.css.rules

import com.greenmist.vector.svgkit.css.selectors.Selector
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class SelectorRule(val selectors: Array<Selector>) : BaseRule() {

    override fun serialize(outWriter: PrintWriter) {
        serializeIteratorWithSeparator(outWriter, selectors.iterator(), ", ")
        outWriter.print(" {")
        serializeProperties(outWriter, true)
        outWriter.print("}")
    }
}