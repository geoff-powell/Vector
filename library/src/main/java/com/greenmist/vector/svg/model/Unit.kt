package com.greenmist.vector.lib.model

/**
 * Created by geoffpowell on 11/19/17.
 */
// Units based on values here https://developer.mozilla.org/en-US/docs/Web/CSS/length
enum class Unit(val pxScaleFactor: Float = 1f, var identifier: String = "") {
    PX(),
    EM(),
    EX(),
    IN(96f),
    CM(37.7953f),
    MM(3.77953f),
    PT(1.32815f),
    PC(16f),
    PERCENT (0.01f, "%");

    init {
        if (identifier.isBlank()) identifier = name.toLowerCase()
    }

    companion object {

        // Default font size found here https://developer.mozilla.org/en-US/docs/Learn/CSS/Introduction_to_CSS/Values_and_units
        val PX_DEFAULT_FONT_SIZE = 16f
        val PX_DEFAULT_X_HEIGHT = 0.5f

        fun fromString(identifier: String) : Unit? {
            return values().firstOrNull {
                identifier.endsWith(it.identifier, true)
            }
        }
    }
}
