package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.matchers.AttributeElementMatcher
import com.greenmist.vector.svgkit.css.matchers.ElementMatcher
import com.greenmist.vector.svgkit.css.parser.CssValue
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/17/17.
 */
class AttributeSelector(val prefix: String?, val namespace: String?, val attr: String, val operation: String, val cssValue: CssValue) : Selector() {

    override fun getElementMatcher(): ElementMatcher? = AttributeElementMatcher(this, namespace, attr, operation, cssValue)

    override fun getSpecificity(): Int = 0x100

    override fun serialize(out: PrintWriter) {
        out.print("[")
        if (cssValue == null) {
            if (prefix != null) {
                out.print(prefix)
                out.print("|")
            }
            out.print(attr)
        } else {
            if (prefix != null) {
                out.print(prefix)
                out.print("|")
            }
            out.print(attr)
            out.print(operation)
            cssValue.serialize(out)
        }
        out.print("]")
    }

}