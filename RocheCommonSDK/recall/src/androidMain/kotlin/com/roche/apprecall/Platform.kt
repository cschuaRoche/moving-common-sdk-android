package com.roche.apprecall

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun initLogger() {
    Napier.base(DebugAntilog())
}

actual fun getOS() = "Android"
actual fun getOSVersion() = android.os.Build.VERSION.SDK_INT.toString()
actual fun getDevice() = android.os.Build.MODEL