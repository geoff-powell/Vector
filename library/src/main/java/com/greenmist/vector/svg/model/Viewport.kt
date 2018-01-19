package com.greenmist.vector.lib.model

import android.graphics.RectF
import com.greenmist.vector.lib.svg.Properties
import com.greenmist.vector.renderer.RenderState

/**
 * Created by geoffpowell on 11/19/17.
 */
data class Viewport(
        val x: Float = 0f,
        val y: Float = 0f,
        val width: Length = Length(100f, Unit.PERCENT),
        val height: Length = Length(100f, Unit.PERCENT)
) {

    constructor(properties: Properties) : this(
            properties["x"]?.toFloatOrNull() ?: 0f,
            properties["y"]?.toFloatOrNull() ?: 0f,
            properties["width"]?.toLength() ?: Length(100f, Unit.PERCENT),
            properties["height"]?.toLength() ?: Length(100f, Unit.PERCENT)
    )

    fun toRectF(renderState: RenderState) : RectF = RectF(x, x, width.getPxValueX(renderState), height.getPxValueY(renderState))
}