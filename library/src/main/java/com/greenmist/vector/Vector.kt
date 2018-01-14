package com.greenmist.vector

import android.content.Context
import com.greenmist.vector.lib.svg.Svg
import com.greenmist.vector.lib.svg.SvgContentHandler
import com.greenmist.vector.lib.svg.SvgDocumentParser
import com.greenmist.vector.lib.svg.parser.ContentParser
import com.greenmist.vector.lib.svg.parser.SvgTagParser
import org.xml.sax.InputSource
import java.io.ByteArrayInputStream
import java.io.InputStream
import javax.xml.parsers.SAXParserFactory

/**
 * Created by geoffpowell on 11/19/17.
 */
object Vector {

    fun with(context: Context) = SvgBuilder(context)

    class SvgBuilder internal constructor(val context: Context) {

        private var inputStream: InputStream? = null

        private var keepUnsupportedTags: Boolean = false
        private var dpi: Int = context.resources.displayMetrics.densityDpi
        // TODO Add functionality to pass custom content parsers
        private var customContentParsers: List<ContentParser> = listOf(SvgTagParser())

        private inline fun <T> T?.checkIfSet(errorFieldDesc: String, block: (T?) -> Unit) {
            if (this != null) throw IllegalArgumentException("$errorFieldDesc has already been set!")
            block.invoke(this)
        }

        fun from(svgStr: String): SvgBuilder {
            this.inputStream.checkIfSet("Svg input using from()") {
                this.inputStream = ByteArrayInputStream(svgStr.toByteArray())
            }
            return this
        }

        fun from(inputStream: InputStream): SvgBuilder {
            this.inputStream.checkIfSet("Svg input using from()") {
                this.inputStream = inputStream
            }
            this.inputStream = inputStream
            return this
        }

        fun withDpi(dpi: Int) {
            this.dpi = dpi
        }

        fun keepUsupportedTags(keepUnsupportedTags: Boolean) : SvgBuilder {
            this.keepUnsupportedTags = keepUnsupportedTags
            return this
        }

        fun load() : Svg {
            val saxParserFactory = SAXParserFactory.newInstance()
            saxParserFactory.setFeature("http://xml.org/sax/features/validation", false)

            val saxParser = saxParserFactory.newSAXParser()
            val xmlReader = saxParser.xmlReader

            val svgDocumentParser = SvgDocumentParser(keepUnsupportedTags, dpi, customContentParsers)
            val svgContentHandler = SvgContentHandler(svgDocumentParser)

            xmlReader.contentHandler = svgContentHandler
            xmlReader.parse(InputSource(inputStream))

            return svgContentHandler.svg
        }
    }
}