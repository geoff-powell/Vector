package com.greenmist.vector.lib.model

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

    fun getPxValue(dpi: Float, fontSize: Float = Unit.PX_DEFAULT_FONT_SIZE, xHeight: Float = Unit.PX_DEFAULT_X_HEIGHT) : Float {
        return when(unit) {
            Unit.PX -> value
            else -> calculatePxValue(fontSize, xHeight)
        }
    }

    fun getPxValueX(dpi: Float, fontSize: Float = Unit.PX_DEFAULT_FONT_SIZE, xHeight: Float = Unit.PX_DEFAULT_X_HEIGHT) : Float {
        return when(unit) {
            Unit.PX -> value
            else -> calculatePxValue(fontSize, xHeight)
        }
    }
}

fun String.toCssLength(): CssLength {
    val unit = Unit.getUnit(this)

    val value = if (this.length > unit.identifier.length) {
        this.substring(0, this.length - unit.identifier.length).toFloat()
    } else {
        this.toFloat()
    }

    return CssLength(value, unit)
}