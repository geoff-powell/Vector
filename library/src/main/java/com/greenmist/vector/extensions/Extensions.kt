package com.greenmist.vector.extensions

/**
 * Created by geoffpowell on 11/17/17.
 */
fun CharSequence?.isNotNullOrBlank(): Boolean = this != null && this.isNotBlank()

inline fun String.isNotBlank(block: (String) -> Unit) = when {
    this.isNotBlank() -> block(this)
    else -> {}
}

fun Float.clamp(min: Float, max: Float): Float = Math.max(min, Math.min(this, max))