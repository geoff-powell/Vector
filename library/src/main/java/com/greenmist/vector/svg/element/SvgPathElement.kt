package com.greenmist.vector.lib.svg.element

import android.graphics.Canvas
import android.graphics.Paint
import com.greenmist.vector.lib.svg.interfaces.Renderable
import com.greenmist.vector.lib.model.VPath
import com.greenmist.vector.lib.model.toCssColor
import com.greenmist.vector.lib.model.toVPath
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class SvgPathElement(attributes: Attributes?) : SvgElement(attributes), Renderable {
    val path: VPath? = properties["d"]?.toVPath()

    override fun applyStyle(paint: Paint) {
    }

    override fun render(canvas: Canvas, paint: Paint) {
        path?.let {
            style.fill?.let {
                it.toCssColor()?.let {
                    paint.color = it.color
                    paint.style = Paint.Style.FILL
                    canvas.drawPath(path, paint)
                }
            }

            style.stroke?.let {
                it.toCssColor()?.let {
                    paint.color = it.color
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = style.strokeWidth?.getPxValue(0f) ?: 1f

                    canvas.drawPath(path, paint)
                }
            }
        }
    }
}