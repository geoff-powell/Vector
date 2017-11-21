package com.greenmist.vector.svgkit.attr

/**
 * Created by geoffpowell on 11/17/17.
 */
interface AttrMap {

    fun get(namespace: String, name: String): Any?

    val iterator: AttrMapIterator

}