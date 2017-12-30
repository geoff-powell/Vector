package com.greenmist.vector.lib.model

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

    companion object {
        val supportedCommands = arrayListOf<Char>()

        val moveCommands = createCommandList('m')
        val lineCommands = createCommandList('l')
        val quadBezierCommands = createCommandList('q')
        val quadBezierShortHandCommands = createCommandList('t')
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
    var skipNextCommand: Boolean = false

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
                        path.rMoveTo(x, y)
                    } else {
                        path.moveTo(x, y)
                    }

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
                        path.rLineTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                    SvgLogger.d("Line from $currentX, $currentY to $x, $y $relativeStr")

                    lastControlX = x
                    lastControlY = y
                    currentX = x
                    currentY = y
                }
                in VPath.quadBezierCommands -> {
                    x1 = scanner.nextFloat()
                    y1 = scanner.nextFloat()
                    if (x1.isNaN() || y1.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    x = scanner.nextFloat()
                    y = scanner.nextFloat()
                    if (x.isNaN() || y.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    if (isRelative) {
                        path.rQuadTo(x1, y1, x, y)
                    } else {
                        path.quadTo(x1, y1, x, y)
                    }
                    SvgLogger.d("Quad Bezier at $currentX, $currentY to $x1, $y1 at control $x1, $y1 $relativeStr")

                    lastControlX = x + (x - x1)
                    lastControlY = y + (y - y1)

                    currentX = x1
                    currentY = y1
                }
                in VPath.quadBezierShortHandCommands -> {
                    x = scanner.nextFloat()
                    y = scanner.nextFloat()
                    if (x.isNaN() || y.isNaN()) {
                        SvgLogger.e("Could not parse command $currentCommand at index ${scanner.position} on ${scanner.current}")
                        break@pathParse
                    }

                    path.quadTo(lastControlX, lastControlY, x, y)
                    SvgLogger.d("ShortHand Quad Bezier at $currentX, $currentY to $x, $y with control $lastControlX, $lastControlY $relativeStr")

                    currentX = x
                    currentY = y
                }
                in VPath.closePathCommands -> {
                    path.lineTo(lastMoveX, lastMoveY)
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