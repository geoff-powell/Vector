package com.greenmist.vector.svg.model

enum class AlignmentType {
    NONE,
    XMIN,
    XMID,
    XMAX,
    YMIN,
    YMID,
    YMAX;

    companion object {
        fun fromString(string: String): AlignmentType? {
            return values().firstOrNull {
                it.name.equals(string, true)
            }
        }
    }
}

fun String.toAlignmentType() = AlignmentType.fromString(this)