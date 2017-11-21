package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.NamedElementSelector

/**
 * Created by geoffpowell on 11/17/17.
 */
class NamedElementMatcher(selector: NamedElementSelector, val namespace: String?, val name: String) : ElementMatcher(selector) {

    override fun popElement() {}

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult? {
        return if (this.name == name && (this.namespace == null || this.namespace == namespace)) {
            MatchResult.ALWAYS
        } else {
            null
        }
    }

}