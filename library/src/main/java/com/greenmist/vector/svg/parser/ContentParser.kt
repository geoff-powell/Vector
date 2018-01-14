package com.greenmist.vector.lib.svg.parser

import com.greenmist.vector.lib.svg.element.SvgElement
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
interface ContentParser {

    val namespace: String

    fun parseTag(localName: String?, attributes: Attributes?) : SvgElement?

    fun parseInnerContent(element: SvgElement?, localName: String?, innerContent: String)
}