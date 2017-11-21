package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import com.greenmist.vector.svgkit.css.matchers.PseudoElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class PseudoElementSelector(val name: String) : Selector() {

    override fun getElementMatcher(): ElementMatcher? = PseudoElementMatcher(this, name)

    override fun getSpecificity(): Int = 1

    override fun serialize(out: PrintWriter) {
        out.print(":")
        out.print(name)
    }
}