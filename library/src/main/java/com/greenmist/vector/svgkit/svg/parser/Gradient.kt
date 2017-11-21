package com.greenmist.vector.svgkit.svg.parser

import android.graphics.Matrix
import com.greenmist.vector.svgkit.svg.ColorSvg
import java.util.*

/**
 * Created by geoffpowell on 11/18/17.
 */
class Gradient {
    internal var id: String? = null
    internal var xlink: String? = null
    var isLinear: Boolean = false
    var x1: Float? = null
    var y1:Float? = null
    var x2:Float? = null
    var y2:Float? = null
    var x: Float? = null
    var y:Float? = null
    var radius:Float? = null
    var fx:Float? = null
    var fy:Float? = null
    var spreadMethod: String? = null
    var gradientUnits: String? = null

    var pX1: Boolean = false
    var pX2:Boolean = false
    var pY1:Boolean = false
    var pY2:Boolean = false
    var pX:Boolean = false
    var pY:Boolean = false
    var pR:Boolean = false
    var pFX:Boolean = false
    var pFY:Boolean = false

    var positions = ArrayList<Float>()
    var colors: ArrayList<ColorSvg> = ArrayList()
    var matrix: Matrix? = null

    fun createChild(g: Gradient): Gradient {
        val child = Gradient()
        child.id = g.id
        child.xlink = id
        child.isLinear = g.isLinear
        child.x1 = g.x1
        child.x2 = g.x2
        child.y1 = g.y1
        child.y2 = g.y2
        child.x = g.x
        child.y = g.y
        child.fx = g.fx
        child.fy = g.fy
        child.radius = g.radius

        child.pX1 = g.pX1
        child.pX2 = g.pX2
        child.pY1 = g.pY1
        child.pY2 = g.pY2
        child.pX = g.pX
        child.pY = g.pY
        child.pR = g.pR
        child.pFX = g.pFX
        child.pFY = g.pFY
        child.spreadMethod = g.spreadMethod
        child.gradientUnits = g.gradientUnits

        child.positions = positions
        child.colors = colors
        child.matrix = matrix

        if (g.matrix != null) {
            if (matrix == null) {
                child.matrix = g.matrix
            } else {
                val m = Matrix(matrix)
                m.postConcat(matrix)
                child.matrix = m
            }
        }
        return child
    }
}