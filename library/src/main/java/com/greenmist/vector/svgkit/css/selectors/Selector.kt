package com.greenmist.vector.svgkit.css.selectors

import com.greenmist.vector.svgkit.css.Serializer
import com.greenmist.vector.svgkit.css.matchers.ElementMatcher

/**
 * Created by geoffpowell on 11/17/17.
 */
abstract class Selector : Serializer() {

    abstract fun getElementMatcher(): ElementMatcher?

    abstract fun getSpecificity(): Int

    fun addSpecificity(s1: Int, s2: Int): Int = s1 + s2
}