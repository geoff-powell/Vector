package com.greenmist.vector.renderer

import android.graphics.Paint
import com.greenmist.vector.lib.svg.css.Style
import com.greenmist.vector.svg.css.CssPaint

class RenderState(val style: Style = Style.BASE) {

    constructor(copy: RenderState): this(copy.style.clone() as Style) {
        fillPaint = Paint(copy.fillPaint)
        strokePaint = Paint(copy.strokePaint)
        textPaint = Paint(copy.textPaint)
    }

    var fillPaint = Paint()
        private set
    var strokePaint = Paint()
        private set
    var textPaint = Paint()
        private set

    fun hasFill() = style.fill != null && style.fill != CssPaint.NONE

    fun hasStroke() = style.stroke != null && style.stroke != CssPaint.NONE

    init {
        fillPaint.style = Paint.Style.FILL
        fillPaint.isAntiAlias = true
        strokePaint.style = Paint.Style.STROKE
        strokePaint.isAntiAlias = true
        textPaint.style = Paint.Style.FILL_AND_STROKE
        textPaint.isAntiAlias = true
    }

    fun updateStyle(style: Style) {
        this.style.updateStyle(style)
    }

    fun apply() {
        style.fill?.cssColor?.color?.let { fillPaint.color = it }

        style.stroke?.cssColor?.color?.let { strokePaint.color = it }
        style.strokeWidth?.getPxValue(0f)?.let { strokePaint.strokeWidth = it }
        style.strokeLineCap?.let { strokePaint.strokeCap = it }
        style.strokeLineJoin?.let { strokePaint.strokeJoin = it }
    }
}