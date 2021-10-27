package com.roche.ssg.pushnotification

actual fun getOS() = "Android"
actual fun getOSVersion() = android.os.Build.VERSION.SDK_INT.toString()
actual fun getDevice(): String = android.os.Build.MODEL
actual fun getMake(): String = android.os.Build.MANUFACTURER