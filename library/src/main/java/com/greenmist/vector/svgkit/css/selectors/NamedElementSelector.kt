package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import com.greenmist.vector.svgkit.css.matchers.NamedElementMatcher
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class NamedElementSelector(val prefix: String?, val namespace: String?, val name: String) : Selector() {

    override fun getElementMatcher(): ElementMatcher? = NamedElementMatcher(this, namespace, name)

    override fun getSpecificity(): Int = 1

    override fun serialize(out: PrintWriter) {
        if (prefix != null) {
            out.print(prefix)
            out.print("|")
        }
        out.print(name)
    }

    fun hasElementNamespace(): Boolean = namespace != null
}