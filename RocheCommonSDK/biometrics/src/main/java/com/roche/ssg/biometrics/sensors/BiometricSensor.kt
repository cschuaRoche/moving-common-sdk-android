package com.roche.ssg.biometrics.sensors

import android.content.Context
import android.content.pm.PackageManager

internal abstract class BiometricSensor(val context: Context) {
    protected var packageManager: PackageManager = context.packageManager

    abstract fun isHardwareAvailable(): Boolean
}