package com.greenmist.vector.lib.keyword

/**
 * Created by geoffpowell on 11/19/17.
 */
// Values from here https://www.w3.org/TR/css-fonts-3/#propdef-font-weight
enum class FontWeight(
        identifier: String? = null,
        val weight: Int
) {

    NORMAL(weight = 400),
    BOLD(weight = 700),
    BOLDER(weight = 1),
    LIGHTER(weight = -1),
    ONE_HUNDRED("100", 100),
    TWO_HUNDRED("200", 200),
    THREE_HUNDRED("300", 300),
    FOUR_HUNDRED("400", 400),
    FIVE_HUNDRED("500", 500),
    SIX_HUNDRED("600", 600),
    SEVEN_HUNDRED("700", 700),
    EIGHT_HUNDRED("800", 800),
    NINE_HUNDRED("900", 900);

    val identifier = identifier ?: name

    companion object {
        fun fromString(string: String): FontWeight? {
            return values().firstOrNull { it.identifier.equals(string, true) }
        }
    }
}

fun String.toFontWeight(): FontWeight? = FontWeight.fromString(this)
