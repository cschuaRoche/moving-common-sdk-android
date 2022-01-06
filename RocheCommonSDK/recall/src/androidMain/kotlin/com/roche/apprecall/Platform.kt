package com.roche.apprecall


actual fun getOS() = "Android"
actual fun getOSVersion() = android.os.Build.VERSION.SDK_INT.toString()
actual fun getDevice() = android.os.Build.MODEL