package com.roche.apprecall

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import platform.UIKit.UIDevice

actual fun initLogger() {
    Napier.base(DebugAntilog())
}

actual fun getOS(): String = UIDevice.currentDevice.systemName
actual fun getOSVersion(): String = UIDevice.currentDevice.systemVersion
actual fun getDevice():String = UIDevice.currentDevice.model