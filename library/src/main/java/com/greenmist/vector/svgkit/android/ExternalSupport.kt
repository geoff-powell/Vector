package com.greenmist.vector.svgkit.android

import android.content.res.AssetManager
import android.graphics.Typeface
import android.os.Environment
import com.greenmist.vector.logger.SvgLogger
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by geoffpowell on 11/18/17.
 */
class ExternalSupport(val assetManager: AssetManager) {

    fun getExternalFont(fontRef: String, fontStyle: Int): Typeface {
        try {
            return Typeface.createFromAsset(assetManager, fontRef)
        } catch (e: Exception) {
            SvgLogger.e(e, "Error creating font asset $fontRef.")
        }

        try {
            var f = File(fontRef)
            if (f.exists() == false) {
                f = File(Environment.getDownloadCacheDirectory(), fontRef)
            }
            if (f.exists() == false) {
                f = File(Environment.getDataDirectory(), fontRef)
            }
            if (f.exists() == false) {
                f = File(Environment.getExternalStorageDirectory(), fontRef)
            }
            if (f.exists() == false) {
                f = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fontRef)
            }
            if (f.exists()) {
                return Typeface.createFromFile(f)
            }
        } catch (e: Exception) {
            SvgLogger.e(e, "Error finding external font $fontRef.")
        }

        try {
            val f = downloadToCache(fontRef)
            if (f != null && f.exists()) {
                return Typeface.createFromFile(f)
            }
        } catch (e: Exception) {
            SvgLogger.e(e, "Error loading downloaded font $fontRef.")
        }

        return Typeface.create(fontRef, fontStyle)
    }

    fun downloadToCache(href: String): File? {
        try {
            val u = URL(href)
            var ref = u.file
            if (ref.indexOf('/') >= 0) {
                ref = ref.substring(ref.lastIndexOf('/') + 1)
            }
            val f = File(Environment.getDownloadCacheDirectory(), ref)
            if (f.exists()) {
                return f
            }

            val urlConnection = u.openConnection() as HttpURLConnection
            urlConnection.doOutput = true
            urlConnection.connect()

            val fileOutput = FileOutputStream(f)
            val inputStream = urlConnection.inputStream
            var downloadedSize = 0

            val buffer = ByteArray(65536)
            var bufferLength = 0
            do {
                bufferLength = inputStream.read(buffer)
                fileOutput.write(buffer, 0, bufferLength)
                downloadedSize += bufferLength

            } while (bufferLength > 0)

            fileOutput.close()
            inputStream.close()
            return f

        } catch (e: Exception) {
            SvgLogger.e(e, "Error downloading font at $href.")
            return null
        }
    }

    private fun canonify(s: String): String {
        var value = s
        while (true) {
            val k = value.indexOf("/..")
            if (k < 0) {
                break
            }

            val j = value.lastIndexOf('/', k - 1)

            value = if (j > 0) {
                value.substring(0, j) + value.substring(k + 3)
            } else {
                value.substring(0, k) + value.substring(k + 3)
            }
        }
        return value
    }

    fun getExternalURL(base: String?, href: String): InputStream? {
        var baseUrl = base
        var hrefValue = href
        if (!hrefValue.startsWith("/") && hrefValue.indexOf(':') < 0) {
            if (baseUrl != null) {
                if (!baseUrl.endsWith("/")) baseUrl += "/"
                hrefValue = baseUrl + hrefValue
            }
        }

        var goodRef = canonify(hrefValue)

        val k = goodRef.indexOf(':')
        if (k > 0 && !goodRef.startsWith("android.resource:")) {
            goodRef = goodRef.substring(k + 1)
        }
        if (goodRef.startsWith("raw/")) {
            goodRef = goodRef.substring(4)
        }

        try {
            return assetManager.open(goodRef)
        } catch (e: Exception) {
            SvgLogger.e(e, "Could not open asset for external url $hrefValue")
        }

        try {
            val f = File(goodRef)
            if (f.exists()) {
                return FileInputStream(f)
            }
        } catch (e: Exception) {
            SvgLogger.e(e, "Could not open file stream for external url $hrefValue")
        }

        try {
            return URL(hrefValue).openStream()
        } catch (e: Exception) {
            SvgLogger.e(e, "Could not open url stream for external url $hrefValue")
        }

        return null
    }
}