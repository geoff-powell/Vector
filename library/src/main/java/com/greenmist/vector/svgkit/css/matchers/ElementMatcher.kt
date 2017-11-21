package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.Selector

/**
 * Created by geoffpowell on 11/17/17.
 */
abstract class ElementMatcher(val selector: Selector) {

    abstract fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult?

    abstract fun popElement()

}