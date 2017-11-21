package com.greenmist.vector.svgkit.svg

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Created by geoffpowell on 11/18/17.
 */
interface SvgElement {

    fun draw(canvas: Canvas, paint: Paint)

}