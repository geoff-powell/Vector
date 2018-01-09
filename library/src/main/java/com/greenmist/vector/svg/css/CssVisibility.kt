package com.greenmist.vector.svg.css

import com.greenmist.vector.svg.exception.SvgParseException

enum class CssVisibility {
    VISIBLE,
    HIDDEN,
    COLLAPSE;

    companion object {
        fun fromName(name: String, ignoreCase: Boolean = false) : CssVisibility?{
            return values().firstOrNull { it.name.equals(name, ignoreCase) }
        }
    }
}

fun String.toCssVisibility(): CssVisibility {
    return CssVisibility.fromName(this, true) ?: throw SvgParseException("Could not parse $this to CssVisibility")
}