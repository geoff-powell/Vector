package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import com.greenmist.vector.svgkit.css.matchers.SiblingElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class SiblingElementSelector(val previousSelector: Selector, val currentSelector: Selector) : Selector() {

    override fun getElementMatcher(): ElementMatcher? = SiblingElementMatcher(this, previousSelector.getElementMatcher(), currentSelector.getElementMatcher())

    override fun getSpecificity(): Int = addSpecificity(previousSelector.getSpecificity(), currentSelector.getSpecificity())

    override fun serialize(out: PrintWriter) {
        previousSelector.serialize(out)
        out.print("+")
        currentSelector.serialize(out)
    }
}