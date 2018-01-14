package com.greenmist.vector.lib.svg

import com.greenmist.vector.extensions.isNotBlank
import com.greenmist.vector.lib.svg.element.RootSvgElement
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.lib.svg.parser.ContentParser
import com.greenmist.vector.logger.SvgLogger
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class SvgDocumentParser(private val keepUnsupportedTags: Boolean, private val dpi: Int, val contentParsers: List<ContentParser>) {

    val elementIdMap: HashMap<String, SvgElement> = HashMap()
    var rootElement: RootSvgElement? = null
    private var currentElement: SvgElement? = null
    private var ignoreElementDepth = 0

    fun pushElement(namespace: String?, localName: String?, attrs: Attributes?) {
        val contentParser = contentParsers.firstOrNull { it.namespace == namespace }
        val element = contentParser?.parseTag(localName, attrs)
                ?:  if (keepUnsupportedTags) SvgElement(attrs) else null

        if (ignoreElementDepth == 0 && element != null) {
            if (rootElement == null) {
                rootElement = element as RootSvgElement?
            }

            currentElement?.addChild(element)

            element.id?.isNotBlank { elementIdMap.put(it, element) }
            currentElement = element
        } else {
            SvgLogger.d("Ignoring tag $localName in $namespace.")
            ignoreElementDepth++
        }
    }

    fun popElement(namespace: String?, localName: String?, innerText: String) {
        if (ignoreElementDepth > 0) {
            ignoreElementDepth--
        } else {
            val contentParser = contentParsers.firstOrNull { it.namespace == namespace }
            contentParser?.parseInnerContent(currentElement, localName, innerText)

            currentElement?.let {
                currentElement = it.parent
            }
        }
    }

    internal fun getSvg() : Svg {
        return Svg(rootElement!!, elementIdMap, dpi)
    }
}