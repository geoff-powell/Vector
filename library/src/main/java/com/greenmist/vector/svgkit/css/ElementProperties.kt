package com.greenmist.vector.svgkit.css

import com.greenmist.vector.svgkit.css.rules.InlineRule
import java.util.*

/**
 * Created by geoffpowell on 11/17/17.
 */
class ElementProperties : Cloneable {

    private var result: InlineRule = InlineRule()
    private var pseudoElementTable: Hashtable<String, InlineRule>? = null

    constructor()

    constructor(other: ElementProperties) {
        this.result = other.result.cloneObject()
        if (other.pseudoElementTable != null) {
            pseudoElementTable = Hashtable()
            other.pseudoElementTable?.forEach {
                pseudoElementTable?.put(it.key, it.value)
            }
        }
    }

    fun pseudoElements(): Iterator<String>? = pseudoElementTable?.keys?.iterator()

    fun getPropertySet(): InlineRule = result

    fun getPropertySetForPseudoElement(pseudoElement: String): InlineRule {
        var r: InlineRule?
        if (pseudoElementTable == null) {
            pseudoElementTable = Hashtable()
            r = null
        } else {
            r = pseudoElementTable?.get(pseudoElement)
        }

        if (r == null) {
            r = InlineRule()
            pseudoElementTable?.put(pseudoElement, r)
        }
        return r
    }

    fun removePseudoElement(pseudoElement: String) {
        if (pseudoElementTable != null) {
            pseudoElementTable?.remove(pseudoElement)
            if (pseudoElementTable?.size == 0) {
                pseudoElementTable = null
            }
        }
    }

    fun isEmpty(): Boolean = pseudoElementTable?.size == 0 && result.isEmpty()

    fun cloneObject(): ElementProperties = ElementProperties(this)

    override fun equals(other: Any?): Boolean {
        if (other?.javaClass != javaClass) {
            return false
        }
        val otherElementProperties = other as ElementProperties?
        if (result != otherElementProperties?.result) {
            return false
        }
        val empty = pseudoElementTable?.size == 0
        val otherEmpty = otherElementProperties.pseudoElementTable?.size == 0
        if (empty && otherEmpty) {
            return true
        }
        return if (empty != otherEmpty) {
            false
        } else {
            pseudoElementTable == otherElementProperties.pseudoElementTable
        }
    }

    override fun hashCode(): Int {
        var code = result.hashCode()
        if (pseudoElementTable != null && pseudoElementTable?.size != 0)
            code += pseudoElementTable?.hashCode() ?: 0
        return code
    }
}