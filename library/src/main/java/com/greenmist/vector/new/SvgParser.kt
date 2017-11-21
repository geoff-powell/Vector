package com.greenmist.vector.new

import org.xml.sax.InputSource
import java.io.InputStream
import javax.xml.parsers.SAXParserFactory

/**
 * Created by geoffpowell on 11/19/17.
 */
class SvgParser {

    fun parse(inputStream: InputStream) : Svg {
        val saxParserFactory = SAXParserFactory.newInstance()
        saxParserFactory.setFeature("http://xml.org/sax/features/validation", false)

        val saxParser = saxParserFactory.newSAXParser()
        val xmlReader = saxParser.xmlReader
        val svgContentHandler = SvgContentHandler()

        xmlReader.contentHandler = svgContentHandler
        xmlReader.parse(InputSource(inputStream))

        return Svg()
    }
}