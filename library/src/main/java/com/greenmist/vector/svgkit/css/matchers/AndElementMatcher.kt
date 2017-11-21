package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.AndSelector

/**
 * Created by geoffpowell on 11/17/17.
 */
class AndElementMatcher(selector: AndSelector, private var first: ElementMatcher?, private var second: ElementMatcher?) : ElementMatcher(selector) {

    override fun popElement() {
        second?.popElement()
        first?.popElement()
    }

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult? {
        val f = first?.pushElement(namespace, name, attrs)
        val s = second?.pushElement(namespace, name, attrs)

        return when {
            f == null || s == null -> null
            f.pseudoElement == null -> s
            s.pseudoElement == null -> f
            else -> null
        }
    }

    init {
        if (first == null || second == null)
            throw NullPointerException()
    }
}