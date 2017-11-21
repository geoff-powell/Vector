package com.greenmist.vector.svgkit.css.rules

import com.greenmist.vector.svgkit.css.Serializer
import com.greenmist.vector.svgkit.css.parser.CssValue
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

/**
 * Created by geoffpowell on 11/17/17.
 */
abstract class BaseRule : Serializer {

    val properties: TreeMap<String, CssValue>

    constructor(properties: TreeMap<String, CssValue>) {
        this.properties = properties
    }

    constructor() : this(TreeMap())

    protected constructor(other: BaseRule) : this() {
        other.properties.entries.forEach {
            properties.put(it.key, it.value)
        }
    }

    fun isEmpty() = properties.isEmpty()

    fun get(property: String): CssValue? = properties[property]

    fun set(property: String, value: CssValue?) {
        if (value != null) {
            properties.put(property, value)
        } else {
            properties.remove(property)
        }
    }

    fun properties() = properties.keys.iterator()

    fun serializeProperties(out: PrintWriter, newlines: Boolean) {
        properties.entries.iterator().forEach {
            if (newlines)
                out.print('\t')
            out.print(it.key)
            out.print(": ")
            it.value.serialize(out)
            out.print(";")
            if (newlines)
                out.println()
            else
                out.print(' ')
        }
    }

    override fun toString(): String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        serialize(printWriter)
        printWriter.flush()
        return stringWriter.toString()
    }
}