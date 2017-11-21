package com.greenmist.vector.svgkit.attr

/**
 * Created by geoffpowell on 11/17/17.
 */
interface AttrMapIterator {

    fun hasItem(): Boolean

    fun nextItem()

    fun getNamespace(): String?

    fun getName(): String

    fun getValue(): Any?

}