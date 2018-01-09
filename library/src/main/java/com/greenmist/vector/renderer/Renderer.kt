package com.greenmist.vector.renderer

import android.graphics.Canvas
import android.graphics.Paint
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

        renderState.updateStyle(element.style)
        renderState.apply()

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

    fun render(canvas: Canvas, svg: Svg) {
        renderState = RenderState()
        stateStack.push(RenderState(renderState))

        drawElement(svg.rootElement, canvas)

        stateStack.pop()
    }
}