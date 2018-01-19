package com.greenmist.vector.lib.model

import android.graphics.Matrix
import android.graphics.RectF
import com.greenmist.vector.lib.svg.util.TextScanner
import com.greenmist.vector.renderer.RenderState
import com.greenmist.vector.svg.exception.SvgParseException
import com.greenmist.vector.svg.model.AlignmentType
import com.greenmist.vector.svg.model.MeetOrSlice
import com.greenmist.vector.svg.model.PreserveAspectRatio

/**
 * Created by geoffpowell on 11/19/17.
 */
class ViewBox() : Cloneable {

    constructor(minX: Float, minY: Float, width: Float, height: Float) : this() {
        this.minX = minX
        this.minY = minY
        this.width = width
        this.height = height
    }

    constructor(copy: ViewBox) : this(copy.minX, copy.minY, copy.width, copy.height)

    fun toRectF() : RectF = RectF(minX, minY, width, height)

    var minX: Float = 0f
    var minY: Float = 0f
    var width: Float = 0f
    var height: Float = 0f

    val maxX: Float
       get() = minX + width

    val maxY: Float
        get() = minY + height

    val aspectRatio: Float
        get() = width/height

    fun union(other: ViewBox) {
        when {
            other.minX < minX -> minX = other.minX
            other.minY < minY -> minY = other.minY
            other.maxX > maxX -> width = other.maxX - minX
            other.maxX > maxX -> height = other.maxY - minY
        }
    }

    //TODO Remove comments
    fun getMatrix(renderState: RenderState, viewPort: Viewport, viewBox: ViewBox, preserveAspectRatio: PreserveAspectRatio?): Matrix {
        val matrix = Matrix()

        val aspectRatio = preserveAspectRatio ?: PreserveAspectRatio()

        val width = viewPort.width.getPxValueX(renderState)
        val height = viewPort.height.getPxValueY(renderState)

        val xScale = width / viewBox.width
        val yScale = height / viewBox.height
        var xOffset = -viewBox.minX
        var yOffset = -viewBox.minY

        // 'none' means scale both dimensions to fit the viewport
        if (aspectRatio.alignment.xAlignment == AlignmentType.NONE && aspectRatio.alignment.yAlignment == AlignmentType.NONE) {
            matrix.preTranslate(viewPort.x, viewPort.y)
            matrix.preScale(xScale, yScale)
            matrix.preTranslate(xOffset, yOffset)
            return matrix
        }

        // Otherwise, the aspect ratio of the image is kept.
        // What scale are we going to use?
        val scale = if (aspectRatio.scale === MeetOrSlice.SLICE) {
            Math.max(xScale, yScale)
        } else {
            Math.min(xScale, yScale)
        }

        // What size will the image end up being?
        val imageW = width / scale
        val imageH = height / scale
        // Determine final X position
        when (aspectRatio.alignment.xAlignment) {
            AlignmentType.XMID -> xOffset -= (viewBox.width - imageW) / 2
            AlignmentType.XMAX -> xOffset -= viewBox.width - imageW
            else -> {}
        }

        // Determine final Y position
        when (aspectRatio.alignment.yAlignment) {
            AlignmentType.YMID -> yOffset -= (viewBox.height - imageH) / 2
            AlignmentType.YMAX -> yOffset -= viewBox.height - imageH
            else -> {}
        }

        matrix.preTranslate(viewPort.x, viewPort.y)
        matrix.preScale(scale, scale)
        matrix.preTranslate(xOffset, yOffset)
        return matrix
    }

    override fun toString(): String = "[$minX $minY $width $height]"

    companion object {
        fun fromString(string: String): ViewBox {
            val viewBox = ViewBox()
            val scanner = TextScanner(string)
            viewBox.minX = scanner.nextFloat()
            if (viewBox.minX.isNaN()) throw SvgParseException("Error parsing minX of viewBox")
            viewBox.minY = scanner.nextFloat()
            if (viewBox.minY.isNaN()) throw SvgParseException("Error parsing minY of viewBox")
            viewBox.width = scanner.nextFloat()
            if (viewBox.width.isNaN()) throw SvgParseException("Error parsing width of viewBox")
            viewBox.height = scanner.nextFloat()
            if (viewBox.height.isNaN()) throw SvgParseException("Error parsing height of viewBox")

            return viewBox
        }

        fun fromLimits(minX: Float, minY: Float, maxX: Float, maxY: Float) : ViewBox =
                ViewBox(minX, minY, maxX - minX, maxY - minY)
    }
}

fun String.toViewBox(): ViewBox = ViewBox.fromString(this)