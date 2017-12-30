package com.greenmist.vector.lib.svg

import com.greenmist.vector.extensions.isNotBlank
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.lib.svg.parser.ContentParser
import com.greenmist.vector.logger.SvgLogger
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class SvgDocumentParser(private val keepUnsupportedTags: Boolean, val contentParsers: List<ContentParser>) {

    val elementIdMap: HashMap<String, SvgElement> = HashMap()
    var rootElement: SvgElement? = null
    private var currentElement: SvgElement? = null
    private var ignoreElementDepth = 0

    fun pushElement(namespace: String?, localName: String?, attrs: Attributes?) {
        val contentParser = contentParsers.firstOrNull { it.namespace == namespace }
        val element = contentParser?.parse(localName, attrs)
                ?:  if (keepUnsupportedTags) SvgElement(attrs) else null

        if (ignoreElementDepth == 0 && element != null) {
            if (rootElement == null) rootElement = element

            currentElement?.addChild(element)

            element.id?.isNotBlank { elementIdMap.put(it, element) }
            currentElement = element
        } else {
            SvgLogger.d("Ignoring tag $localName in $namespace.")
            ignoreElementDepth++
        }
    }

    fun popElement() {
        if (ignoreElementDepth > 0) {
            ignoreElementDepth--
        } else {
            currentElement?.let {
                currentElement = it.parent
            }
        }
    }

    internal fun getSvg() : Svg {
        return Svg(rootElement!!, elementIdMap)
    }
}