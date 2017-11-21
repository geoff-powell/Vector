package com.greenmist.vector.svgkit.css.results

import com.greenmist.vector.svgkit.css.ElementProperties
import java.util.*

/**
 * Created by geoffpowell on 11/17/17.
 */
class CascadeResult {
    private var properties: ElementProperties = ElementProperties()
    private var mediaTable: Hashtable<String, ElementProperties>? = null

    constructor(mediaTable: Hashtable<String, ElementProperties>? = null) {
        this.mediaTable = mediaTable
    }

    constructor(other: CascadeResult) {
        properties = other.properties.cloneObject()

        if (other.mediaTable != null) {
            mediaTable = Hashtable()
            other.mediaTable?.forEach {
                mediaTable?.put(it.key, it.value.cloneObject())
            }
        }
    }

    fun media(): Iterator<String>? {
        return if (mediaTable == null) {
            null
        } else {
            mediaTable?.keys?.iterator()
        }
    }

    fun getProperties(): ElementProperties = properties

    fun getPropertiesForMedia(media: String?): ElementProperties {
        var r: ElementProperties?
        if (mediaTable == null) {
            mediaTable = Hashtable()
            r = null
        } else {
            r = mediaTable?.get(media)
        }

        if (r == null) {
            r = ElementProperties()
            mediaTable?.put(media, r)
        }
        return r
    }

    fun removeMedia(media: String) {
        if (mediaTable != null) {
            mediaTable?.remove(media)
            if (mediaTable?.size == 0)
                mediaTable = null
        }
    }

    fun isEmpty(): Boolean = mediaTable?.isEmpty == true && properties.isEmpty()

    fun cloneObject(): CascadeResult = CascadeResult(this)
}