package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.Selector

/**
 * Created by geoffpowell on 11/17/17.
 */
class DescendantElementMatcher(selector: Selector, val ancestor: ElementMatcher?, val descendant: ElementMatcher?) : ElementMatcher(selector) {

    var ancestorMatchedDepth: Int = 0

    override fun popElement() {
        if (ancestorMatchedDepth > 0) {
            descendant?.popElement()
        } else {
            ancestor?.popElement()
        }
        ancestorMatchedDepth--
    }

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult? {
        return if (ancestorMatchedDepth > 0) {
            ancestorMatchedDepth++
            descendant?.pushElement(namespace, name, attrs)
        } else {
            val r = ancestor?.pushElement(namespace, name, attrs)
            if (r != null && r.pseudoElement == null) {
                ancestorMatchedDepth = 1
            }
            return null
        }
    }
}