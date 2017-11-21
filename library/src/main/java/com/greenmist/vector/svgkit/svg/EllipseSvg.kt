package com.greenmist.vector.svgkit.svg

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * Created by geoffpowell on 11/18/17.
 */
class EllipseSvg(left: Float, top: Float, width: Float, height: Float) : SvgElement {

    val ellipse: RectF
    val bounds: RectF

    init {
        val x2 = left + width
        val y2 = top + height
        ellipse = RectF(left, top, x2, y2)
        bounds = RectF(left, top, width, height)
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawOval(ellipse, paint)
    }
}