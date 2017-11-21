package com.greenmist.vector.svgkit.attr

import org.xml.sax.Attributes

/**
 * Created by geoffpowell on 11/17/17.
 */
class AttrMapAttributeAdapter(private val attributes: Attributes) : AttrMap {

    override fun get(namespace: String, name: String): Any? = attributes.getValue(namespace, name)

    override val iterator: AttrMapIterator = object : AttrMapIterator {

        var index = 0

        override fun hasItem(): Boolean = index < attributes.getLength()

        override fun nextItem() {
            index++
        }
        override fun getNamespace() = attributes.getURI(index)
        override fun getName() = attributes.getLocalName(index)
        override fun getValue() = attributes.getValue(index)
    }
}