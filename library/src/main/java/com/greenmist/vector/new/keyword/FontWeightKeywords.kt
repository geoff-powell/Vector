package com.greenmist.vector.new.keyword

/**
 * Created by geoffpowell on 11/19/17.
 */
// Values from here https://www.w3.org/TR/css-fonts-3/#propdef-font-weight
object FontWeightKeywords {

    val FONT_WEIGHT_NORMAL = 400
    val FONT_WEIGHT_BOLD = 700
    val FONT_WEIGHT_LIGHTER = -1
    val FONT_WEIGHT_BOLDER = +1

    private val fontWeightKeywords = HashMap<String, Int>()

    init {
        fontWeightKeywords.put("normal", FONT_WEIGHT_NORMAL)
        fontWeightKeywords.put("bold", FONT_WEIGHT_BOLD)
        fontWeightKeywords.put("bolder", FONT_WEIGHT_BOLDER)
        fontWeightKeywords.put("lighter", FONT_WEIGHT_LIGHTER)
        fontWeightKeywords.put("100", 100)
        fontWeightKeywords.put("200", 200)
        fontWeightKeywords.put("300", 300)
        fontWeightKeywords.put("400", 400)
        fontWeightKeywords.put("500", 500)
        fontWeightKeywords.put("600", 600)
        fontWeightKeywords.put("700", 700)
        fontWeightKeywords.put("800", 800)
        fontWeightKeywords.put("900", 900)
    }
}