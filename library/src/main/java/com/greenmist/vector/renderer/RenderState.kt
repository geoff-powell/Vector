package com.greenmist.vector.renderer

import android.graphics.Canvas
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
        viewBox = ViewBox(copy.viewBox)
        viewPort = ViewBox(copy.viewPort)
    }

    var fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private set
    var strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private set
    var dpi: Int = dpi
        private set
    var viewBox: ViewBox = ViewBox()

    private var viewPort: ViewBox = ViewBox(0f, 0f, width.getPxValueX(this), height.getPxValueY(this))

    fun hasFill() = style.fill != null && style.fill != CssPaint.NONE && style.visibility == CssVisibility.VISIBLE

    fun hasStroke() = style.stroke != null && style.stroke != CssPaint.NONE && style.visibility == CssVisibility.VISIBLE

    fun shouldDisplay() = style.display == null || style.display != CssDisplay.NONE || (viewBox.width > 0f && viewBox.height > 0f)

    init {
        fillPaint.style = Paint.Style.FILL
        strokePaint.style = Paint.Style.STROKE
    }

    fun apply(element: SvgElement, canvas: Canvas) {
        style.updateStyle(element.style)

        if (element is ViewportElement) {
            element.viewBox?.let {
                matrix.preConcat(it.getMatrix(viewPort, it, element.preserveAspectRatio))
            }
        }

        element.transform?.let {
            matrix.preConcat(it.getMatrix())
        }

        style.fontName?.let {
            // TODO Load typeface into cache from assets/downloadable fonts etc
            fillPaint.typeface = Typeface.create(it, Typeface.NORMAL)
            strokePaint.typeface = Typeface.create(it, Typeface.NORMAL)
        }

        val fontSize = style.fontSize?.getPxValueY(this) ?: Unit.PX_DEFAULT_FONT_SIZE
        fillPaint.textSize = fontSize
        strokePaint.textSize = fontSize

        style.fill?.cssColor?.color?.let {
            fillPaint.color = it
        }

        style.stroke?.cssColor?.color?.let { strokePaint.color = it }
        style.strokeWidth?.getPxValue(this)?.let { strokePaint.strokeWidth = it }
        style.strokeLineCap?.let { strokePaint.strokeCap = it }
        style.strokeLineJoin?.let { strokePaint.strokeJoin = it }

        canvas.matrix = matrix
    }
}