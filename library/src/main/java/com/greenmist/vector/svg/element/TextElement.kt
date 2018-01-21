package com.greenmist.vector.lib.svg.element

import android.graphics.*
import com.greenmist.vector.lib.model.toLength
import com.greenmist.vector.renderer.RenderState
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class TextElement(
        attributes: Attributes?
) : SvgElement(attributes), RenderableElement {

    //TODO x, y list of coordinates
    val x = properties["x"]?.toLength()
    val y = properties["y"]?.toLength()
    //TODO dx, dy list of lengths
    val dx = properties["dx"]?.toLength()
    val dy = properties["dy"]?.toLength()
    //TODO list of rotations
    val rotate = properties["rotate"]?.toFloatOrNull()
    val textLength = properties["textLength"]?.toLength()

    var text: String = ""

    override fun parseContent(content: String) {
        text = content
    }

    override fun render(canvas: Canvas, paint: Paint, renderState: RenderState) {
        if (text.isNotBlank()) {
            val xVal = x?.getPxValueX(renderState) ?: 0f
            val yVal = y?.getPxValueY(renderState) ?: 0f
            canvas.drawText(text, xVal, yVal, paint)
        }
    }
}