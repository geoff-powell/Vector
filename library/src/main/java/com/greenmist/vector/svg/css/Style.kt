package com.greenmist.vector.lib.svg.css

import android.graphics.Paint
import com.greenmist.vector.extensions.clamp
import com.greenmist.vector.lib.keyword.FontWeight
import com.greenmist.vector.lib.keyword.toFontWeight
import com.greenmist.vector.lib.model.CssColor
import com.greenmist.vector.lib.model.Length
import com.greenmist.vector.lib.model.Unit
import com.greenmist.vector.lib.model.toLength
import com.greenmist.vector.lib.svg.Properties
import com.greenmist.vector.svg.css.*
import com.greenmist.vector.svg.model.FontStyle
import com.greenmist.vector.svg.model.toFontStyle

/**
 * Created by geoff.powell on 11/27/17.
 */
class Style() : Cloneable {

    constructor(properties: Properties) : this() {
        fill = properties["fill"]?.toCssPaint()
        fillRule = properties["fill-rule"]?.toCssFillRule()
        fillOpacity = properties["fill-opacity"]?.toFloat()?.clamp(0f, 1f)

        stroke = properties["stroke"]?.toCssPaint()
        strokeOpacity = properties["stroke-opacity"]?.toFloat()?.clamp(0f, 1f)
        strokeWidth = properties["stroke-width"]?.toLength()
        strokeLineCap = properties["stroke-linecap"]?.toCap()
        strokeLineJoin = properties["stroke-linejoin"]?.toJoin()

        opacity = properties["opacity"]?.toFloat()?.clamp(0f, 1f)

        display = properties["display"]?.toCssDisplay()
        visibility = properties["visibility"]?.toCssVisibility()

        fontSize = properties["font-size"]?.toLength()
        fontName = properties["font-family"]
        fontStyle = properties["font-style"]?.toFontStyle()
        fontWeight = properties["font-weight"]?.toFontWeight()
    }

    internal var fill: CssPaint? = null
    internal var fillRule: CssFillRule? = null
    internal var fillOpacity: Float? = null

    internal var stroke: CssPaint? = null
    internal var strokeOpacity: Float? = null
    internal var strokeWidth: Length? = null
    internal var strokeLineCap: Paint.Cap? = null
    internal var strokeLineJoin: Paint.Join? = null
    internal var strokeMiterLimit: Float? = null
    internal var strokeDashArray: Array<Length>? = null
    internal var strokeDashOffset: Length? = null

    internal var opacity: Float? = null // master opacity of both stroke and fill
    internal var color: String? = null

    internal var visibility: CssVisibility? = null
    internal var display: CssDisplay? = null

    internal var fontSize: Length? = null
    internal var fontName: String? = null
    internal var fontWeight: FontWeight? = null
    internal var fontStyle: FontStyle? = null

    // parent/inherited values
//    var gr: Gradient? = null
//
//    var textAnchor: String? = null
//
//    var filter: Filter? = null

    fun updateStyle(style: Style) {
        this.fill = style.fill ?: this.fill
        this.fillRule = style.fillRule ?: this.fillRule
        this.fillOpacity = style.fillOpacity ?: this.fillOpacity

        this.stroke = style.stroke ?: this.stroke
        this.strokeOpacity = style.strokeOpacity ?: this.strokeOpacity
        this.strokeWidth = style.strokeWidth ?: this.strokeWidth
        this.strokeLineCap = style.strokeLineCap ?: this.strokeLineCap
        this.strokeLineJoin = style.strokeLineJoin ?: this.strokeLineJoin
        this.strokeMiterLimit = style.strokeMiterLimit ?: this.strokeMiterLimit
        this.strokeDashArray = style.strokeDashArray ?: this.strokeDashArray
        this.strokeDashOffset = style.strokeDashOffset ?: this.strokeDashOffset

        this.opacity = style.opacity ?: this.opacity
        this.color = style.color ?: this.color

        this.visibility = style.visibility ?: this.visibility

        this.fontName = style.fontName ?: this.fontName
        this.fontSize = style.fontSize ?: this.fontSize
        this.fontWeight = style.fontWeight ?: this.fontWeight
        this.fontStyle = style.fontStyle ?: this.fontStyle
    }

    override public fun clone(): Any {
        return super.clone()
    }

    companion object {
        val BASE : Style by lazy {
            val style = Style()
            style.fill = CssPaint(CssColor.BLACK)
            style.fillRule = CssFillRule.NONZERO
            style.fillOpacity = 1f
            style.strokeWidth = Length(1f)
            style.strokeOpacity = 1f
            style.strokeLineCap = Paint.Cap.BUTT
            style.strokeLineJoin = Paint.Join.MITER

            style.opacity = 1f

            style.visibility = CssVisibility.VISIBLE
            style.display = CssDisplay.INLINE

            style.fontSize = Length(Unit.PX_DEFAULT_FONT_SIZE)
            style.fontWeight = FontWeight.NORMAL

            return@lazy style
        }
    }
}

fun String.toCap() : Paint.Cap = Paint.Cap.valueOf(this.toUpperCase())
fun String.toJoin() : Paint.Join = Paint.Join.valueOf(this.toUpperCase())