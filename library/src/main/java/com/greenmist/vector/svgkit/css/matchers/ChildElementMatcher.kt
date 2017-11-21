package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.SparseStack
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.ChildSelector

/**
 * Created by geoffpowell on 11/17/17.
 */
class ChildElementMatcher(selector: ChildSelector, val parent: ElementMatcher?, val child: ElementMatcher?) : ElementMatcher(selector) {
    var parentMatched: Boolean = false
    val state = SparseStack()

    override fun popElement() {
        parent?.popElement()
        parentMatched = state.pop() != null
        if (parentMatched) {
            child?.popElement()
        }
    }

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult? {
        val isParentMatched = this.parentMatched
        state.push(if (isParentMatched) true else null)
        val r = parent?.pushElement(namespace, name, attrs)
        this.parentMatched = r != null && r.pseudoElement == null

        return if (isParentMatched) {
            child?.pushElement(namespace, name, attrs)
        } else {
            null
        }
    }
}