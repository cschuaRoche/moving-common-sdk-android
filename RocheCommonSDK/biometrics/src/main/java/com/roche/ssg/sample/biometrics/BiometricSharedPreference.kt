package com.roche.ssg.sample.biometrics

import android.content.Context
import androidx.annotation.VisibleForTesting

internal object BiometricSharedPreference {
    const val BIOMETRICS_PREFS = "PATIENT_APP_USER_PREFS"
    @VisibleForTesting
    internal const val PREFS_BIOMETRICS_ENABLED = "key_biometrics"
    @VisibleForTesting
    internal const val PREFS_REGISTRATION_BIOMETRICS_COMPLETE = "key_biometrics_registration"

    fun setIsAppBiometricEnabled(context: Context, isBiometricsEnabled: Boolean) {
        val pref = PreferenceUtil.createOrGetPreference(context, BIOMETRICS_PREFS)
        pref.set(PREFS_BIOMETRICS_ENABLED, isBiometricsEnabled)
        // if true make sure registration is also flagged as true -
        // edge case when skipped in Registration
        if (isBiometricsEnabled) {
            setIsBiometricRegistrationComplete(context, isBiometricsEnabled)
        }
    }

    fun isAppBiometricEnabled(context: Context): Boolean {
        val pref = PreferenceUtil.createOrGetPreference(context, BIOMETRICS_PREFS)
        return pref.get(PREFS_BIOMETRICS_ENABLED, false) ?: false
    }

    fun setIsBiometricRegistrationComplete(context: Context, isBiometricsRegistrationComplete: Boolean) {
        val pref = PreferenceUtil.createOrGetPreference(context, BIOMETRICS_PREFS)
        pref.set(PREFS_REGISTRATION_BIOMETRICS_COMPLETE, isBiometricsRegistrationComplete)
    }

    fun isBiometricRegistrationComplete(context: Context): Boolean {
        val pref = PreferenceUtil.createOrGetPreference(context, BIOMETRICS_PREFS)
        return pref.get(PREFS_REGISTRATION_BIOMETRICS_COMPLETE, false) ?: false
    }
}