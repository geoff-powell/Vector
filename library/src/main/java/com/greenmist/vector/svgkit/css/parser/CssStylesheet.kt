package com.greenmist.vector.svgkit.css.parser

import com.greenmist.vector.svgkit.css.rules.BaseRule
import com.greenmist.vector.svgkit.css.rules.SelectorRule
import java.util.*

/**
 * Created by geoffpowell on 11/17/17.
 */
class CssStylesheet {

    val statements = Vector<BaseRule>()
    val rulesBySelector = Hashtable<Any, BaseRule>()

    fun add(rule: BaseRule) {
        if (rule is SelectorRule) {

        }
    }
}