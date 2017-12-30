package com.greenmist.vector.lib.svg

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.lib.svg.interfaces.Renderable

/**
 * Created by geoffpowell on 11/19/17.
 */
class Svg internal constructor(
        val rootElement: SvgElement,
        private val elementIdMap: HashMap<String, SvgElement>
) {

    val paint = Paint()

    fun drawElement(element: SvgElement, canvas: Canvas) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL

        if (element is Renderable) {
            element.applyStyle(paint)
            element.render(canvas, paint)
        }

        element.children.forEach {
            drawElement(it, canvas)
        }
    }

    fun getBitmap() : Bitmap {
        val bitmap = Bitmap.createBitmap(1200, 600, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        drawElement(rootElement, canvas)

        canvas.save()

        return bitmap
    }
}