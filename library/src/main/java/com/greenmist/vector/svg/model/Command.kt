package com.greenmist.vector.svg.model

open class Command(
        val type: CommandType,
        val values: ArrayList<Float?> = ArrayList()
) {

    constructor(command: Command) : this(command.type, ArrayList(command.values))
}