package com.greenmist.vector.svgkit.css.matchers

import com.greenmist.vector.svgkit.attr.AttrMap
import com.greenmist.vector.svgkit.css.results.MatchResult
import com.greenmist.vector.svgkit.css.selectors.AttributeSelector

/**
 * Created by geoffpowell on 11/17/17.
 */
class AttributeElementMatcher(selector: AttributeSelector, val namespace: String?, val attr: String, val operation: String, val value: Any?) : ElementMatcher(selector) {

    override fun popElement() {}

    override fun pushElement(namespace: String?, name: String?, attrs: AttrMap?): MatchResult? {
        if (attrs == null) {
            return null
        }
        
        val currentValue = when {
            this.namespace != null -> attrs.get(this.namespace, attr) ?: return null
            else -> null
        }


        when {
            value == null -> return MatchResult.ALWAYS
            currentValue == null -> return null
            operation == "=" -> return if (value.toString() == currentValue.toString()) MatchResult.ALWAYS else null
            operation == "~=" -> return if (isInList(currentValue, value.toString())) MatchResult.ALWAYS else null
            operation == "|=" -> {
                val sv = currentValue.toString()
                val v = value.toString()
                return if (v == sv || sv.startsWith(v + "-")) MatchResult.ALWAYS else null
            }
            operation == "^=" -> {
                val sv = currentValue.toString()
                val v = value.toString()
                return if (sv.startsWith(v)) MatchResult.ALWAYS else null
            }
            operation == "$=" -> {
                val sv = currentValue.toString()
                val v = value.toString()
                return if (sv.endsWith(v)) MatchResult.ALWAYS else null
            }
            operation == "*=" -> {
                val sv = currentValue.toString()
                val v = value.toString()
                return if (sv.indexOf(v) >= 0) MatchResult.ALWAYS else null
            }
            else -> throw RuntimeException("Unknown operation: " + operation)
        }
    }

    companion object {

        fun isInList(list: Any, value: String): Boolean {
            val listString = list.toString()
            var index = listString.indexOf(' ')
            if (index < 0)
                return value == listString
            else {
                var prevIndex = 0
                val valueLength = value.length
                val listStringLength = listString.length
                while (true) {
                    if (index == prevIndex + valueLength && listString.regionMatches(prevIndex, value, 0, valueLength))
                        return true
                    prevIndex = index + 1
                    if (prevIndex > listStringLength)
                        return false
                    index = listString.indexOf(' ', prevIndex)
                    if (index < 0)
                        index = listStringLength
                }
            }
        }
    }
}