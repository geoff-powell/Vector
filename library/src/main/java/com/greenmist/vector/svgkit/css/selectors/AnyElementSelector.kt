package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.AnyElementMatcher
import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class AnyElementSelector : Selector() {

    override fun getElementMatcher(): ElementMatcher? = AnyElementMatcher(this)

    override fun serialize(out: PrintWriter) {
        out.print("*")
    }

    override fun getSpecificity(): Int = 0
}