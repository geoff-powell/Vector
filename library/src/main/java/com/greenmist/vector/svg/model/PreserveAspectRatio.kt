package com.greenmist.vector.svg.model

import com.greenmist.vector.lib.svg.util.TextScanner
import com.greenmist.vector.svg.exception.SvgParseException

class PreserveAspectRatio(
        var defer: Boolean = false,
        var alignment: Alignment = Alignment(),
        var scale: MeetOrSlice = MeetOrSlice.MEET
) {

    companion object {

        fun fromString(string: String) : PreserveAspectRatio {
            val preserveAspectRatio = PreserveAspectRatio()
            val scanner = TextScanner(string)

            var currentString: String?

            while (scanner.hasNext()) {
                currentString = scanner.nextString()

                when {
                    currentString == null -> throw SvgParseException("Error parsing preserve aspect ratio: $currentString")
                    currentString == "defer" -> preserveAspectRatio.defer = true
                    currentString == "meet" -> preserveAspectRatio.scale = MeetOrSlice.MEET
                    currentString == "slice" -> preserveAspectRatio.scale = MeetOrSlice.SLICE
                    currentString == "none" || currentString.startsWith("x", true) -> {
                        preserveAspectRatio.alignment = currentString.toAlignment()
                    }
                    else -> throw SvgParseException("Error parsing preserve aspect ratio: $currentString")
                }

                scanner.skipWhitespace()
            }

            return preserveAspectRatio
        }
    }
}

fun String.toPreserveAspectRatio() = PreserveAspectRatio.fromString(this)