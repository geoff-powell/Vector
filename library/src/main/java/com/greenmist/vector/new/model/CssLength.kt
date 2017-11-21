package com.greenmist.vector.new.model

/**
 * Created by geoffpowell on 11/19/17.
 */
// Units based on values here https://developer.mozilla.org/en-US/docs/Web/CSS/length
class CssLength(internal val value: Float = 0f, internal val unit: Unit = Unit.PX) {

    private fun calculatePxValue(fontSize: Float = Unit.PX_DEFAULT_FONT_SIZE, xHeight: Float = Unit.PX_DEFAULT_X_HEIGHT) : Float {
        val factor = when(unit) {
            Unit.EM -> fontSize
            Unit.EX -> xHeight
            else -> unit.pxScaleFactor
        }

        return value * factor
    }

    fun getValue() : Float = value

    fun getPxValue(dpi: Float, fontSize: Float = Unit.PX_DEFAULT_FONT_SIZE, xHeight: Float = Unit.PX_DEFAULT_X_HEIGHT) {
        when(unit) {
            Unit.PX -> value
            else -> calculatePxValue(fontSize, xHeight)
        }
    }

    fun getPxValueX(dpi: Float, fontSize: Float = Unit.PX_DEFAULT_FONT_SIZE, xHeight: Float = Unit.PX_DEFAULT_X_HEIGHT) {
        when(unit) {
            Unit.PX -> value
            else -> calculatePxValue(fontSize, xHeight)
        }
    }
}