package com.greenmist.vector.lib.svg.element

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.greenmist.vector.lib.model.Length
import com.greenmist.vector.lib.model.toLength
import com.greenmist.vector.renderer.RenderState
import com.greenmist.vector.svg.element.BoxElement
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class RectElement(attributes: Attributes?) : SvgElement(attributes), RenderableElement, BoxElement {
    override var x: Length? = properties["x"]?.toLength() ?: Length()
    override var y: Length? = properties["y"]?.toLength() ?: Length()
    override var width = properties["width"]?.toLength()
    override var height = properties["height"]?.toLength()
    val rx = properties["rx"]?.toLength()
    val ry = properties["ry"]?.toLength()

    override fun render(canvas: Canvas, paint: Paint, renderState: RenderState) {
        val xVal = x?.getPxValueX(renderState)
        val yVal = y?.getPxValueY(renderState)
        val w = width?.getPxValueX(renderState)
        val h = height?.getPxValueY(renderState)

        if (xVal != null && yVal != null && w != null && h != null) {
            val rect = RectF(xVal, yVal, xVal + w, yVal + h)

            var radiusX = rx?.getPxValueX(renderState)
            var radiusY = ry?.getPxValueY(renderState)

            when {
                radiusX != null && radiusY == null -> {
                    radiusY = radiusX
                }
                radiusY != null && radiusX == null -> {
                    radiusX = radiusY
                }
            }

            radiusX = radiusX ?: 0f
            radiusY = radiusY ?: 0f

            if (radiusX > w / 2f) {
                radiusX = w / 2f
            }

            if (radiusY > w / 2f) {
                radiusY = w / 2f
            }

            if (radiusX > 0 && radiusY > 0) {
                canvas.drawRoundRect(rect, radiusX, radiusY, paint)
            } else {
                canvas.drawRect(rect, paint)
            }
        }
    }
}