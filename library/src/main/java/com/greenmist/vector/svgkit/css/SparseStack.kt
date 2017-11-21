package com.greenmist.vector.svgkit.css

import java.util.*

/**
 * Created by geoffpowell on 11/17/17.
 */
class SparseStack {

    val stack: Stack<Entry> = Stack()

    constructor() {
        stack.push(Entry())
    }

    class Entry(val content: Any? = null) {
        var depth: Int = 0
    }

    fun push(obj: Any?) {
        when (obj) {
            null -> (stack.peek() as Entry).depth++
            else -> stack.push(Entry(obj))
        }
    }

    fun pop(): Any? {
        val top = stack.peek() as Entry
        if (top.depth == 0) {
            stack.pop()
            if (top.content == null) {
                throw RuntimeException("stack underflow")
            }
            return top.content
        }
        top.depth--
        return null
    }
}