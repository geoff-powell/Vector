package com.greenmist.vector.lib.keyword

/**
 * Created by geoff.powell on 11/27/17.
 */
enum class SvgTagKeywords {
    SVG,
    A,
    CIRCLE,
    CLIPPATH,
    DEFS,
    DESC,
    ELLIPSE,
    G,
    IMAGE,
    LINE,
    LINEARGRADIENT,
    MARKER,
    MASK,
    PATH,
    PATTERN,
    POLYGON,
    POLYLINE,
    RADIALGRADIENT,
    RECT,
    SOLIDCOLOR,
    STOP,
    STYLE,
    SWITCH,
    SYMBOL,
    TEXT,
    TEXTPATH,
    TITLE,
    TREF,
    TSPAN,
    USE,
    VIEW,
    UNSUPPORTED;

    companion object {

        fun fromString(str: String?): SvgTagKeywords {
            return values().firstOrNull {
                it.name.equals(str, true)
            } ?: UNSUPPORTED
        }
    }
}