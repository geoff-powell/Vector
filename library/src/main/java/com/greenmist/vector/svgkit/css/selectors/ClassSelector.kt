package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.ClassElementMatcher
import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class ClassSelector : Selector {

    val className: String

    internal constructor(className: String) : super() {
        this.className = className
    }

    override fun getElementMatcher(): ElementMatcher? = ClassElementMatcher(this, className)

    override fun getSpecificity(): Int = 0x100

    override fun serialize(out: PrintWriter) {
        out.print('.')
        out.print(className)
    }

}