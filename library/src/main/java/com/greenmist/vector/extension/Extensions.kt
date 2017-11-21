package com.greenmist.vector.extension

/**
 * Created by geoffpowell on 11/17/17.
 */
fun CharSequence?.isNotNullOrBlank(): Boolean = this != null && this.isNotBlank()