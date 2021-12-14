package com.roche.ssg.pushnotification

import platform.UIKit.UIDevice

actual fun getOS(): String = UIDevice.currentDevice.systemName
actual fun getOSVersion(): String? = UIDevice.currentDevice.systemVersion
actual fun getDevice(): String? = UIDevice.currentDevice.name
actual fun getMake(): String? = "Apple"