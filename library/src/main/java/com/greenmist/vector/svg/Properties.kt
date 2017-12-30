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
                propMap.put(attributes.getLocalName(i), attributes.getValue(i))
            }
        }
    }

    operator fun get(key: String) = propMap[key]

    fun set(key: String, value: String) {
        propMap.put(key, value)
    }
}