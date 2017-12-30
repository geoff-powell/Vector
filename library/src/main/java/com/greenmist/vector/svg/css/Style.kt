package com.greenmist.vector.lib.svg.css

import com.greenmist.vector.lib.model.CssLength
import com.greenmist.vector.lib.model.toCssLength
import com.greenmist.vector.lib.svg.Properties

/**
 * Created by geoff.powell on 11/27/17.
 */
class Style {

    internal var fill: String? = null
    internal var fillOpacity: Float? = null

    internal var stroke: String? = null
    internal var strokeOpacity: Float? = null
    internal var strokeWidth: CssLength? = null
    internal var strokeLineCap: String? = null
    internal var strokeLineJoin: String? = null
    internal var strokeMiterLimit: Float? = null
    internal var strokeDashArray: Array<CssLength>? = null
    internal var strokeDashOffset: CssLength? = null

    internal var opacity: Float? = null // master opacity of both stroke and fill

    internal var color: String? = null

    internal var parentStyle: Style? = null

    // parent/inherited values
//    var fillColor: ColorSVG? = null
//    var fillOpacity: Float? = null
//    var gr: Gradient? = null
//
//    var strokeColor: ColorSVG? = null
//    var strokeCapStyle: Paint.Cap? = null
//    var strokeJoinStyle: Paint.Join? = null
//    var strokeWidth: Float? = null
//    var strokeOpacity: Float? = null
//    var dasharray: Numbers? = null
//    var dashOffset: Float? = null
//    var opacity: Float? = null
//
//    var fontName: String? = null
//    var fontSize: Float? = null
//    var fontWeight: String? = null
//    var fontStyle: String? = null
//    var textAnchor: String? = null
//
//    var filter: Filter? = null

    constructor() {

    }

    constructor(properties: Properties, parentStyle: Style? = null) {
        //TODO parse inlineStyle string

        this.parentStyle = parentStyle

        fill = properties["fill"]
        stroke = properties["stroke"]
        strokeWidth = properties["stroke-width"]?.toCssLength()
    }
}