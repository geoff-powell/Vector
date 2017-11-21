package com.greenmist.vector.svgkit.svg.parser

import com.greenmist.vector.svgkit.css.rules.InlineRule
import com.greenmist.vector.logger.SvgLogger
import com.greenmist.vector.svgkit.svg.ColorSvg
import com.greenmist.vector.svgkit.svg.properties.Colors
import com.greenmist.vector.svgkit.svg.properties.Numbers
import org.xml.sax.Attributes
import java.util.*

/**
 * Created by geoffpowell on 11/18/17.
 */
class Properties {

    var attributes: Attributes? = null
    var style: InlineRule? = null

    constructor(atts: Attributes?, style: InlineRule) {
        this.attributes = atts
        this.style = style
    }

    fun getProperty(name: String): String? {
        var value: String? = null
        style?.let { style ->
            val cssValue = style.get(name)
            if (cssValue != null) {
                value = cssValue.toString()
            }
        }

        if (value == null) {
            value = getStringAttr(name)
        }
        return value
    }

    fun getString(name: String): String? = getProperty(name)

    fun getHex(name: String): Int? {
        val v = getProperty(name)
        return if (v == null || v.startsWith("#") == false) {
            null
        } else {
            try {
                Integer.parseInt(v.substring(1), 16)
            } catch (e: NumberFormatException) {
                // TODO - Parse word based colors
                SvgLogger.i("Number format exception when parsing color: ${e.printStackTrace()}")
                null
            }
        }
    }

    private fun calculateOpacity(parentOpacity: Float, globalOpacity: Float?) : Float {
        val opValue: Float = globalOpacity ?: 1f
        return parentOpacity * opValue
    }

    fun getOpacityColorForGradient(name: String, paintData: PaintData?): ColorSvg {
        var prefix = ""
        var parentOpacity: Float? = null
        if (name.startsWith("fill")) {
            prefix = "fill-"
            parentOpacity = paintData?.fillOpacity
        }
        if (name.startsWith("stroke")) {
            prefix = "stroke-"
            parentOpacity = paintData?.strokeOpacity
        }
        if (name.startsWith("stop")) {
            prefix = "stop-"
        }

        var globalOpacity = getFloat("opacity")
        if (globalOpacity == null && paintData != null) {
            globalOpacity = paintData.opacity
        }

        var opacity = getFloat(prefix + "opacity")
        opacity = if (opacity == null) {
            if (parentOpacity == null) {
                globalOpacity
            } else {
                calculateOpacity(parentOpacity, globalOpacity)
            }
        } else {
            calculateOpacity(opacity, globalOpacity)
        }

        var alphaInt = 255//By default
        if (opacity != null) {
            alphaInt = Math.round(255 * opacity).toInt()
        }
        val alphaModifier = alphaInt shl 24

        //TODO Remove
        //val v = getProperty(name)

        return ColorSvg(alphaModifier, true)
    }


