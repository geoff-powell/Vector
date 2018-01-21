package com.greenmist.vector.renderer

import android.graphics.Canvas
import com.greenmist.vector.lib.svg.Svg
import com.greenmist.vector.lib.svg.element.SvgElement
import com.greenmist.vector.lib.svg.element.RenderableElement
import com.greenmist.vector.svg.element.BoxElement
import com.greenmist.vector.svg.element.PositionElement
import com.greenmist.vector.svg.element.ReferenceElement
import java.util.*

object Renderer {

    private var renderState = RenderState()

    val stateStack = Stack<RenderState>()

    fun drawElement(svg: Svg, element: SvgElement, canvas: Canvas) {
        stateStack.push(renderState)
        renderState = RenderState(renderState)
        renderState.apply(element, canvas)

        if (renderState.shouldDisplay()) {
            if (element is RenderableElement) {
                if (renderState.hasFill()) {
                    element.render(canvas, renderState.fillPaint, renderState)
                }
                if (renderState.hasStroke()) {
                    element.render(canvas, renderState.strokePaint, renderState)
                }
            }
            canvas.save()

            if (element is ReferenceElement) {
                element.link?.let {
                    svg.getByxLink(it)?.let {
                        if (it is BoxElement && element is BoxElement) {
                            it.x = element.x ?: it.x
                            it.y = element.y ?: it.y
                            it.width = element.width ?: it.width
                            it.height = element.height ?: it.height
                        }
                        drawElement(svg, it, canvas)
                    }
                }
            }

            if (element.renderChildren) {
                element.children.forEach {
                    drawElement(svg, it, canvas)
                }
            }
        }

        canvas.restore()
        renderState = stateStack.pop()
    }

    fun render(canvas: Canvas, svg: Svg, dpi: Int) {
        renderState = RenderState(dpi = dpi, viewport = svg.rootElement.viewport, viewBox = svg.rootElement.viewBox)

        stateStack.push(RenderState(renderState))

        drawElement(svg, svg.rootElement, canvas)

        stateStack.pop()
    }
}