package com.greenmist.vector.lib.model

import android.graphics.Color
import com.greenmist.vector.lib.keyword.ColorKeywords
import com.greenmist.vector.svg.exception.SvgParseException

/**
 * Created by geoff.powell on 11/20/17.
 */
class CssColor(
        val color: Int = INT_COLOR_BLACK
) {
    val alpha = Color.alpha(color)

    constructor(r: Int, g: Int, b: Int) : this(Color.rgb(r, g, b))
    constructor(r: Int, g: Int, b: Int, a: Int) : this(Color.argb(a, r, g, b))

    companion object {
        const val INT_COLOR_BLACK = -0x1000000

        val BLACK: CssColor = CssColor()
        var CURRENT_COLOR: CssColor = CssColor()

        fun fromString(string: String): CssColor {
            ColorKeywords[string]?.let { return CssColor(it) }
            return CssColor(Color.parseColor(string))
        }
    }
}

fun String.toCssColor(): CssColor {
    try {
        return CssColor.fromString(this)
    } catch (e : Exception) {
        throw SvgParseException("Could not parse $this to CssColor")
    }
}

