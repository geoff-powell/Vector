package com.greenmist.vector.new

import com.greenmist.vector.logger.SvgLogger
import com.greenmist.vector.new.keyword.AttributeKeywords
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

/**
 * Created by geoffpowell on 11/19/17.
 */
class SvgContentHandler : DefaultHandler() {

    override fun startDocument() {
        SvgLogger.d("Start Document")
    }

    override fun endDocument() {
        SvgLogger.d("End Document")
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        var attrStr = "{"
        attributes?.let {
            var sep = ""
            for (i in 0 until attributes.length) {
                attrStr += "$sep\n    uri: ${attributes.getURI(i)} localName: ${attributes.getLocalName(i)}, qname: ${attributes.getQName(i)} value: ${attributes.getValue(i)}"
                sep = ","
            }
        }
        attrStr += "\n}"
        SvgLogger.d("Start -> uri: $uri, localName: $localName, qName: $qName, attributes: $attrStr")


        attributes?.let {
            (0 until attributes.length)
                    .map {
                        AttributeKeywords.fromString(attributes.getLocalName(it))
                    }.forEach {
                        SvgLogger.e(it.name)
                    }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        SvgLogger.d("End -> uri: $uri, localName: $localName, qName: $qName")
    }
}