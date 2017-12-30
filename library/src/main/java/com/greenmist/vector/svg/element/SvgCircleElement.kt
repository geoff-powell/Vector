package com.greenmist.vector.lib.svg.element

import android.graphics.Canvas
import android.graphics.Paint
import com.greenmist.vector.lib.model.toCssColor
import com.greenmist.vector.lib.svg.interfaces.Renderable
import com.greenmist.vector.logger.SvgLogger
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class SvgCircleElement(attributes: Attributes?) : SvgElement(attributes), Renderable {

    val x = properties["cx"]?.toFloatOrNull()
    val y = properties["cy"]?.toFloatOrNull()
    val radius = properties["r"]?.toFloatOrNull()

    override fun applyStyle(paint: Paint) {
    }

    override fun render(canvas: Canvas, paint: Paint) {
        if (x != null && y != null && radius != null) {
            style.fill?.let {
                it.toCssColor()?.let {
                    paint.color = it.color
                }
            }
            canvas.drawCircle(x, y, radius, paint)

            style.stroke?.let {
                it.toCssColor()?.let {
                    paint.color = it.color
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = style.strokeWidth?.getPxValue(0f) ?: 1f

                    canvas.drawCircle(x, y, radius, paint)
                }
            }
        } else {
            SvgLogger.e("Invalid properties x=$x y=$y radius=$radius")
        }
    }
}