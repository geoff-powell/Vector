package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.Selector

/**
 * Created by geoffpowell on 11/17/17.
 */
class ClassElementMatcher(selector: Selector, val className: String) : ElementMatcher(selector) {

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult? {
        if (attrs == null) {
            return null
        }
        val classAttrName = getClassAttribute(namespace, name) ?: return null
        val classValue = attrs.get("", classAttrName) ?: return null
        return if (AttributeElementMatcher.isInList(classValue, className)) {
            MatchResult.ALWAYS
        } else {
            null
        }
    }

    override fun popElement() {}

    companion object {
        var FB2NS = "http://www.gribuser.ru/xml/fictionbook/2.0"

        fun getClassAttribute(namespace: String?, name: String?): String? {
            return if (namespace == FB2NS) {
                // By FB2's designer's infinite wisdom, CSS class is allowed only on an element named "style" and is given by the attribute named "name".
                if (name == "style") {
                    "name"
                } else {
                    null
                }
                // assume it is "class" (holds true for XHTML and SVG types)
            } else "class"
        }
    }
}