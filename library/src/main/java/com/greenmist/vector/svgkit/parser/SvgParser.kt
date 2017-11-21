package com.greenmist.vector.svgkit.parser

/**
 * Created by geoffpowell on 11/17/17.
 */
/*
class SvgParser {

    fun parse(inputStream: InputStream) : Svg {
        val saxParserFactory = SAXParserFactory.newInstance()
        saxParserFactory.setFeature("http://xml.org/sax/features/validation", false)

        val saxParser = saxParserFactory.newSAXParser()
        val xmlReader = saxParser.xmlReader
        val svgContentHandler = SvgContentHandler()

        xmlReader.contentHandler = svgContentHandler
        xmlReader.parse(InputSource(inputStream))

        return svgContentHandler.getSvg()
    }
}*/