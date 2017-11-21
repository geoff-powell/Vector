package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.SparseStack
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.SiblingElementSelector

/**
 * Created by geoffpowell on 11/17/17.
 */
class SiblingElementMatcher(selector: SiblingElementSelector, val previousMatcher: ElementMatcher?, val currentMatcher: ElementMatcher?) : ElementMatcher(selector) {

    var previousMatched: Boolean = true
    val state = SparseStack()

    override fun popElement() {
        previousMatcher?.popElement()
        currentMatcher?.popElement()
        previousMatched = state.pop() != null
    }

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult? {
        val p = previousMatcher?.pushElement(namespace, name, attrs)
        if (p?.pseudoElement != null) {
            return null
        }
        state.push(p)
        var c = currentMatcher?.pushElement(namespace, name, attrs)
        if (previousMatched == false) {
            c = null
        }
        previousMatched = false
        return c
    }

}