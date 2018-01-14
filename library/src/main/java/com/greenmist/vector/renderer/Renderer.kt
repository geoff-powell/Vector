package com.greenmist.vector.renderer

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.greenmist.vector.lib.model.Length
import com.greenmist.vector.lib.model.ViewBox
import com.greenmist.vector.lib.svg.Svg
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.lib.svg.element.SvgRenderableElement
import java.util.*

object Renderer {

    private var renderState = RenderState()

    val stateStack = Stack<RenderState>()

    fun drawElement(element: SvgElement, canvas: Canvas) {
        stateStack.push(renderState)
        renderState = RenderState(renderState)
        renderState.apply(element)

        canvas.matrix = renderState.matrix

        if (renderState.shouldDisplay()) {
            if (element is SvgRenderableElement) {
                if (renderState.hasFill()) {
                    element.render(canvas, renderState.fillPaint, renderState)
                }
                if (renderState.hasStroke()) {
                    element.render(canvas, renderState.strokePaint, renderState)
                }
            }

            element.children.forEach {
                drawElement(it, canvas)
            }
        }

        renderState = stateStack.pop()
    }

    fun render(canvas: Canvas, svg: Svg, dpi: Int) {
        renderState = RenderState(dpi = dpi, width = svg.rootElement.width ?: Length(), height = svg.rootElement.height ?: Length())
        renderState.viewBox = svg.rootElement.viewBox ?: ViewBox()

        stateStack.push(RenderState(renderState))

        drawElement(svg.rootElement, canvas)

        stateStack.pop()
    }
}