package com.greenmist.vector.renderer

import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Typeface
import com.greenmist.vector.lib.model.Length
import com.greenmist.vector.lib.model.Unit
import com.greenmist.vector.lib.model.ViewBox
import com.greenmist.vector.lib.svg.css.Style
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.svg.css.CssDisplay
import com.greenmist.vector.svg.css.CssPaint
import com.greenmist.vector.svg.css.CssVisibility
import com.greenmist.vector.svg.element.ViewportElement

class RenderState(
        val style: Style = Style.BASE,
        val matrix: Matrix = Matrix(),
        val width: Length = Length(),
        val height: Length = Length(),
        dpi: Int = 0
) {

    constructor(copy: RenderState): this(copy.style.clone() as Style, Matrix(copy.matrix), Length(copy.width), Length(copy.height), copy.dpi) {
        fillPaint = Paint(copy.fillPaint)
        strokePaint = Paint(copy.strokePaint)
        textPaint = Paint(copy.textPaint)
        viewBox = ViewBox(copy.viewBox)
        viewPort = ViewBox(copy.viewPort)
    }

    var fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private set
    var strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private set
    var textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private set
    var dpi: Int = dpi
        private set
    var viewBox: ViewBox = ViewBox()

    var viewPort: ViewBox = ViewBox(0f, 0f, width.getPxValueX(this), height.getPxValueY(this))

    fun hasFill() = style.fill != null && style.fill != CssPaint.NONE && style.visibility == CssVisibility.VISIBLE

    fun hasStroke() = style.stroke != null && style.stroke != CssPaint.NONE && style.visibility == CssVisibility.VISIBLE

    fun shouldDisplay() = style.display == null || style.display != CssDisplay.NONE || (viewBox.width > 0f && viewBox.height > 0f)

    init {
        fillPaint.style = Paint.Style.FILL
        strokePaint.style = Paint.Style.STROKE
        textPaint.style = Paint.Style.FILL_AND_STROKE
    }

    fun apply(element: SvgElement) {
        style.updateStyle(element.style)

        if (element is ViewportElement) {
            element.viewBox?.let {
                matrix.preConcat(it.getMatrix(viewPort, it, element.preserveAspectRatio))
            }
        }

        element.transform?.let {
            matrix.preConcat(it.getMatrix())
        }

        textPaint.textSize = style.fontSize?.getPxValueY(this) ?: Unit.PX_DEFAULT_FONT_SIZE

        style.fill?.cssColor?.color?.let {
            textPaint.color = it
            fillPaint.color = it
        }

        style.stroke?.cssColor?.color?.let { strokePaint.color = it }
        style.strokeWidth?.getPxValue(this)?.let { strokePaint.strokeWidth = it }
        style.strokeLineCap?.let { strokePaint.strokeCap = it }
        style.strokeLineJoin?.let { strokePaint.strokeJoin = it }
    }
}