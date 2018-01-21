package com.greenmist.vector.lib.svg.element

import com.greenmist.vector.lib.svg.Properties
import com.greenmist.vector.lib.svg.css.Style
import com.greenmist.vector.svg.model.toTransform
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
open class SvgElement(attributes: Attributes? = null) {

    val properties = Properties(attributes)
    var id: String? = properties["id"]

    var parent: SvgElement? = null
    var children: MutableList<SvgElement> = ArrayList()

    val classType: String? = properties["class"]

    var style: Style = Style(properties)

    open val renderChildren = true

    open fun parseContent(content: String) {

    }

    fun addChild(element: SvgElement) {
        children.add(element)
        element.parent = this
    }
}
