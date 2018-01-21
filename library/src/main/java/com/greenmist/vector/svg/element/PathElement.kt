package com.greenmist.vector.lib.svg.element

import android.graphics.Canvas
import android.graphics.Paint
import com.greenmist.vector.lib.model.VPath
import com.greenmist.vector.lib.model.toVPath
import com.greenmist.vector.renderer.RenderState
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class PathElement(attributes: Attributes?) : SvgElement(attributes), RenderableElement {
    val path: VPath? = properties["d"]?.toVPath()

    override fun render(canvas: Canvas, paint: Paint, renderState: RenderState) {
        path?.let {
            renderState.style.fillRule?.let { path.fillType = it.fillType }
            canvas.drawPath(path, paint)
        }
    }
}