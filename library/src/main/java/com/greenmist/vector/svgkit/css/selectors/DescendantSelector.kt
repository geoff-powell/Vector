package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.DescendantElementMatcher
import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class DescendantSelector(val ancestor: Selector, val descendant: Selector) : Selector() {

    override fun getElementMatcher(): ElementMatcher? = DescendantElementMatcher(this, ancestor.getElementMatcher(), descendant.getElementMatcher())

    override fun getSpecificity(): Int = addSpecificity(ancestor.getSpecificity(), descendant.getSpecificity())

    override fun serialize(out: PrintWriter) {
        ancestor.serialize(out)
        out.print(" ")
        descendant.serialize(out)
    }

}