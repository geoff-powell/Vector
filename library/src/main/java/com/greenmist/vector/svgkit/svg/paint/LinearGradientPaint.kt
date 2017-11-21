package com.greenmist.vector.svgkit.svg.paint

import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.Shader
import com.greenmist.vector.svgkit.svg.ColorSvg

/**
 * Created by geoffpowell on 11/18/17.
 */
class LinearGradientPaint(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        val positions: FloatArray,
        val colors: Array<ColorSvg>,
        internal val mTileMode: Shader.TileMode) {

    internal var matrix: Matrix? = null

    constructor(
            start: PointF,
            end: PointF,
            positions: FloatArray,
            colors: Array<ColorSvg>,
            tileMode: Shader.TileMode,
            matrix: Matrix
    ) : this(start.x, start.y, end.x, end.y, positions, colors, tileMode) {
        this.matrix = matrix
    }
}
