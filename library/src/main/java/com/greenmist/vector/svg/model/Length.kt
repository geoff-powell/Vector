package com.greenmist.vector.lib.model

import com.greenmist.vector.logger.SvgLogger
import com.greenmist.vector.renderer.RenderState
import com.greenmist.vector.svg.exception.SvgParseException
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by geoffpowell on 11/19/17.
 */
// Units based on values here https://developer.mozilla.org/en-US/docs/Web/CSS/length
class Length(private val value: Float = 0f, internal val unit: Unit = Unit.PX) {

    constructor(length: Length) : this(length.getValue(), length.unit)

    private fun calculatePxValue(fontSize: Float = Unit.PX_DEFAULT_FONT_SIZE, xHeight: Float = Unit.PX_DEFAULT_X_HEIGHT) : Float {
        val factor = when(unit) {
            Unit.EM -> fontSize
            Unit.EX -> xHeight
            else -> unit.pxScaleFactor
        }

        return value * factor
    }

    fun getValue() : Float = value

    fun getPxValue(renderState: RenderState): Float {
        val fontSize = renderState.style.fontSize?.getPxValueY(renderState.dpi) ?: Unit.PX_DEFAULT_FONT_SIZE
        return getPxValue(renderState.dpi, renderState.viewBox, fontSize)
    }

    fun getPxValue(dpi: Number, viewPort: ViewBox? = null, fontSize: Float = Unit.PX_DEFAULT_FONT_SIZE, xHeight: Float = Unit.PX_DEFAULT_X_HEIGHT) : Float {
        return when(unit) {
            Unit.PX -> value
            Unit.PERCENT -> {
                val value = calculatePxValue(fontSize, xHeight)
                val width = viewPort?.width ?: 1f
                val height = viewPort?.height ?: 1f
                val percentMultiplier = sqrt(((value - width).pow(2) + (value - height).pow(2)).toDouble()) / sqrt(2.0)
                val pxValue = value * percentMultiplier.toFloat()

                SvgLogger.e("Viewport Width: $width Viewport height: $height  multiplier: $percentMultiplier Unit: $unit value: $value pxValue: $pxValue")

                return pxValue
            }
            else -> calculatePxValue(fontSize, xHeight)
        }
    }

    fun getPxValueY(renderState: RenderState): Float {
        val fontSize = renderState.style.fontSize?.getPxValueY(renderState.dpi) ?: Unit.PX_DEFAULT_FONT_SIZE
        return getPxValueY(renderState.dpi, renderState.viewBox, fontSize)
    }

    fun getPxValueY(dpi: Number, viewPort: ViewBox? = null, fontSize: Float = Unit.PX_DEFAULT_FONT_SIZE, xHeight: Float = Unit.PX_DEFAULT_X_HEIGHT) : Float {
        return when(unit) {
            Unit.PX -> value
            Unit.PERCENT -> {
                val pxValue = calculatePxValue(fontSize, xHeight) * (viewPort?.height ?: 1f)
                SvgLogger.e("Viewport height: ${viewPort?.height} Unit: $unit value: $value pxValue: $pxValue")

                pxValue
            }
            else -> calculatePxValue(fontSize, xHeight)
        }
    }

    fun getPxValueX(renderState: RenderState): Float {
        val fontSize = renderState.style.fontSize?.getPxValueY(renderState.dpi) ?: Unit.PX_DEFAULT_FONT_SIZE
        return getPxValueX(renderState.dpi, renderState.viewBox, fontSize)
    }

    fun getPxValueX(dpi: Number, viewPort: ViewBox? = null, fontSize: Float = Unit.PX_DEFAULT_FONT_SIZE, xHeight: Float = Unit.PX_DEFAULT_X_HEIGHT) : Float {
        return when(unit) {
            Unit.PX -> value
            Unit.PERCENT -> {
                val pxValue = calculatePxValue(fontSize, xHeight) * (viewPort?.width ?: 1f)
                SvgLogger.e("Viewport width: ${viewPort?.width} Unit: $unit value: $value pxValue: $pxValue")
                pxValue
            }
            else -> calculatePxValue(fontSize, xHeight)
        }
    }

    companion object {
        fun fromString(string: String) : Length {
            var unit = Unit.fromString(string)

            val value = if (unit != null && string.length > unit.identifier.length) {
                string.substring(0, string.length - unit.identifier.length).toFloatOrNull()
            } else {
                unit = Unit.PX
                string.toFloatOrNull()
            }

            value ?: throw SvgParseException("Could not parse $string to Length")

            return Length(value, unit)
        }
    }
}

fun String.toLength(): Length {
    return Length.fromString(this)
}