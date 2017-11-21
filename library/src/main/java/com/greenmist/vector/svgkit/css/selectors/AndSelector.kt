package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.AndElementMatcher
import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class AndSelector(val first: Selector, val second: Selector) : Selector() {

    override fun getElementMatcher(): ElementMatcher? = AndElementMatcher(this, first.getElementMatcher(), second.getElementMatcher())

    override fun getSpecificity(): Int = addSpecificity(first.getSpecificity(), second.getSpecificity())

    override fun serialize(out: PrintWriter) {
        first.serialize(out)
        second.serialize(out)
    }
}