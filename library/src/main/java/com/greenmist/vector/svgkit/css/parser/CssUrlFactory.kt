package com.greenmist.vector.svgkit.css.parser

/**
 * Created by geoffpowell on 11/18/17.
 */
interface CssUrlFactory {

    fun createCSSURL(url: String): CssUrl
}