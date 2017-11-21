package com.greenmist.vector.svgkit.svg.parser

import java.util.*

/**
 * Created by geoffpowell on 11/18/17.
 */
class Text : TextSpan {
    var spans = ArrayList<TextSpan>()
    private val currentSpans = Stack<TextSpan>()

    constructor(textProperties: TextProperties) : super(textProperties) {
        spans.add(this)
    }

    fun addSpan(pp: TextProperties) {
        val currentSpan = TextSpan(pp)
        spans.add(currentSpan)
        currentSpans.push(currentSpan)
        currentSpan.parent = this
    }

    fun endSpan() {
        currentSpans.pop()
    }

    override fun setText(text: String?) {
        if (text?.trim().isNullOrEmpty()) {
            return
        }
        if (currentSpans.size > 0 && currentSpans.peek().getText() == null) {
            currentSpans.peek().setText(text)
        } else {
            if (spans.size > 1) {
                textProperties?.let {
                    addSpan(it.cloneStyle())
                }
                setText(text)
            } else {
                super.setText(text)
            }
        }
    }

    override fun toString(): String {
        var result = ""
        spans.forEach {
            result += it.asString() + "\n"
        }
        return "[" + result.substring(0, result.length - 1) + "]"
    }
}