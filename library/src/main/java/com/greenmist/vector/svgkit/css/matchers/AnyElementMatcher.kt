package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.AnyElementSelector

/**
 * Created by geoffpowell on 11/17/17.
 */
class AnyElementMatcher(anyElementSelector: AnyElementSelector) : ElementMatcher(anyElementSelector) {

    override fun popElement() {}

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?) = MatchResult.ALWAYS
}