package com.greenmist.vector.svgkit.attr

import com.greenmist.vector.extension.isNotNullOrBlank
import java.util.*

/**
 * Created by geoffpowell on 11/17/17.
 */
class AttrMapImpl : AttrMap {

    private var table: Hashtable<Any, Any> = Hashtable()

    constructor() {
        iterator = AttrMapIteratorImpl()
    }

    constructor(mapToClone: AttrMap) {
        if (mapToClone is AttrMapImpl) {
            table = mapToClone.table.clone() as Hashtable<Any, Any>
        } else {
            table = Hashtable()
            iterator = AttrMapIteratorImpl()
            while (iterator.hasItem()) {
                put(iterator.getNamespace(), iterator.getName(), iterator.getValue())
                iterator.nextItem()
            }
        }
    }

    private fun getKey(namespace: String?, name: String) : Any? {
        var key: Any? = null
        if (namespace.isNotNullOrBlank()) {
            key = name
        } else if (namespace != null) {
            key = AttrName(namespace, name)
        }

        return key
    }

    override fun get(namespace: String, name: String) : Any? = table[getKey(namespace, name)]

    fun put(namespace: String?, name: String, value: Any?) {
        val key = getKey(namespace, name) ?: return
        table.put(key, value) ?: table.remove(key)
    }

    override lateinit var iterator: AttrMapIterator

    fun cloneMap() : AttrMapImpl = AttrMapImpl()

    inner class AttrMapIteratorImpl : AttrMapIterator {

        val keys = table.keys()
        var current: Any? = null

        override fun hasItem(): Boolean = current != null

        override fun nextItem() {
            if (keys.hasMoreElements()) {
                current = keys.nextElement()
            } else {
                current = null
            }
        }

        override fun getNamespace(): String? {
            return if (current is AttrName) {
                (current as AttrName).namespace
            } else {
                null
            }
        }

        override fun getName(): String {
            return if (current is AttrName) {
                (current as AttrName).name
            } else {
                current as String
            }
        }

        override fun getValue(): Any? = table[current]
    }
}