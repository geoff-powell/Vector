package com.greenmist.vector.svgkit.svg

import android.graphics.Color

/**
 * Created by geoffpowell on 11/18/17.
 */
class ColorSvg(var value: Int = 0) {

    var isNone = false

    constructor(r: Int, g: Int, b: Int) : this(Color.argb(255, r, g, b))

    constructor(value: Int, withAlpha: Boolean) : this() {
        val alpha = if (withAlpha) {
            Color.alpha(value)
        } else {
            255
        }

        this.value = Color.argb(alpha, Color.red(value), Color.green(value), Color.blue(value))
    }

    fun getRgb(): Int = value

    fun getAlpha(): Int = Color.alpha(value)

    companion object {

        fun createNoneColor(): ColorSvg {
            val colorSVG = ColorSvg()
            colorSVG.isNone = true
            return colorSVG
        }
    }
}