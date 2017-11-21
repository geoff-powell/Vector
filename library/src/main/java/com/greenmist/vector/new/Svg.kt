package com.greenmist.vector.new

import java.io.ByteArrayInputStream
import java.io.InputStream

/**
 * Created by geoffpowell on 11/19/17.
 */
class Svg {

    companion object {

        private val svgParser = SvgParser()

        fun getFromString(svgString: String): Svg =
                svgParser.parse(ByteArrayInputStream(svgString.toByteArray()))

        fun getFromInputStream(inputStream: InputStream): Svg =
                svgParser.parse(inputStream)
    }
}