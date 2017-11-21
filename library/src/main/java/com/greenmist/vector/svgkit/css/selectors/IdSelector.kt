package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import com.greenmist.vector.svgkit.css.matchers.IdElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class IdSelector(val id: String) : Selector() {

    override fun getElementMatcher(): ElementMatcher? = IdElementMatcher(this, id)

    override fun getSpecificity(): Int = 0x10000

    override fun serialize(out: PrintWriter) {
        out.print("#")
        out.print(id)
    }
}