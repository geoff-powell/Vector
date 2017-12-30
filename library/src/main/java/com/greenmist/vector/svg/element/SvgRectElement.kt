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
class SvgRectElement(attributes: Attributes?) : SvgElement(attributes), Renderable {
    val x = properties["x"]?.toFloatOrNull()
    val y = properties["y"]?.toFloatOrNull()
    val width = properties["width"]?.toFloatOrNull()
    val height = properties["height"]?.toFloatOrNull()

    override fun applyStyle(paint: Paint) {
    }

    override fun render(canvas: Canvas, paint: Paint) {
        if (x != null && y != null && width != null && height != null) {
            style.fill?.let {
                it.toCssColor()?.let {
                    paint.color = it.color
                    paint.style = Paint.Style.FILL
                    canvas.drawRect(x, y, x + width, y + height, paint)
                }
            }

            style.stroke?.let {
                it.toCssColor()?.let {
                    paint.color = it.color
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = style.strokeWidth?.getPxValue(0f) ?: 1f

                    canvas.drawRect(x, y, x + width, y + height, paint)
                }
            }
        } else {
            SvgLogger.e("Invalid properties x=$x y=$y width=$width height=$height")
        }
    }
}