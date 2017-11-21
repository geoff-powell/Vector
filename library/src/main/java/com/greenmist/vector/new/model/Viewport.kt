package com.greenmist.vector.new.model

import android.graphics.RectF

/**
 * Created by geoffpowell on 11/19/17.
 */
class Viewport constructor(var minX: Float, var minY: Float, var width: Float, var height: Float) : Cloneable {

    constructor(copy: Viewport) : this(copy.minX, copy.minY, copy.width, copy.height)

    fun toRectF() : RectF = RectF(minX, minY, width, height)

    fun maxX() : Float = minX + width

    fun maxY() : Float = minY + height

    fun union(other: Viewport) {
        when {
            other.minX < minX -> minX = other.minX
            other.minY < minY -> minY = other.minY
            other.maxX() > maxX() -> width = other.maxX() - minX
            other.maxX() > maxX() -> height = other.maxY() - minY
        }
    }

    override fun toString(): String = "[$minX $minY $width $height]"

    companion object {

        fun fromLimits(minX: Float, minY: Float, maxX: Float, maxY: Float) : Viewport =
                Viewport(minX, minY, maxX - minX, maxY - minY)
    }
}