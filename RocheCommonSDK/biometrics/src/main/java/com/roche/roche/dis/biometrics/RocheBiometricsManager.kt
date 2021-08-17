package com.roche.roche.dis.biometrics

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.roche.roche.dis.biometrics.callback.OnAuthenticationCallback
import com.roche.roche.dis.biometrics.sensors.Face
import com.roche.roche.dis.biometrics.sensors.Fingerprint
import com.roche.roche.dis.biometrics.sensors.Iris

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

    private var isBiometricsDialogShowing = false

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
}