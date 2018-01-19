package com.greenmist.vector.lib.svg.element

import com.greenmist.vector.lib.model.ViewBox
import com.greenmist.vector.lib.model.Viewport
import com.greenmist.vector.lib.model.toViewBox
import com.greenmist.vector.svg.element.ViewportElement
import com.greenmist.vector.svg.model.PreserveAspectRatio
import com.greenmist.vector.svg.model.toPreserveAspectRatio
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class RootSvgElement(attributes: Attributes?) : SvgElement(attributes), ViewportElement {

    val version = properties["version"]
    override var viewport: Viewport = Viewport(properties)
    var width = viewport.width
    var height = viewport.height

    override var viewBox: ViewBox? = properties["viewBox"]?.toViewBox()
    override var preserveAspectRatio: PreserveAspectRatio? = properties["preserveAspectRatio"]?.toPreserveAspectRatio()
}