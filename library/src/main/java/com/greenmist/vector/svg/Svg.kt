package com.greenmist.vector.lib.svg

import android.graphics.Bitmap
import android.graphics.Canvas
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.renderer.Renderer

/**
 * Created by geoffpowell on 11/19/17.
 */
class Svg internal constructor(
        val rootElement: SvgElement,
        private val elementIdMap: HashMap<String, SvgElement>
) {

    fun getBitmap() : Bitmap {
        val bitmap = Bitmap.createBitmap(1200, 600, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        Renderer.render(canvas, this)

        canvas.save()

        return bitmap
    }
}