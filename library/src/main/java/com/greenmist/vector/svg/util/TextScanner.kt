package com.greenmist.vector.lib.svg.util

/**
 * Created by geoff.powell on 12/16/17.
 */
class TextScanner(input: CharSequence)  {

    val input: CharSequence = input.trim()
    var position: Int = 0
    val length: Int = this.input.length

    val current: Char
        get() = if (position < length) {
                input[position]
            } else {
                '\u0000'
            }

    fun hasNext() = position < length
    
    fun next() {
        if (position < length) ++position
    }

    fun skip(skipFunc: (Char) -> Boolean) {
        while (hasNext()) {
            if (skipFunc(current) == false) break
            next()
        }
    }

    fun skipWhitespace() {
        skip { it.isWhitespace() }
    }

    fun skipNumberSeparator() {
        skip { it == ',' || it.isWhitespace() }
    }

    private fun parseNumber() : Float? {
        var num: Float? = null
        while (hasNext()) {
            if (current.isDigit() == false) break
            num = num ?: 0f
            num = num * 10 + (current.toInt() - '0'.toInt()) % 10
            next()
        }
        
        return num
    }

    private fun parseString() : String? {
        var string: String? = null
        while (hasNext()) {
            if (current.isLetter() == false) break
            string = string ?: ""
            string += current
            next()
        }

        return string
    }

    /**
     * Parse a SVG 'number' or a CSS 'number' from a String.
     *
     * We use our own parser because the one in Android (from Harmony I think) is slow.
     *
     * An SVG 'number' is defined as
     *   integer ([Ee] integer)?
     *   | [+-]? [0-9]* "." [0-9]+ ([Ee] integer)?
     * Where 'integer' is
     *   [+-]? [0-9]+
     * CSS numbers were different, but have now been updated to a compatible definition (see 2.1 Errata)
     *   [+-]?([0-9]+|[0-9]*\.[0-9]+)(e[+-]?[0-9]+)?
     *
     */

    private fun parseFloat(): Float {
        var mant: Float
        val mantDig: Float
        var mantPos = true
        var expPos = true

        val startPos: Int
        val endPos: Int

        /* -12.24e-2 */
        if (current == '-') mantPos = false
        if (current == '+' || current == '-') next()

        mant = parseNumber() ?: return Float.NaN

        if (current == '.') {
            startPos = position
            next()

            mantDig = parseNumber() ?: return Float.NaN
            endPos = position - 1
            if (startPos != endPos)
                mant += (mantDig / pow10[endPos - startPos]).toFloat()
        }
        if (!mantPos) mant = -mant

        if (current == 'e' || current == 'E') {
            next()
            if (current == '-') expPos = false
            if (current == '-' || current == '+') next()

            var exp = parseNumber()?.toInt() ?: return Float.NaN
            if (!expPos) exp = -exp

            if (exp < -125 || mant == 0f) return 0.0f
            if (exp >= 128) return if (mant > 0) Float.POSITIVE_INFINITY else Float.NEGATIVE_INFINITY
            if (exp == 0) return mant

            mant = (if (exp > 0) mant * pow10[exp] else mant / pow10[-exp]).toFloat()
        }
        return mant
    }

    private fun <T> possibleNext(nextFunc: () -> T) : T {
        val oldPosition = position
        val next = nextFunc()
        position = oldPosition

        return next
    }

    fun nextFloat() : Float {
        skipWhitespace()
        val f = parseFloat()
        skipNumberSeparator()
        return f
    }

    fun nextString() : String? {
        skipWhitespace()
        val s = parseString()
        skipNumberSeparator()
        return s
    }

    fun possibleNextFloat() : Float? {
        val value = possibleNext(this::nextFloat)
        return if (value.isNaN()) null else value
    }

    companion object {
        private val pow10 = DoubleArray(128, { Math.pow(10.0, it.toDouble()) })
    }
}