    fun getColor(name: String, paintData: PaintData?): ColorSvg? {

        var prefix = ""
        var parentOpacity: Float? = null
        var parentColor: ColorSvg? = null
        if (name.startsWith("fill")) {
            prefix = "fill-"
            parentOpacity = paintData?.fillOpacity
            parentColor = paintData?.fillColor
        }
        if (name.startsWith("stroke")) {
            prefix = "stroke-"
            parentOpacity = paintData?.strokeOpacity
            parentColor = paintData?.strokeColor
        }
        if (name.startsWith("stop")) {
            prefix = "stop-"
        }

        var globalOpacity = getFloat("opacity")
        if (globalOpacity == null && paintData != null) {
            globalOpacity = paintData.opacity
        }

        var opacity = getFloat(prefix + "opacity")
        opacity = if (opacity == null) {
            if (parentOpacity == null) {
                globalOpacity
            } else {
                calculateOpacity(parentOpacity, globalOpacity)
            }
        } else {
            calculateOpacity(opacity, globalOpacity)
        }

        var alphaModifier = 0
        if (opacity != null) {
            val alphaInt = Math.round(255 * opacity).toInt()
            alphaModifier = alphaInt shl 24
        }

        var v: String? = getProperty(name)

        if ("none".equals(v, true)) {
            return ColorSvg.createNoneColor()
        }

        if (v == null || !v.startsWith("#") == false) {
            if (opacity != null) {
                val col: ColorSvg? = if (v == null) {
                    parentColor
                } else {
                    Properties.getNamedColor(v)
                }
                return if (col == null) {
                    if (alphaModifier != 0 && name.startsWith("fill")) {
                        ColorSvg(alphaModifier, true)//situation when we have opacity without color
                    } else {
                        null
                    }
                } else if (!col.isNone) {
                    val rgb = col.getRgb() and 0xFFFFFF
                    ColorSvg(rgb or alphaModifier, true)
                } else {
                    col//returns "none" color
                }
            } else {
                return if (v == null) {
                    parentColor
                } else {
                    Properties.getNamedColor(v)
                }
            }
        } else {
            try {
                v = v.substring(1).trim { it <= ' ' }
                if (v.length == 3) {
                    val r = v[0]
                    val g = v[1]
                    val b = v[2]
                    v = "$r$r$g$g$b$b"
                }
                val i = Integer.parseInt(v, 16)
                return if (opacity != null) {
                    ColorSvg(i or alphaModifier, true)
                } else {
                    if (v.length == 6) {
                        ColorSvg(i, false)
                    } else {
                        ColorSvg(i)
                    }
                }
            } catch (nfe: NumberFormatException) {
                return Properties.getNamedColor(v)
            }
        }
    }

    fun getFloat(name: String, defaultValue: Float?): Float? {
        var v: String? = getProperty(name)
        if (v == null) {
            return defaultValue
        } else {
            var factor = 1f
            if (v.endsWith("in")) {
                factor = 96f
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("px")) {
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("pt")) {
                factor = 1.3f
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("cm")) {
                //factor = 37.795f;
                factor = 35.43307f
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("mm")) {
                //	factor = 370.795f;
                factor = 3.543307f//from spec
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("em")) {
                factor = 16f // XXX
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("%")) {
                factor = .01f
                v = v.substring(0, v.length - 1)
            }
            v = v.replace(" ", "")// for prevent crush when float like "45464 54654 5464"
            val ff = v.toFloat()
            return ff * factor
        }
    }

    fun getFloat(name: String): Float? = getFloat(name, null)

    /* Attributes only, no style */

    fun getNumberParseAttr(name: String): Numbers? {
        val num = getProperty(name)
        return if (num != null) {
            Numbers.parseNumbers(num)
        } else {
            null
        }
    }

    fun getStringAttr(name: String): String? = getStringAttr(name, null)

    fun getStringAttr(name: String, defaultValue: String?): String? {
        return attributes?.let { attributes ->
            (0 until attributes.length).firstOrNull { index ->
                attributes.getLocalName(index) == name
            }?.let { firstIndex ->
               attributes.getValue(firstIndex)
            }
        } ?: defaultValue
    }

    fun getFloatAttr(name: String): Float? = getFloatAttr(name, null)

