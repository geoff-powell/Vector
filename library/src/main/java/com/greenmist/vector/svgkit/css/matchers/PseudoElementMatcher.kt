package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.PseudoElementSelector

/**
 * Created by geoffpowell on 11/17/17.
 */
class PseudoElementMatcher(selector: PseudoElementSelector, pseudoElement: String) : ElementMatcher(selector) {

    val result: MatchResult = MatchResult(pseudoElement)

    override fun popElement() {}

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult = result

}