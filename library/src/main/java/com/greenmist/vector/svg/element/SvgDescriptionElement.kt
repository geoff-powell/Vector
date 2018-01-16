package com.greenmist.vector.svg.element

import com.greenmist.vector.lib.svg.element.SvgElement
import org.xml.sax.Attributes

class SvgDescriptionElement(attributes: Attributes?) : SvgElement(attributes) {

    var description: String = ""

    override fun parseContent(content: String) {
        description = content
    }
}