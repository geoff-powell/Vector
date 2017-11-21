package com.greenmist.vector.svgkit.css.rules

import java.io.PrintWriter
import java.util.*

/**
 * Created by geoffpowell on 11/17/17.
 */
class MediaRule(val mediaList: Set<Any>) : BaseRule() {

    val statements = Vector<BaseRule>()

    fun add(rule: BaseRule) {
        statements.add(rule)
    }

    override fun serialize(outWriter: PrintWriter) {
        outWriter.print("@media ")
        serializeIteratorWithSeparator(outWriter, mediaList.iterator(), ", ")
        outWriter.println(" {")
        statements.forEach {
            if (it is BaseRule) {
                (it as SelectorRule).serialize(outWriter)
                outWriter.println()
            } else if (it is MediaRule) {
                it.serialize(outWriter)
                outWriter.println()
            }
        }
        outWriter.println("}")
    }
}