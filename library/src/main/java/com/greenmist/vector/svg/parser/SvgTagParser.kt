package com.greenmist.vector.lib.svg.parser

import com.greenmist.vector.lib.keyword.SvgTagKeywords
import com.greenmist.vector.lib.svg.element.*
import com.greenmist.vector.logger.SvgLogger
import com.greenmist.vector.svg.element.SvgDescriptionElement
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class SvgTagParser : ContentParser {

    override val namespace: String = "http://www.w3.org/2000/svg"

    override fun parseTag(localName: String?, attributes: Attributes?): SvgElement? {
        val tag = SvgTagKeywords.fromString(localName)

        return when(tag) {
            SvgTagKeywords.SVG -> RootSvgElement(attributes)
//            SvgTagKeywords.DESC -> SvgDescriptionElement(attributes)
            SvgTagKeywords.CIRCLE -> SvgCircleElement(attributes)
            SvgTagKeywords.RECT -> SvgRectElement(attributes)
            SvgTagKeywords.LINE -> SvgLineElement(attributes)
            SvgTagKeywords.PATH -> SvgPathElement(attributes)
            SvgTagKeywords.TEXT -> SvgTextElement(attributes)
            SvgTagKeywords.TSPAN -> SvgTextSpanElement(attributes)
            SvgTagKeywords.G -> SvgGroupElement(attributes)
            else -> {
                SvgLogger.d("Support for $localName element has not been implemented")
                return null
            }
        }
    }

    override fun parseContent(element: SvgElement?, localName: String?, content: String) {
        element?.parseContent(content)
    }
}

