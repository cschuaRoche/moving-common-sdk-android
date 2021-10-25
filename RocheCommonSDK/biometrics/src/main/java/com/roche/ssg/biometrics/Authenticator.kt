package com.roche.ssg.biometrics

import androidx.biometric.BiometricManager

enum class Authenticator(val value: Int) {
    STRONG(BiometricManager.Authenticators.BIOMETRIC_STRONG),
    WEAK(BiometricManager.Authenticators.BIOMETRIC_WEAK)
}