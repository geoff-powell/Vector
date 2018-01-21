package com.greenmist.vector.svg.element

import com.greenmist.vector.lib.model.ViewBox
import com.greenmist.vector.lib.model.toViewBox
import com.greenmist.vector.lib.svg.element.SvgElement
import org.xml.sax.Attributes

class SvgSymbolElement(attributes: Attributes?) : SvgElement(attributes), ViewBoxElement {

    override var viewBox: ViewBox? = properties["viewBox"]?.toViewBox()
}