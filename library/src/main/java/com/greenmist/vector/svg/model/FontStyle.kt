package com.greenmist.vector.svg.model

enum class FontStyle {
    NORMAL,
    ITALIC,
    OBLIQUE;

    companion object {
        fun fronString(string: String) : FontStyle {
            return values().firstOrNull { it.name.equals(string, true) }  ?: NORMAL
        }
    }
}

fun String.toFontStyle(): FontStyle = FontStyle.fronString(this)