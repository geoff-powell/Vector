package com.greenmist.vector.svg.element

import android.view.View
import com.greenmist.vector.lib.model.ViewBox
import com.greenmist.vector.lib.model.Viewport
import com.greenmist.vector.lib.model.toLength
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.svg.model.PreserveAspectRatio
import com.greenmist.vector.svg.model.Transform
import com.greenmist.vector.svg.model.toTransform
import org.xml.sax.Attributes

class SvgUseElement(attributes: Attributes?) : SvgElement(attributes), TransformElement {
    val x = properties["x"]?.toLength()
    val y = properties["y"]?.toLength()
    val width = properties["width"]?.toLength()
    val height = properties["height"]?.toLength()

    override val transform: Transform? = properties["transform"]?.toTransform()
    val xLink = properties["xlink:href"]
}