package com.greenmist.vector.svg.element

import com.greenmist.vector.lib.svg.element.SvgElement
import org.xml.sax.Attributes

class SvgTitleElement(attributes: Attributes?) : SvgElement(attributes) {

    var title: String = ""

    override fun parseContent(content: String) {
        title = content
    }
}