    fun getFloatAttr(name: String, defaultValue: Float?): Float? {
        if (attributes == null) {
            return defaultValue
        }
        var v: String? = getStringAttr(name)
        if (v == null) {
            return defaultValue
        } else {
            var factor = 1f
            if (v.endsWith("in")) {
                factor = 96f
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("px")) {
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("pt")) {
                factor = 1.3f
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("cm")) {
                //factor = 37.795f;
                factor = 35.43307f
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("mm")) {
                //	factor = 370.795f;
                factor = 3.543307f//from spec
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("em")) {
                factor = 16f // XXX
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("%")) {
                factor = .01f
                v = v.substring(0, v.length - 1)
            }

            val ind = v.indexOf(' ')
            if (ind > 0) {
                v = v.substring(0, ind)
            }
            var ff: Float
            try {
                ff = java.lang.Float.parseFloat(v)
            } catch (e: NumberFormatException) {
                var iz1 = v.indexOf(',')
                var iz2 = v.indexOf(' ')
                var iz = -1
                if (iz1 >= 0) {
                    iz = getIndex(iz2, iz1)
                    iz1 = -1
                    iz2 = -1
                }
                if (iz2 >= 0) {
                    iz = getIndex(iz2, iz1)
                }
                if (iz > 0) {
                    ff = v.substring(0, iz).toFloat()
                } else {
                    throw e
                }
            }

            return ff * factor
        }
    }

    fun getScaledFloatAttr(name: String, baseValue: Float?): Float? {
        if (attributes == null) {
            return baseValue
        }

        var v: String? = getStringAttr(name)
        if (v == null) {
            return baseValue
        } else {
            var factor = 1f
            if (v.endsWith("in")) {
                factor = 96f
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("px")) {
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("pt")) {
                factor = 1.25f
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("cm")) {
                //factor = 37.795f;
                factor = 35.43307f
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("mm")) {
                //	factor = 370.795f;
                factor = 3.543307f//from spec
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("em")) {
                factor = 16f // XXX
                v = v.substring(0, v.length - 2)
            }
            if (v.endsWith("%")) {
                v = v.substring(0, v.length - 1)
                return java.lang.Float.parseFloat(v) * .01f * baseValue!!
            }
            var ff: Float
            try {
                ff = java.lang.Float.parseFloat(v)
            } catch (e: NumberFormatException) {
                var iz1 = v.indexOf(',')
                var iz2 = v.indexOf(' ')
                var iz = -1
                if (iz1 >= 0) {
                    iz = getIndex(iz1, iz2)
                    iz1 = -1
                    iz2 = -1
                }
                if (iz2 >= 0) {
                    iz = getIndex(iz2, iz1)
                }
                if (iz > 0) {
                    ff = v.substring(0, iz).toFloat()
                } else {
                    throw e
                }
            }

            return ff * factor
        }
    }

    private fun getIndex(iz2: Int, iz1: Int): Int {
        return if (iz2 > 0) {
            Math.min(iz1, iz2)
        } else {
            iz1
        }
    }

    fun getHexAttr(name: String): Int? {
        val v = getStringAttr(name)
        return if (v == null) {
            null
        } else {
            try {
                v.substring(1).toInt(16)
            } catch (nfe: NumberFormatException) {
                null
            }
        }
    }

    override fun toString(): String {
        var ret = ""
        attributes?.let { attributes ->
            for (i in 0 until attributes.length) {
                ret += attributes.getQName(i) + "=" + attributes.getValue(i)
                if (i < attributes.length - 1) {
                    ret += ", "
                }
            }
        }
        return ret
    }

    companion object {

        fun getNamedColor(colorName: String?): ColorSvg? {
            if (colorName == null) {
                return null
            }

            if (colorName.indexOf("rgb") >= 0) {
                val i1 = colorName.indexOf("(")
                val i2 = colorName.indexOf(")")
                if (i2 > i1) {
                    val col = colorName.substring(i1 + 1, i2)
                    if (col.isNotEmpty()) {
                        val st = StringTokenizer(col, ",")
                        if (st.countTokens() == 3) {
                            val r = Integer.parseInt(st.nextToken().trim { it <= ' ' })
                            val g = Integer.parseInt(st.nextToken().trim { it <= ' ' })
                            val b = Integer.parseInt(st.nextToken().trim { it <= ' ' })
                            return ColorSvg(r, g, b)
                        }
                    }
                    return null
                }
                return null
            }

            return Colors.getColor(colorName)
        }
    }
}