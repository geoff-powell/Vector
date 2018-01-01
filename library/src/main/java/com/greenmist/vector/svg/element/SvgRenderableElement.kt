package com.greenmist.vector.lib.svg.element

import android.graphics.Canvas
import android.graphics.Paint
import com.greenmist.vector.renderer.RenderState
import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
abstract class SvgRenderableElement(attributes: Attributes?) : SvgElement(attributes) {

    abstract fun render(canvas: Canvas, paint: Paint, renderState: RenderState)
}