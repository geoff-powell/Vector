package com.greenmist.vector.svgkit.svg.properties

import java.util.*

/**
 * Created by geoffpowell on 11/18/17.
 */
class Numbers(val numbers: ArrayList<Float>?, val nextCmd: Int) {

    val numberArray: FloatArray = numbers?.toFloatArray() ?: FloatArray(0)

    fun get(index: Int): Float? = numbers?.get(index)

    companion object {

        fun parseNumbers(s: String): Numbers {
            val n = s.length
            var p = 0
            val numbers = ArrayList<Float>()
            var skipChar = false

            for (i in 1 until n) {
                if (skipChar) {
                    skipChar = false
                    continue
                }
                val c = s[i]
                when (c) {
                    'e' -> skipChar = true
                    'M', 'm', 'Z', 'z', 'L', 'l', 'H', 'h', 'V', 'v', 'C', 'c', 'S', 's', 'Q', 'q', 'T', 't', 'a', 'A', ')' -> {
                        val str = s.substring(p, i)
                        if (str.trim().isNotEmpty()) {
                            //Util.debug("  Last: " + str);
                            val f = str.toFloat()
                            numbers.add(f)
                        }
                        p = i
                        return Numbers(numbers, p)
                    }
                    '\n', '\t', ' ', ',', '-' -> {
                        val str = s.substring(p, i)
                        if (str.trim().isNotEmpty()) {
                            val f = str.toFloat()
                            numbers.add(f)
                            if (c == '-') {
                                p = i
                            } else {
                                p = i + 1
                                skipChar = true
                            }
                        } else {
                            p++
                        }
                    }
                }
            }
            val last = s.substring(p)
            if (last.isNotEmpty()) {
                //Util.debug("  Last: " + last);
                try {
                    numbers.add(last.toFloat())
                } catch (nfe: NumberFormatException) {
                    // Just white-space, forget it
                }

                p = s.length
            }
            return Numbers(numbers, p)
        }
    }
}