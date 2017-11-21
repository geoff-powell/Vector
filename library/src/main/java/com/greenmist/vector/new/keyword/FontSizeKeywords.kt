package com.greenmist.vector.new.keyword

import com.greenmist.vector.new.model.CssLength
import com.greenmist.vector.new.model.Unit

/**
 * Created by geoffpowell on 11/19/17.
 */
//Size calculations from https://www.w3.org/TR/css-fonts-3
object FontSizeKeywords {

    private val fontSizeMap = HashMap<String, CssLength>()

    init {
        fontSizeMap.put("xx-small", CssLength(0.694f, Unit.PT))
        fontSizeMap.put("x-small", CssLength(0.833f, Unit.PT))
        fontSizeMap.put("small", CssLength(10.0f, Unit.PT))
        fontSizeMap.put("medium", CssLength(12.0f, Unit.PT))
        fontSizeMap.put("large", CssLength(14.4f, Unit.PT))
        fontSizeMap.put("x-large", CssLength(18f, Unit.PT))
        fontSizeMap.put("xx-large", CssLength(24f, Unit.PT))
        // Should take parent size into account https://www.w3.org/TR/css-fonts-3/#relative-size-value
        fontSizeMap.put("smaller", CssLength(83.33f, Unit.PERCENT))
        fontSizeMap.put("larger", CssLength(120f, Unit.PERCENT))
    }
}