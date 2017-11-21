package com.greenmist.vector.svgkit.css.rules

import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class InlineRule : BaseRule {

    constructor() : super()

    constructor(other: InlineRule) : super(other)

    override fun serialize(outWriter: PrintWriter) = serializeProperties(outWriter, false)

    fun cloneObject(): InlineRule = InlineRule(this)
}