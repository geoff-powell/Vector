package com.greenmist.vector.svgkit.svg.paint

import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.Shader
import com.greenmist.vector.svgkit.svg.ColorSvg

/**
 * Created by geoffpowell on 11/18/17.
 */
class RadialGradientPaint(
        val x: Float,
        val y: Float,
        val radius: Float,
        val fx: Float,
        val fy: Float,
        val positions: FloatArray,
        val colors: Array<ColorSvg>,
        internal val tileMode: Shader.TileMode
) {

    var matrix: Matrix? = null

    constructor(
            center: PointF,
            radius: Float,
            focus: PointF,
            positions: FloatArray,
            colors: Array<ColorSvg>,
            tileMode: Shader.TileMode,
            matrix: Matrix
    ) : this(center.x, center.y, radius, focus.x, focus.y, positions, colors, tileMode) {
        this.matrix = matrix
    }
}