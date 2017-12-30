package com.greenmist.vector.lib.svg.interfaces

import android.graphics.Canvas
import android.graphics.Paint

interface Renderable {

    fun applyStyle(paint: Paint)

    fun render(canvas: Canvas, paint: Paint)
}