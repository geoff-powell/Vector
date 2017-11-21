package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.IdSelector

/**
 * Created by geoffpowell on 11/17/17.
 */
class IdElementMatcher(selector: IdSelector, val id: String) : ElementMatcher(selector) {

    override fun popElement() {}

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult? {
        val id = attrs?.get("", "id")
        return id?.let {
            MatchResult.ALWAYS
        }
    }
}