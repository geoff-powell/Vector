package com.greenmist.vector.lib.model

import android.graphics.Matrix
import android.graphics.Path
import com.greenmist.vector.lib.svg.util.TextScanner
import com.greenmist.vector.logger.SvgLogger

/**
 * Created by geoff.powell on 12/16/17.
 */

/*
 SVG Path Documentation
 https://www.w3.org/TR/SVG/paths.html
 */
class VPath : Path() {

    fun arcTo(lastX: Float, lastY: Float, rx: Float, ry: Float, angle: Float, largeArcFlag: Boolean, sweepFlag: Boolean, x: Float, y: Float) {
        if (lastX == x && lastY == y) {
            return
        }

        if (rx == 0f || ry == 0f) {
            lineTo(x, y)
            return
        }

        var rx = Math.abs(rx)
        var ry = Math.abs(ry)

        val angleRad = Math.toRadians(angle % 360.0).toFloat()
        val cosAngle = Math.cos(angleRad.toDouble()).toFloat()
        val sinAngle = Math.sin(angleRad.toDouble()).toFloat()

        val dx2 = (lastX - x) / 2.0f
        val dy2 = (lastY - y) / 2.0f

        val x1 = (cosAngle * dx2 + sinAngle * dy2)
        val y1 = (-sinAngle * dx2 + cosAngle * dy2)

        var rxSquared = rx * rx
        var rySquared = ry * ry
        val x1Squared = x1 * x1
        val y1Squared = y1 * y1

        val radiiCheck = x1Squared / rxSquared + y1Squared / rySquared
        if (radiiCheck > 1) {
            rx *= Math.sqrt(radiiCheck.toDouble()).toFloat()
            ry *= Math.sqrt(radiiCheck.toDouble()).toFloat()
            rxSquared = rx * rx
            rySquared = ry * ry
        }

        var sign = if (largeArcFlag == sweepFlag) -1f else 1f
        var sq = ((rxSquared * rySquared) - (rxSquared * y1Squared) - (rySquared * x1Squared)) / ((rxSquared * y1Squared) + (rySquared * x1Squared))
        sq = if (sq < 0) 0f else sq
        val coef = sign * Math.sqrt(sq.toDouble())
        val cx1 = coef * ((rx * y1) / ry)
        val cy1 = coef * -((ry * x1) / rx)

        val sx2 = (lastX + x) / 2.0f
        val sy2 = (lastY + y) / 2.0f
        val cx = (sx2 + (cosAngle * cx1 - sinAngle * cy1)).toFloat()
        val cy = (sy2 + (sinAngle * cx1 + cosAngle * cy1)).toFloat()

        val ux = (x1 - cx1) / rx
        val uy = (y1 - cy1) / ry
        val vx = (-x1 - cx1) / rx
        val vy = (-y1 - cy1) / ry

        var n = Math.sqrt((ux * ux) + (uy * uy))
        var p = ux
        sign = if (uy < 0) -1f else 1f
        var angleStart = Math.toDegrees(sign * Math.acos(p / n))

        n = Math.sqrt((ux * ux + uy * uy) * (vx * vx + vy * vy))
        p = ux * vx + uy * vy
        sign = if (ux * vy - uy * vx < 0) -1f else 1f
        var angleExtent = Math.toDegrees(sign * Math.acos(p / n))
        if (sweepFlag == false && angleExtent > 0) {
            angleExtent -= 360f
        } else if (sweepFlag && angleExtent < 0) {
            angleExtent += 360f
        }
        angleExtent %= 360f
        angleStart %= 360f

        val segments = Math.ceil(Math.abs(angleExtent) / 90.0).toInt()

        angleStart = Math.toRadians(angleStart)
        angleExtent = Math.toRadians(angleExtent)
        val angleIncr = (angleExtent / segments).toFloat()

        val controlLen = (4.0 / 3.0 * Math.sin(angleIncr / 2.0) / (1.0 + Math.cos(angleIncr / 2.0))).toFloat()

        val cubicPoints = FloatArray(segments * 6)
        var pos = 0

        var curAngle: Float
        var dx: Float
        var dy: Float

        for (i in 0 until segments) {
            curAngle = (angleStart + i * angleIncr).toFloat()

            dx = Math.cos(curAngle.toDouble()).toFloat()
            dy = Math.sin(curAngle.toDouble()).toFloat()

            cubicPoints[pos++] = dx - controlLen * dy
            cubicPoints[pos++] = dy + controlLen * dx

            curAngle += angleIncr
            dx = Math.cos(curAngle.toDouble()).toFloat()
            dy = Math.sin(curAngle.toDouble()).toFloat()
            cubicPoints[pos++] = dx + controlLen * dy
            cubicPoints[pos++] = dy - controlLen * dx

            cubicPoints[pos++] = dx
            cubicPoints[pos++] = dy
        }

        val m = Matrix()
        m.postScale(rx, ry)
        m.postRotate(angle)
        m.postTranslate(cx, cy)
        m.mapPoints(cubicPoints)

        cubicPoints[cubicPoints.size - 2] = x
        cubicPoints[cubicPoints.size - 1] = y

        for (i in 0 until cubicPoints.size step 6) {
            cubicTo(cubicPoints[i], cubicPoints[i + 1], cubicPoints[i + 2], cubicPoints[i + 3], cubicPoints[i + 4], cubicPoints[i + 5])
        }
    }
    
