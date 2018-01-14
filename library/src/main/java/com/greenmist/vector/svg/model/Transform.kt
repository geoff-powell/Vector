package com.greenmist.vector.svg.model

import android.graphics.Matrix
import com.greenmist.vector.lib.svg.util.TextScanner
import com.greenmist.vector.svg.exception.SvgParseException

class Transform(val commands: ArrayList<Command> = ArrayList()) {

    constructor(transform: Transform) : this(ArrayList(transform.commands))

    fun getMatrix() : Matrix {
        var floatArray: FloatArray
        val matrix = Matrix()
        var angle: Double

        commands.forEach {
            floatArray = it.values.mapNotNull{ it }.toFloatArray()

            when (it.type) {
                CommandType.MATRIX -> matrix.mapPoints(floatArray)
                CommandType.TRANSLATE -> matrix.preTranslate(floatArray[0], it.values[1] ?: 0f)
                CommandType.ROTATE -> {
                    if (floatArray.size >= 3) {
                        matrix.preRotate(floatArray[0], floatArray[1], floatArray[2])
                    } else {
                        matrix.preRotate(floatArray[0])
                    }
                }
                CommandType.SCALE -> {
                    val scaleY = if (floatArray.size > 1) floatArray[1] else floatArray[0]
                    matrix.preScale(floatArray[0], scaleY)
                }
                CommandType.SKEWX -> {
                    angle = Math.toRadians(floatArray[0].toDouble())
                    matrix.preSkew(Math.tan(angle).toFloat(), 0f)
                }
                CommandType.SKEWY -> {
                    angle = Math.toRadians(floatArray[0].toDouble())
                    matrix.preSkew( 0f, Math.tan(angle).toFloat())
                }
            }
        }

        return matrix
    }

    companion object {

        fun fromString(commandStr: String) : Transform {
            val transform = Transform()
            val scanner = TextScanner(commandStr)

            var currentCommand: String?
            var commandType: CommandType?
            var requiredParams: Int
            var maxParams: Int
            var param: Float?
            var invalidParam: Boolean
            var command: Command

            if (commandStr.isNotBlank()) {
                while (scanner.hasNext()) {
                    currentCommand = scanner.nextString()

                    commandType = currentCommand?.toCommandType()
                    commandType?.let {
                        command = Command(commandType)
                        scanner.skip { it == '(' || it.isWhitespace() }

                        when(commandType) {
                            CommandType.MATRIX -> {
                                requiredParams = 6
                                maxParams = 6
                            }
                            CommandType.TRANSLATE, CommandType.SCALE -> {
                                requiredParams = 1
                                maxParams = 2
                            }
                            CommandType.ROTATE -> {
                                requiredParams = 1
                                maxParams = 3
                            }
                            CommandType.SKEWX, CommandType.SKEWY -> {
                                requiredParams = 1
                                maxParams = 1
                            }
                        }

                        for (i in 0 until maxParams) {
                            param = scanner.nextFloat()
                            invalidParam = param?.isNaN() ?: true

                            if (i <= requiredParams - 1 && invalidParam) {
                                throw SvgParseException("Could not parse float at ${scanner.position}")
                            } else if (i > requiredParams - 1 && invalidParam) {
                                param = null
                            }

                            command.values.add(param)
                        }
                        scanner.skip { it == ')' || it.isWhitespace() }

                        transform.commands.add(command)
                    } ?: throw SvgParseException("Could not parse transform command $commandType")
                }
            }

            return transform
        }
    }
}

fun String.toTransform() : Transform = Transform.fromString(this)