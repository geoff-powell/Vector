package com.greenmist.vector.lib.svg.element

import android.graphics.Canvas
import android.graphics.Paint
import com.greenmist.vector.renderer.RenderState
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class LineElement(attributes: Attributes?) : SvgElement(attributes), RenderableElement {

    val x1 = properties["x1"]?.toFloatOrNull() ?: 0f
    val y1 = properties["y1"]?.toFloatOrNull() ?: 0f
    val x2 = properties["x2"]?.toFloatOrNull() ?: 0f
    val y2 = properties["y2"]?.toFloatOrNull() ?: 0f

    override fun render(canvas: Canvas, paint: Paint, renderState: RenderState) {
        if (x1 + y1 + x2 + y2 > 0) {
            canvas.drawLine(x1, y1, x2, y2, paint)
        }
    }
}