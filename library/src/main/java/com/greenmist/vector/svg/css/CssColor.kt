package com.greenmist.vector.lib.model

import android.graphics.Color
import com.greenmist.vector.lib.keyword.ColorKeywords

/**
 * Created by geoff.powell on 11/20/17.
 */
class CssColor(color: Int = 0, withAlpha: Boolean = true) {

    val color = if (withAlpha) {
        color
    } else {
        Color.rgb(Color.red(color), Color.green(color), Color.blue(color))
    }

    val alpha = Color.alpha(color)

    constructor(r: Int, g: Int, b: Int) : this(Color.rgb(r, g, b))
    constructor(r: Int, g: Int, b: Int, a: Int) : this(Color.argb(a, r, g, b))
    constructor(hexColor: String) : this(Color.parseColor(hexColor))

    companion object {
        val BLACK: CssColor = CssColor()
        var CURRENT_COLOR: CssColor = CssColor()
    }
}

fun String.toCssColor(): CssColor {
    ColorKeywords[this]?.let { return CssColor(it) }
    return CssColor(this)
}

