package com.greenmist.vector.svg.model

import com.greenmist.vector.logger.SvgLogger

class Alignment(
    val xAlignment: AlignmentType = AlignmentType.NONE,
    val yAlignment: AlignmentType = AlignmentType.NONE
) {

    companion object {
        fun fromString(string: String) : Alignment {
            lateinit var xAlignment: AlignmentType
            lateinit var yAlignment: AlignmentType

            when {
                string.length >= 8 -> {
                    xAlignment = string.substring(0, 4).toAlignmentType() ?: AlignmentType.NONE
                    yAlignment = string.substring(4, 8).toAlignmentType() ?: AlignmentType.NONE
                }
                string.equals("none", true) -> {
                    xAlignment = AlignmentType.NONE
                    yAlignment = AlignmentType.NONE
                }
                else -> SvgLogger.w("Invalid alignment parameter $string, defaulting to NONE")
            }

            return Alignment(xAlignment, yAlignment)
        }
    }
}

fun String.toAlignment() = Alignment.fromString(this)