    companion object {
        val supportedCommands = arrayListOf<Char>()

        val moveCommands = createCommandList('m')
        val lineCommands = createCommandList('l')
        val horizontalLineCommands = createCommandList('h')
        val verticalLineCommands = createCommandList('v')
        val curveCommands = createCommandList('c')
        val curveShortHandCommands = createCommandList('s')
        val quadBezierCommands = createCommandList('q')
        val quadBezierShortHandCommands = createCommandList('t')
        val archCommands = createCommandList('a')
        val closePathCommands = createCommandList('z')

        private fun createCommandList(command: Char) : List<Char> {
            val list = listOf(command.toLowerCase(), command.toUpperCase())
            supportedCommands.addAll(list)
            return list
        }
    }
}

fun CharSequence.toVPath() : VPath {

    val path = VPath()
    val scanner = TextScanner(this)

    var currentX = 0f
    var currentY = 0f    // The last point visited in the subpath
    var lastMoveX = 0f
    var lastMoveY = 0f  // The initial point of current subpath
    var lastControlX = 0f
    var lastControlY = 0f  // Last control point of the just completed bezier curve.

    var isRelative: Boolean
    var isFirstCommand = true
    var skipNextCommand = false
    var largeArcFlag: Boolean
    var sweepFlag: Boolean

    var x: Float
    var y: Float
    var x1: Float
    var y1: Float
    var x2: Float
    var y2: Float
    var rx: Float
    var ry: Float
    var xAxisRotation: Float
    var currentCommand: Char = scanner.current

    SvgLogger.e("Start path")
    pathParse@ while (scanner.hasNext()) {
        if (skipNextCommand == false) {
            scanner.next()
        }

        skipNextCommand = false
        isRelative = currentCommand.isLowerCase()

        if (currentCommand in VPath.supportedCommands) {
            isRelative = isRelative && isFirstCommand == false

            val relativeStr = if (isRelative) "relative" else "absolute"

            when (currentCommand) {
                in VPath.moveCommands -> {
                    x = scanner.nextFloat()
                    y = scanner.nextFloat()

                    if (x.isNaN() || y.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        x += currentX
                        y += currentY
                    }

                    path.moveTo(x, y)
                    SvgLogger.d("Move to $x, $y $relativeStr")

                    lastControlX = x
                    lastControlY = y
                    currentX = x
                    currentY = y
                    lastMoveX = x
                    lastMoveY = y
                }
                in VPath.lineCommands -> {
                    x = scanner.nextFloat()
                    y = scanner.nextFloat()

                    if (x.isNaN() || y.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        x += currentX
                        y += currentY
                    }

                    path.lineTo(x, y)
                    SvgLogger.d("Line from $currentX, $currentY to $x, $y $relativeStr")

                    lastControlX = x
                    lastControlY = y
                    currentX = x
                    currentY = y
                }
                in VPath.horizontalLineCommands -> {
                    x = scanner.nextFloat()

                    if (x.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        x += currentX
                    }

                    path.lineTo(x, currentY)

                    lastControlX = x
                    currentX = x
                }
                in VPath.verticalLineCommands -> {
                    y = scanner.nextFloat()

                    if (y.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        y += currentY
                    }

                    path.lineTo(currentX, y)

                    lastControlY = y
                    currentY = y
                }
                in VPath.curveCommands -> {
                    x1 = scanner.nextFloat()
                    y1 = scanner.nextFloat()
                    x2 = scanner.nextFloat()
                    y2 = scanner.nextFloat()
                    x = scanner.nextFloat()
                    y = scanner.nextFloat()

                    if (x.isNaN() || y.isNaN() || x1.isNaN() || y1.isNaN() || x2.isNaN() || y2.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        x += currentX
                        y += currentY
                        x1 += currentX
                        y1 += currentY
                        x2 += currentX
                        y2 += currentY
                    }

                    path.cubicTo(x1, y1, x2, y2, x, y)
                    SvgLogger.d("Cubic Bezier at $currentX, $currentY to $x, $y at start control: $x1, $y1 and end control: $x2, $y2 $relativeStr")

                    lastControlX = x + (x - x2)
                    lastControlY = y + (y - y2)

                    currentX = x
                    currentY = y
                }
                in VPath.curveShortHandCommands -> {
                    x2 = scanner.nextFloat()
                    y2 = scanner.nextFloat()
                    x = scanner.nextFloat()
                    y = scanner.nextFloat()

                    if (x.isNaN() || y.isNaN() || x2.isNaN() || y2.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        x += currentX
                        y += currentY
                        x2 += currentX
                        y2 += currentY
                    }

                    path.cubicTo(lastControlX, lastControlY, x2, y2, x, y)
                    SvgLogger.d("Shorthand Cubic Bezier at $currentX, $currentY to $x, $y at start control: $lastControlX, $lastControlY and end control: $x2, $y2 $relativeStr")

                    lastControlX = x + (x - x2)
                    lastControlY = y + (y - y2)

                    currentX = x
                    currentY = y
                }
                in VPath.quadBezierCommands -> {
                    x1 = scanner.nextFloat()
                    y1 = scanner.nextFloat()
                    x = scanner.nextFloat()
                    y = scanner.nextFloat()

                    if (x.isNaN() || y.isNaN() || x1.isNaN() || y1.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        x += currentX
                        y += currentY
                        x1 += currentX
                        y1 += currentY
                    }

                    path.quadTo(x1, y1, x, y)
                    SvgLogger.d("Quad Bezier at $currentX, $currentY to $x, $y at control $x1, $y1 $relativeStr")

                    lastControlX = x + (x - x1)
                    lastControlY = y + (y - y1)

                    currentX = x
                    currentY = y
                }
                in VPath.quadBezierShortHandCommands -> {
                    x = scanner.nextFloat()
                    y = scanner.nextFloat()

                    if (x.isNaN() || y.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        x += currentX
                        y += currentY
                    }

                    path.quadTo(lastControlX, lastControlY, x, y)
                    SvgLogger.d("Shorthand Quad Bezier at $currentX, $currentY to $x, $y with control $lastControlX, $lastControlY $relativeStr")

                    currentX = x
                    currentY = y
                }
                in VPath.archCommands -> {
                    rx = scanner.nextFloat()
                    ry = scanner.nextFloat()
                    xAxisRotation = scanner.nextFloat()
                    largeArcFlag = scanner.nextFloat() != 0f
                    sweepFlag = scanner.nextFloat() != 0f
                    x = scanner.nextFloat()
                    y = scanner.nextFloat()

                    if (rx.isNaN() || ry.isNaN() || xAxisRotation.isNaN() || x.isNaN() || y.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        x += currentX
                        y += currentY
                    }

                    path.arcTo(currentX, currentY, rx, ry, xAxisRotation, largeArcFlag, sweepFlag, x, y)

                    currentX = x
                    currentY = y
                }
                in VPath.closePathCommands -> {
                    path.close()
                    SvgLogger.d("Closing subpath at $currentX, $currentY to $lastMoveX, $lastMoveY")
                }
            }

            scanner.possibleNextFloat()?.let {
                if (currentCommand in VPath.moveCommands) {
                    currentCommand = if (isFirstCommand || isRelative) 'l' else 'L'
                }
                skipNextCommand = true
            }
        }

        isFirstCommand = false
        scanner.skipWhitespace()

        if (skipNextCommand == false) {
            currentCommand = scanner.current
        }
    }

    SvgLogger.e("End path")
    return path
}