package com.greenmist.vector.svgkit.svg.parser

/**
 * Created by geoffpowell on 11/18/17.
 */
open class TextSpan {

    var parent: TextSpan? = null
    var textProperties: TextProperties? = null
    private var text: String? = null

    constructor()

    constructor(textProperties: TextProperties) : this() {
        this.textProperties = textProperties
    }

    open fun setText(text: String?) {
        var str = text
        str = str?.replace("\\s+".toRegex(), " ")
        str = str?.replace("(\\s)\\1".toRegex(), " ")
        this.text = str
    }

    fun getText(): String? = text

    fun asString(): String = "${textProperties?.x}, ${textProperties?.y} ${textProperties?.fillColor}: $text"
}