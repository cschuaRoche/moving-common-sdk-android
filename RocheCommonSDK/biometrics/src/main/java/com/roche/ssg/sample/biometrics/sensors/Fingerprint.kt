package com.roche.ssg.sample.biometrics.sensors

import android.content.Context
import android.content.pm.PackageManager

internal class Fingerprint(context: Context) : BiometricSensor(context) {
    override fun isHardwareAvailable() =
        packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
}