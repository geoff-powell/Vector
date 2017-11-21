package com.greenmist.vector.svgkit.css.parser

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.PrintWriter

/**
 * Created by geoffpowell on 11/18/17.
 */
class CssUrl(val url: String) : CssValue() {

    override fun serialize(outWriter: PrintWriter) {
        outWriter.print("url(")
        outWriter.print(url)
        outWriter.print(")")
    }

    fun getContentType(): String = "application/octet-stream"

    @Throws(IOException::class)
    fun getInputStream(): InputStream {
        throw RuntimeException("not implemented")
    }

    @Throws(IOException::class)
    fun getData(): ByteArray {
        val inputStream = getInputStream()
        val outputStream = ByteArrayOutputStream()
        val buf = ByteArray(4096)
        var value: Int = inputStream.read(buf)
        while (value > 0) {
            outputStream.write(buf, 0, value)
            value = inputStream.read(buf)
        }
        inputStream.close()
        outputStream.close()
        return outputStream.toByteArray()
    }
}