package com.greenmist.vector.svg.css

import android.graphics.Path

enum class CssFillRule(val fillType: Path.FillType) {
    NONZERO(Path.FillType.WINDING),
    EVENODD(Path.FillType.EVEN_ODD)
}

fun String.toCssFillRule() : CssFillRule {
    return CssFillRule.valueOf(this.toUpperCase())
}