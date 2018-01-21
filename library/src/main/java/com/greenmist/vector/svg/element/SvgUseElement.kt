package com.greenmist.vector.svg.element

import com.greenmist.vector.lib.model.ViewBox
import com.greenmist.vector.lib.model.Viewport
import com.greenmist.vector.lib.model.toLength
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.svg.model.PreserveAspectRatio
import com.greenmist.vector.svg.model.Transform
import com.greenmist.vector.svg.model.toTransform
import org.xml.sax.Attributes

class SvgUseElement(attributes: Attributes?) : SvgElement(attributes), TransformElement, ReferenceElement, BoxElement {
    override var x = properties["x"]?.toLength()
    override var y = properties["y"]?.toLength()
    override var width = properties["width"]?.toLength()
    override var height = properties["height"]?.toLength()

//    override var viewBox: ViewBox? = null
//    override var viewport: Viewport = Viewport(properties)
//    override var preserveAspectRatio: PreserveAspectRatio? = null

    override val transform: Transform? = properties["transform"]?.toTransform()
    override var link = properties["xlink", "href"]
}