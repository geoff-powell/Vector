package com.greenmist.vector.svgkit.svg

import android.graphics.Paint

/**
 * Created by geoffpowell on 11/18/17.
 */
class BasicStroke(
        val lineWidth: Float,
        val capStyle: Paint.Cap,
        val joinStyle: Paint.Join,
        val dashArray: FloatArray? = null,
        val dashOffset: Float? = null) {

}