package com.greenmist.vector.svg.css

import android.graphics.Path
import com.greenmist.vector.svg.exception.SvgParseException

enum class CssFillRule(val fillType: Path.FillType) {
    NONZERO(Path.FillType.WINDING),
    EVENODD(Path.FillType.EVEN_ODD);

    companion object {
        fun fromString(string: String) : CssFillRule? {
            return CssFillRule.values().firstOrNull {
                it.name.equals(string, true)
            }
        }
    }
}

fun String.toCssFillRule() : CssFillRule {
    return CssFillRule.fromString(this) ?: throw SvgParseException("Could not parse $this to CssFileRule")
}