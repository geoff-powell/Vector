package com.greenmist.vector.svgkit.svg.parser

import com.greenmist.vector.svgkit.svg.ColorSvg

/**
 * Created by geoffpowell on 11/18/17.
 */
class TextProperties() {

    var x: Float? = null
    var y: Float? = null
    var dx: Float? = 0f
    var dy: Float? = 0f
    var fillColor: ColorSvg? = null
    var strokeColor: ColorSvg? = null
    var strokeWidth: Float? = null

    internal var fontName: String? = null
    internal var fontSize: Float? = null
    internal var fontWeight: String? = null
    internal var fontStyle: String? = null
    internal var textAnchor: String? = null
    internal var extUrl: String? = null

    constructor(properties: Properties, paintData: PaintData?) : this() {
        x = properties.getFloat("x")
        y = properties.getFloat("y")
        dx = properties.getFloat("dx", 0f)
        dy = properties.getFloat("dy", 0f)
        fillColor = properties.getColor("fill", paintData)
        strokeColor = properties.getColor("stroke", paintData)
        strokeWidth = properties.getFloat("stroke-width")
        if (strokeWidth == null && strokeColor != null) {
            strokeWidth = 1f
        }

        val strokeWidth = strokeWidth
        if (strokeColor == null && strokeWidth != null && strokeWidth > 0) {
            if (fillColor != null) {
                strokeColor = fillColor
            } else {
                strokeColor?.value = android.graphics.Color.BLACK
            }
        }

        fontName = properties.getString("font-family")
        fontSize = properties.getFloat("font-size", paintData?.fontSize)
        fontWeight = properties.getString("font-weight")
        fontStyle = properties.getString("font-style")
        textAnchor = properties.getString("text-anchor")
        extUrl = properties.getString("src")
    }

    fun cloneStyle(): TextProperties {
        val t = TextProperties()
        t.fillColor = fillColor
        t.strokeColor = strokeColor
        t.strokeWidth = strokeWidth

        t.fontName = fontName
        t.fontSize = fontSize
        t.fontWeight = fontWeight
        t.fontStyle = fontStyle
        t.textAnchor = textAnchor
        return t
    }
}