package com.greenmist.vector.lib.keyword

import com.greenmist.vector.lib.model.Length
import com.greenmist.vector.lib.model.Unit

/**
 * Created by geoffpowell on 11/19/17.
 */
//Size calculations from https://www.w3.org/TR/css-fonts-3
object FontSizeKeywords {

    private val fontSizeMap = HashMap<String, Length>()

    init {
        fontSizeMap.put("xx-small", Length(0.694f, Unit.PT))
        fontSizeMap.put("x-small", Length(0.833f, Unit.PT))
        fontSizeMap.put("small", Length(10.0f, Unit.PT))
        fontSizeMap.put("medium", Length(12.0f, Unit.PT))
        fontSizeMap.put("large", Length(14.4f, Unit.PT))
        fontSizeMap.put("x-large", Length(18f, Unit.PT))
        fontSizeMap.put("xx-large", Length(24f, Unit.PT))
        // Should take parent size into account https://www.w3.org/TR/css-fonts-3/#relative-size-value
        fontSizeMap.put("smaller", Length(83.33f, Unit.PERCENT))
        fontSizeMap.put("larger", Length(120f, Unit.PERCENT))
    }
}