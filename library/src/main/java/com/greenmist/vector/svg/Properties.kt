package com.greenmist.vector.lib.svg

import org.xml.sax.Attributes

/**
 * Created by geoff.powell on 11/27/17.
 */
class Properties(attributes: Attributes?) {

    private val propMap = HashMap<String, String>()

    init {
        attributes?.let {
            for (i in 0 until attributes.length) {
                attributes.getQName(i)?.let {
                    propMap.put(attributes.getQName(i), attributes.getValue(i))
                } ?: propMap.put(attributes.getLocalName(i), attributes.getValue(i))
            }
        }
    }

    operator fun get(key: String) = propMap[key]

    operator fun get(qualifiedName: String, key: String): String? {
        return propMap["$qualifiedName:$key"]
    }

    fun set(key: String, value: String) {
        propMap.put(key, value)
    }
}