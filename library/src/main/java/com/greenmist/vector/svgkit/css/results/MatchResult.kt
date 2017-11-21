package com.greenmist.vector.svgkit.css.results

/**
 * Created by geoffpowell on 11/17/17.
 */
class MatchResult {

    val pseudoElement: String?

    internal constructor() : this(null)

    internal constructor(pseudoElement: String?) {
        this.pseudoElement = pseudoElement
    }

    companion object {
        val ALWAYS = MatchResult()
    }
}