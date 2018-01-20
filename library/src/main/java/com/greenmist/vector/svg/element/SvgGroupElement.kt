package com.greenmist.vector.lib.svg.element

import com.greenmist.vector.svg.element.TransformElement
import com.greenmist.vector.svg.model.toTransform
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class SvgGroupElement(attributes: Attributes?) : SvgElement(attributes), TransformElement {

    override var transform = properties["transform"]?.toTransform()
}