package com.greenmist.vector.svgkit.svg.parser

import android.graphics.Paint
import com.greenmist.vector.svgkit.svg.ColorSvg
import com.greenmist.vector.svgkit.svg.properties.Numbers

/**
 * Created by geoffpowell on 11/18/17.
 */
class PaintData {
    var fillColor: ColorSvg? = null
    var fillOpacity: Float? = null
    var gr: Gradient? = null

    var strokeColor: ColorSvg? = null
    var strokeCapStyle: Paint.Cap? = null
    var strokeJoinStyle: Paint.Join? = null
    var strokeWidth: Float? = null
    var strokeOpacity: Float? = null
    var dasharray: Numbers? = null
    var dashOffset: Float? = null
    var opacity: Float? = null

    var fontName: String? = null
    var fontSize: Float? = null
    var fontWeight: String? = null
    var fontStyle: String? = null
    var textAnchor: String? = null

    var filter: Filter? = null
}