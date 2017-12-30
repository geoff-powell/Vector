package com.greenmist.vector.lib.model

import android.graphics.Path
import android.os.Build
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

    fun arcToCompat() {

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
                    x = scanner.nextFloat()
                    y = scanner.nextFloat()

                    if (rx.isNaN() || ry.isNaN() || xAxisRotation.isNaN() || x.isNaN() || y.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        path.arcTo(rx, ry, x, y, xAxisRotation, 0f, true)
                    } else {

                    }
                    SvgLogger.d("Shorthand Quad Bezier at $currentX, $currentY to $x, $y with control $lastControlX, $lastControlY $relativeStr")

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