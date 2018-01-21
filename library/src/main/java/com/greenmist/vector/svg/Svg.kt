package com.greenmist.vector.lib.svg

import android.graphics.Bitmap
import android.graphics.Canvas
import com.greenmist.vector.lib.svg.element.RootSvgElement
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.renderer.Renderer

/**
 * Created by geoffpowell on 11/19/17.
 */
class Svg internal constructor(
        val rootElement: RootSvgElement,
        private val elementIdMap: HashMap<String, SvgElement>,
        var dpi: Int
) {

    val width: Float
        get() = rootElement.width?.getPxValueX(dpi.toFloat()) ?: 0f
    val height: Float
        get() = rootElement.height?.getPxValueY(dpi.toFloat()) ?: 0f

    fun getByxLink(id: String): SvgElement? {
        return if (id.contains("#")) {
            elementIdMap[id.replace("#", "")]
        } else {
            null
        }
    }

    fun getBitmap() : Bitmap {
        val bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        Renderer.render(canvas, this, dpi)

        canvas.save()

        return bitmap
    }
}