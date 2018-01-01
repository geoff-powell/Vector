package com.greenmist.vector.svg.css

import com.greenmist.vector.lib.model.CssColor
import com.greenmist.vector.lib.model.toCssColor
import com.greenmist.vector.svg.css.CssPaint.Companion.NONE

/*
https://www.w3.org/TR/SVG/painting.html#FillProperty
 */
class CssPaint(var cssColor: CssColor) {
    companion object {
        val NONE = CssPaint(CssColor(0, 0, 0, 0))
    }
}

fun String.toCssPaint(): CssPaint {
    if (this == "none") return NONE
    if (this == "currentColor") return CssPaint(CssColor.CURRENT_COLOR)
    return CssPaint(this.toCssColor())
}