package com.greenmist.vector.svg.model

enum class CommandType {
    MATRIX,
    TRANSLATE,
    ROTATE,
    SCALE,
    SKEWX,
    SKEWY;

    companion object {
        fun fromString(value: String) : CommandType? {
            return values().firstOrNull {
                it.name.equals(value, true)
            }
        }
    }
}

fun String.toCommandType() : CommandType? {
    return CommandType.fromString(this)
}