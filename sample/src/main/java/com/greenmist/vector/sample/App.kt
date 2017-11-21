package com.greenmist.vector.sample

import android.app.Application
import android.util.Log
import com.greenmist.vector.logger.SvgLogger

/**
 * Created by geoffpowell on 11/19/17.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        SvgLogger.logLevel = Log.VERBOSE
    }
}