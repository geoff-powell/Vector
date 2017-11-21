package com.greenmist.vector.logger

import android.util.Log

/**
 * Created by geoffpowell on 11/18/17.
 */
object SvgLogger {

    var logLevel: Int = Log.ERROR

    fun i(text: String) {
        checkLevel(Log.INFO) {
            Log.i(javaClass.simpleName, text)
        }
    }

    fun v(text: String) {
        checkLevel(Log.VERBOSE) {
            Log.v(javaClass.simpleName, text)
        }
    }

    fun d(text: String) {
        checkLevel(Log.DEBUG) {
            Log.d(javaClass.simpleName, text)
        }
    }

    fun d(text: String, throwable: Throwable) {
        checkLevel(Log.DEBUG) {
            Log.d(javaClass.simpleName, text, throwable)
        }
    }

    fun w(text: String) {
        checkLevel(Log.WARN) {
            Log.w(javaClass.simpleName, text)
        }
    }

    fun e(text: String) {
        checkLevel(Log.ERROR) {
            Log.e(javaClass.simpleName, text)
        }
    }


    fun e(throwable: Throwable, text: String) {
        checkLevel(Log.ERROR) {
            Log.e(javaClass.simpleName, text, throwable)
        }
    }

    private fun checkLevel(logLevel: Int, block: () -> Unit) {
        if (logLevel >= this.logLevel) {
            block()
        }
    }
}