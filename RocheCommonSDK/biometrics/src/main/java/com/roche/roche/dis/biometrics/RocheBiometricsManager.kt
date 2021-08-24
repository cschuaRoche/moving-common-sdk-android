package com.roche.roche.dis.biometrics

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.VisibleForTesting
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.roche.roche.dis.biometrics.callback.OnAuthenticationCallback
import com.roche.roche.dis.biometrics.sensors.Face
import com.roche.roche.dis.biometrics.sensors.Fingerprint
import com.roche.roche.dis.biometrics.sensors.Iris
import java.util.Locale

/**
 * Interacts with the Biometrics API and displays the Biometrics prompt on top of a Fragment.
 *
 * This will automatically detect which biometrics is available or NONE if both are unavailable.
 * @see "https://developer.android.com/reference/androidx/biometric/BiometricPrompt.PromptInfo.Builder#setAllowedAuthenticators(int)"
 * @param context the application context where the prompt dialog will be shown
 * @param allowedAuthenticators authenticator types - com.roche.roche.dis.biometrics.Authenticator
 */
class RocheBiometricsManager(
    private val context: Context,
    private var allowedAuthenticators: Authenticator
) {
    private val biometricsDialogs: BiometricsDialogs =
        BiometricsDialogs(allowedAuthenticators.value)

    var type: BiometricsType

    var isBiometricsDialogShowing = false

    @Deprecated("", ReplaceWith("isBiometricsEnrolled()"), DeprecationLevel.WARNING)
    var isAvailable = isBiometricsEnrolled()

    init {
        type = when {
            hasMultipleBiometrics() -> {
                BiometricsType.MULTIPLE
            }
            isFaceSupported() -> {
                BiometricsType.FINGERPRINT
            }
            isFaceSupported() -> {
                BiometricsType.FACE_UNLOCK
            }
            isIrisSupported() -> {
                BiometricsType.IRIS
            }
            else -> {
                BiometricsType.NONE
            }
        }
    }

    /**
     * Verifies whether the device has biometric hardware to perform authentication.
     * @return true if either Fingerprint or Face or Iris hardware is available, otherwise false
     */
    fun isBiometricSupported() = isFingerprintSupported() || isFaceSupported() || isIrisSupported()

    /**
     * Verifies whether the device has biometric hardware to perform Fingerprint authentication.
     * @return true if Fingerprint hardware is available, otherwise false
     */
    fun isFingerprintSupported() = Fingerprint(context).isHardwareAvailable()

    /**
     * Verifies whether the device has biometric hardware to perform Face authentication.
     * @return true if Face hardware is available, otherwise false
     */
    fun isFaceSupported() = Face(context).isHardwareAvailable()

    /**
     * Verifies whether the device has biometric hardware to perform IRIS authentication.
     * @return true if IRIS hardware is available, otherwise false
     */
    fun isIrisSupported() = Iris(context).isHardwareAvailable()


    /**
     * verifies whether user has setup their biometrics
     * @return true if biometrics is setup on the device, otherwise false
     */
    fun isBiometricsEnrolled(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(allowedAuthenticators.value) == BiometricManager.BIOMETRIC_SUCCESS
    }

    /**
     * Launch the setting app to enroll the Biometric
     */
    fun enrollBiometric() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.startActivity(Intent(Settings.ACTION_BIOMETRIC_ENROLL))
        } else {
            context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
        }
    }

    /**
     * Shows a system biometric dialog for authentication
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     * @param title Title of the Biometric Dialog
     * @param description Description of the Biometric Dialog
     * @param negativeButtonText Negative button text of Biometric Dialog
     */
    fun showAuthDialog(
        activity: FragmentActivity,
        callback: OnAuthenticationCallback,
        title: String = context.getString(R.string.biometric_auth_title),
        description: String = context.getString(R.string.biometric_auth_desc),
        negativeButtonText: String = context.getString(R.string.cancel)
    ) {
        biometricsDialogs.showAuthDialog(activity, callback, title, description, negativeButtonText)
        isBiometricsDialogShowing = true
    }

    /**
     * Shows a system biometric dialog for authentication
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     * @param title Title of the Biometric Dialog
     * @param description Description of the Biometric Dialog
     * @param negativeButtonText Negative button text of Biometric Dialog
     */
    fun showAuthDialog(
        fragment: Fragment,
        callback: OnAuthenticationCallback,
        title: String = context.getString(R.string.biometric_auth_title),
        description: String = context.getString(R.string.biometric_auth_desc),
        negativeButtonText: String = context.getString(R.string.cancel)
    ) {
        biometricsDialogs.showAuthDialog(fragment, callback, title, description, negativeButtonText)
        isBiometricsDialogShowing = true
    }

    /**
     * Dismiss the current Biometrics Dialog on screen.
     */
    fun dismissBiometricDialog() {
        isBiometricsDialogShowing = false
        biometricsDialogs.dismissBiometricDialog()
    }

    /**
     * verifies whether the user's biometric or device credential is not enrolled.
     * @return true if biometrics is not yet setup on the device and the user can set one up now, otherwise false
     * Note: if hardware is not supported, this will return false
     */
    fun canSetupBiometrics(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(allowedAuthenticators.value) == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED

    }

    /**
     * verifies whether fingerprint is available or can be setup in the device settings
     * @return true if fingerprint is available or can be setup in the device settings
     */
    fun hasFingerprintSetup(): Boolean {
        return BiometricsType.FINGERPRINT == type && (isBiometricsEnrolled() || canSetupBiometrics())
    }

    /**
     * verifies whether face unlock is available or can be setup in the device settings
     * @return true if face unlock is available or can be setup in the device settings
     */
    fun hasFaceUnlockSetup(): Boolean {
        return BiometricsType.FACE_UNLOCK == type && (isBiometricsEnrolled() || canSetupBiometrics())
    }

    /**
     * verifies whether iris is available or can be setup in the device settings
     * @return true if iris is available or can be setup in the device settings
     */
    fun hasIrisSetup(): Boolean {
        return BiometricsType.IRIS == type && (isBiometricsEnrolled() || canSetupBiometrics())
    }

    /**
     * verifies whether multiple biometrics is available or can be setup in the device settings
     * @return true if multiple biometrics is available or can be setup in the device settings
     */
    fun hasMultipleBiometricsSetup(): Boolean {
        return BiometricsType.MULTIPLE == type && (isAvailable || canSetupBiometrics())
    }


    /**
     * verifies whether multiple biometrics are supported in the device
     * @return true if multiple biometrics are supported, otherwise false if only one or None
     */
    fun hasMultipleBiometrics(): Boolean {
        if (isNotSupportedDevice()) {
            // For all other manufacturers use multiple biometric.  Android framework cannot
            // accurately determine which ones are available on the the system settings.
            return true
        }
        var count = 0
        if (isFingerprintSupported()) count++
        if (isFaceSupported()) count++
        if (isIrisSupported()) count++
        return count > 1
    }

    @VisibleForTesting
    internal fun isNotSupportedDevice(): Boolean {
        return !Build.MANUFACTURER.toUpperCase(Locale.getDefault()).contains("GOOGLE")
    }

}

enum class BiometricsType {
    FINGERPRINT,
    FACE_UNLOCK,
    IRIS,
    MULTIPLE,
    NONE
}