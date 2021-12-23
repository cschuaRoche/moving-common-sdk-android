package com.roche.ssg.sample

import android.app.Application
import com.amplitude.api.Amplitude
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SsgApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initAmplitude()
        logEvent("app_open")
    }

    private fun initAmplitude() {
        try {
            Amplitude.getInstance()
                .initialize(applicationContext, "73da6cc77f300042541bbbbb8cf31ae1")
                .enableForegroundTracking(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun logEvent(event: String) {
        try {
            val client = Amplitude.getInstance()
            client.userId = null
            client.logEvent(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}