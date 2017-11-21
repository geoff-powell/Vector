package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.Selector

/**
 * Created by geoffpowell on 11/17/17.
 */
class FirstChildElementMatcher(selector: Selector) : ElementMatcher(selector) {

    var firstChild: Boolean = false

    override fun popElement() {
        firstChild = false
    }

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult? {
        val firstChild = this.firstChild
        this.firstChild = true
        return if (firstChild) {
            MatchResult.ALWAYS
        } else {
            null
        }
    }
}