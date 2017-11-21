package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.ChildElementMatcher
import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class ChildSelector(val parent: Selector, val child: Selector) : Selector() {

    override fun getElementMatcher(): ElementMatcher? = ChildElementMatcher(this, parent.getElementMatcher(), child.getElementMatcher())

    override fun getSpecificity(): Int = addSpecificity(parent.getSpecificity(), child.getSpecificity())

    override fun serialize(out: PrintWriter) {
        parent.serialize(out)
        out.print(">")
        child.serialize(out)
    }
}