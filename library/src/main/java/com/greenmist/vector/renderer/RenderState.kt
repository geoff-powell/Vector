package com.greenmist.vector.renderer

import android.graphics.*
import com.greenmist.vector.lib.model.Unit
import com.greenmist.vector.lib.model.ViewBox
import com.greenmist.vector.lib.model.Viewport
import com.greenmist.vector.lib.svg.css.Style
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.svg.css.CssDisplay
import com.greenmist.vector.svg.css.CssPaint
import com.greenmist.vector.svg.css.CssVisibility
import com.greenmist.vector.svg.element.TransformElement
import com.greenmist.vector.svg.element.ViewportElement

class RenderState(
        val style: Style = Style.BASE,
        val matrix: Matrix = Matrix(),
        var viewport: Viewport = Viewport(),
        var viewBox: ViewBox? = null,
        val fillPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG),
        val strokePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG),
        val dpi: Int = 0
) {

    constructor(copy: RenderState): this(
            copy.style.clone() as Style,
            Matrix(copy.matrix),
            copy.viewport.copy(),
            if (copy.viewBox != null)
                ViewBox(copy.viewBox!!)
            else null,
            Paint(copy.fillPaint),
            Paint(copy.strokePaint),
            copy.dpi)

    fun hasFill() = style.fill != null && style.fill != CssPaint.NONE && style.visibility == CssVisibility.VISIBLE

    fun hasStroke() = style.stroke != null && style.stroke != CssPaint.NONE && style.visibility == CssVisibility.VISIBLE

    fun shouldDisplay(): Boolean {
        val validViewbox = viewBox?.let {
            it.width > 0f && it.height > 0f
        } ?: false
        return style.display == null || style.display != CssDisplay.NONE || validViewbox
    }

    init {
        fillPaint.style = Paint.Style.FILL
        strokePaint.style = Paint.Style.STROKE
    }

    fun apply(element: SvgElement, canvas: Canvas) {
        style.updateStyle(element.style)

        when(element) {
            is ViewportElement -> {
                viewport = element.viewport
                canvas.clipRect(viewport.toRectF(this))

                element.viewBox?.let {
                    viewBox = it
                }
                viewBox?.let {
                    matrix.preConcat(it.getMatrix(this, viewport, it, element.preserveAspectRatio))
                }
            }
            is TransformElement -> {
                element.transform?.let {
                    matrix.preConcat(it.getMatrix())
                }
            }
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