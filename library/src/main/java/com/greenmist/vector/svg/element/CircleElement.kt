package com.greenmist.vector.lib.svg.element

import android.graphics.Canvas
import android.graphics.Paint
import com.greenmist.vector.renderer.RenderState
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class CircleElement(attributes: Attributes?) : SvgElement(attributes), RenderableElement {

    val x = properties["cx"]?.toFloatOrNull()
    val y = properties["cy"]?.toFloatOrNull()
    val radius = properties["r"]?.toFloatOrNull()

    override fun render(canvas: Canvas, paint: Paint, renderState: RenderState) {
        if (x != null && y != null && radius != null) {
            canvas.drawCircle(x, y, radius, paint)
        }
    }
}