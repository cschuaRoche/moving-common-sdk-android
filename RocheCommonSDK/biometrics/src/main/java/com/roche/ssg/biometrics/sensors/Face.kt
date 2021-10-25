package com.roche.ssg.biometrics.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

internal class Face(context: Context) : BiometricSensor(context) {
    override fun isHardwareAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            packageManager.hasSystemFeature(PackageManager.FEATURE_FACE)
        } else {
            false
        }
    }
}