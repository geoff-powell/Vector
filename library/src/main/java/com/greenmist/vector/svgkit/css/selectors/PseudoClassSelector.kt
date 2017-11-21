package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import com.greenmist.vector.svgkit.css.matchers.FirstChildElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class PseudoClassSelector(val name: String) : Selector() {

    override fun getElementMatcher(): ElementMatcher? {
        return if (name == "first-child") {
            FirstChildElementMatcher(this)
        } else null
        // TODO implement other class element matcher
    }

    override fun getSpecificity(): Int = 0x100

    override fun serialize(out: PrintWriter) {
        out.print(":")
        out.print(name)
    }
}