package com.greenmist.vector.svg.element

import com.greenmist.vector.lib.svg.element.SvgElement
import org.xml.sax.Attributes

class SvgDefsElement(attributes: Attributes?) : SvgElement(attributes) {
    override val renderChildren = false
}