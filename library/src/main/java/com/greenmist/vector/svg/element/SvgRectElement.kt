package com.greenmist.vector.lib.svg.element

import android.graphics.Canvas
import android.graphics.Paint
import com.greenmist.vector.renderer.RenderState
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class SvgRectElement(attributes: Attributes?) : SvgRenderableElement(attributes) {
    val x = properties["x"]?.toFloatOrNull()
    val y = properties["y"]?.toFloatOrNull()
    val width = properties["width"]?.toFloatOrNull()
    val height = properties["height"]?.toFloatOrNull()

    override fun render(canvas: Canvas, paint: Paint, renderState: RenderState) {
        if (x != null && y != null && width != null && height != null) {
            canvas.drawRect(x, y, x + width, y + height, paint)
        }
    }
}