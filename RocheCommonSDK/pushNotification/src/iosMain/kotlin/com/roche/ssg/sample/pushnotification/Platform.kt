package com.roche.ssg.sample.pushnotification

import platform.UIKit.UIDevice

actual fun getOS(): String = UIDevice.currentDevice.systemName
actual fun getOSVersion(): String = UIDevice.currentDevice.systemVersion
actual fun getDevice(): String = UIDevice.currentDevice.model
actual fun getMake(): String = "Apple"