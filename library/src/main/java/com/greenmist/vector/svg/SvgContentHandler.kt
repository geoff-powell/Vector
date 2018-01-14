package com.greenmist.vector.lib.svg

import com.greenmist.vector.logger.SvgLogger
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

/**
 * Created by geoffpowell on 11/19/17.
 */
class SvgContentHandler(val parser: SvgDocumentParser) : DefaultHandler() {

    internal lateinit var svg: Svg
    private val innerBuffer = StringBuilder()

    override fun startDocument() {
        SvgLogger.d("Start Document")
    }

    override fun endDocument() {
        SvgLogger.d("End Document")
        svg = parser.getSvg()
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        val name = localName ?: qName ?: return

        parser.pushElement(uri, name, attributes)
        innerBuffer.setLength(0)
    }

    override fun characters(array: CharArray?, start: Int, length: Int) {
        array?.let {
            for (i in start until length) {
                innerBuffer.append(it[i])
            }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        val name: String = localName ?: qName ?: return

        parser.popElement(uri, name, innerBuffer.toString().trim())
    }
}