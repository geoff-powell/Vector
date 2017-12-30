package com.greenmist.vector.lib.svg.element

import com.greenmist.vector.lib.svg.Properties
import com.greenmist.vector.lib.svg.css.Style
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
open class SvgElement(attributes: Attributes? = null) {

    val properties = Properties(attributes)
    var id: String? = properties["id"]

    var parent: SvgElement? = null
    var children: MutableList<SvgElement> = ArrayList()

    var style: Style = Style(properties, parent?.style)

    fun addChild(element: SvgElement) {
        children.add(element)
        element.parent = this
    }
}
