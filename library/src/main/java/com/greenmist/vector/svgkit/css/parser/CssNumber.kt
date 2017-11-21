package com.greenmist.vector.svgkit.css.parser

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class CssNumber : CssValue {

    val number: Number

    constructor(number: Number) {
        this.number = number
    }

    constructor(number: Int) {
        this.number = number
    }

    constructor(number: Double) {
        this.number = number
    }

    override fun serialize(outWriter: PrintWriter) {
        outWriter.print(number)
    }

    override fun toString(): String = number.toString()
}