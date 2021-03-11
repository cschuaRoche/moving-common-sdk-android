package com.roche.roche.dis.biometrics

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.roche.roche.dis.biometrics.OnAuthenticationCallback.BiometricStatusConstants
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Interacts with the Biometrics API and displays the Biometrics prompt on top of a Fragment.
 *
 * This will automatically detect which biometrics is available or NONE if both are unavailable.
 * @see BiometricsType
 * @see "https://developer.android.com/reference/androidx/biometric/BiometricPrompt.PromptInfo.Builder#setAllowedAuthenticators(int)"
 * @param context the application context where the prompt dialog will be shown
 * @param allowedAuthenticators authenticator types - BiometricManager.Authenticators
 */
class RocheBiometricsManager(
    private val context: Context,
    private var allowedAuthenticators: Int?
) {
    var type: BiometricsType
        private set

    /**
     * Determines if biometrics have been setup and ready to be used.
     * Note that in some devices, Biometrics is set to unknown due to security updates.
     * Check the RocheBiometricsManager.type to determine the supported hardware.
     */
    var isAvailable: Boolean
        private set

    var isNotEnrolled: Boolean
        private set

    var isBiometricsDialogShowing: Boolean
        private set

    private val biometricsDialogs: BiometricsDialogs

    init {
        if (allowedAuthenticators == null) {
            allowedAuthenticators = BiometricManager.Authenticators.BIOMETRIC_WEAK
        }
        type = when {
            hasMultipleBiometrics(context) -> {
                BiometricsType.MULTIPLE
            }
            hasFingerPrint(context) -> {
                BiometricsType.FINGERPRINT
            }
            hasFaceIrisAPISupport() -> {
                when {
                    hasFaceUnlock(context) -> {
                        BiometricsType.FACE_UNLOCK
                    }
                    hasIrisUnlock(context) -> {
                        BiometricsType.IRIS
                    }
                    else -> {
                        BiometricsType.NONE
                    }
                }
            }
            else -> {
                BiometricsType.NONE
            }
        }
        isAvailable = isBiometricsAvailable()
        isNotEnrolled = isBiometricsNotEnrolled()
        isBiometricsDialogShowing = false
        biometricsDialogs = BiometricsDialogs(allowedAuthenticators!!, type)
    }

    /**
     * Shows a system dialog to confirm use of biometrics
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showConfirmationDialog(fragment: Fragment, callback: OnAuthenticationCallback) {
        biometricsDialogs.showConfirmationDialog(fragment, callback)
        isBiometricsDialogShowing = true
    }

    /**
     * Shows a system biometric dialog for authentication
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showAuthDialog(fragment: Fragment, callback: OnAuthenticationCallback) {
        biometricsDialogs.showAuthDialog(fragment, callback)
        isBiometricsDialogShowing = true
    }

    /**
     * Shows a system dialog to confirm use of biometrics
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showConfirmationDialog(activity: FragmentActivity, callback: OnAuthenticationCallback) {
        biometricsDialogs.showConfirmationDialog(activity, callback)
        isBiometricsDialogShowing = true
    }

    /**
     * Shows a system biometric dialog for authentication
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showAuthDialog(activity: FragmentActivity, callback: OnAuthenticationCallback) {
        biometricsDialogs.showAuthDialog(activity, callback)
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
     * verifies whether fingerprint is available or can be setup in the device settings
     * @return true if fingerprint is available or can be setup in the device settings
     */
    fun hasFingerprintSetup(): Boolean {
        return BiometricsType.FINGERPRINT == type && (isAvailable || isNotEnrolled)
    }

    /**
     * verifies whether face unlock is available or can be setup in the device settings
     * @return true if face unlock is available or can be setup in the device settings
     */
    fun hasFaceUnlockSetup(): Boolean {
        return BiometricsType.FACE_UNLOCK == type && (isAvailable || isNotEnrolled)
    }

    /**
     * verifies whether iris is available or can be setup in the device settings
     * @return true if iris is available or can be setup in the device settings
     */
    fun hasIrisSetup(): Boolean {
        return BiometricsType.IRIS == type && (isAvailable || isNotEnrolled)
    }

    /**
     * verifies whether multiple biometrics is available or can be setup in the device settings
     * @return true if multiple biometrics is available or can be setup in the device settings
     */
    fun hasMultipleBiometricsSetup(): Boolean {
        return BiometricsType.MULTIPLE == type && (isAvailable || isNotEnrolled)
    }

    /**
     * verifies whether user has setup their biometrics
     * @return true if biometrics is setup on the device, otherwise false
     */
    private fun isBiometricsAvailable(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(allowedAuthenticators!!) == BiometricManager.BIOMETRIC_SUCCESS
    }

    /**
     * verifies whether the user's biometric or device credential is not enrolled.
     * @return true if biometrics is not yet setup on the device and the user can set one up now, otherwise false
     * Note: if hardware is not supported, this will return false
     */
    private fun isBiometricsNotEnrolled(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(allowedAuthenticators!!) == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
    }

    /**
     * Verifies whether the app should show the biometrics registration flow.
     * returns true if biometrics registration should be displayed, otherwise don't show
     */
    fun shouldShowRegisterBiometric(): Boolean {
        return (!BiometricSharedPreference.isBiometricRegistrationComplete(context) &&
                canShowBiometric())
    }

    /**
     * Verifies whether the app should show the biometrics authentication flow.
     * returns true if biometrics authentication should be displayed, otherwise don't show
     */
    fun shouldShowAuthBiometric(): Boolean {
        return isAppBiometricEnabled(context) &&
                canShowBiometric()
    }

    /**
     * Verifies whether the device can authenticate with biometrics.
     * returns true if the device has setup biometric or can setup a biometrics, otherwise false
     */
    private fun canShowBiometric(): Boolean {
        val biometricManager = BiometricManager.from(context)
        val status = biometricManager.canAuthenticate(allowedAuthenticators!!)
        return status == BiometricManager.BIOMETRIC_SUCCESS ||
                status == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ||
                status == BiometricManager.BIOMETRIC_STATUS_UNKNOWN
    }

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal fun hasFaceIrisAPISupport(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        }

        /**
         * verifies whether fingerprint is supported in the device
         * @return true if fingerprint is supported, otherwise false
         */
        fun hasFingerPrint(context: Context): Boolean {
            val packageManager: PackageManager = context.packageManager
            return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
        }

        /**
         * verifies whether face unlock is supported in the device
         * @return true if face unlock is supported, otherwise false
         */
        @RequiresApi(Build.VERSION_CODES.Q)
        fun hasFaceUnlock(context: Context): Boolean {
            val packageManager: PackageManager = context.packageManager
            return packageManager.hasSystemFeature(PackageManager.FEATURE_FACE)
        }

        /**
         * verifies whether face unlock is supported in the device
         * @return true if face unlock is supported, otherwise false
         */
        @RequiresApi(Build.VERSION_CODES.Q)
        fun hasIrisUnlock(context: Context): Boolean {
            val packageManager: PackageManager = context.packageManager
            return packageManager.hasSystemFeature(PackageManager.FEATURE_IRIS)
        }

        /**
         * verifies whether multiple biometrics are supported in the device
         * @return true if multiple biometrics are supported, otherwise false if only one or None
         */
        fun hasMultipleBiometrics(context: Context): Boolean {
            if (isNotSupportedDevice()) {
                // For all other manufacturers use multiple biometric.  Android framework cannot
                // accurately determine which ones are available on the the system settings.
                return true
            }
            var count = 0
            if (hasFingerPrint(context)) count++
            if (hasFaceIrisAPISupport()) {
                if (hasFaceUnlock(context)) count++
                if (hasIrisUnlock(context)) count++
            }
            return count > 1
        }

        /**
         * Sets the flag for isAppBiometricsEnabled.  Typically used in a user settings to enable
         * or disable biometrics in the application.
         * @param context the application context
         * @param isBiometricsEnabled sets flag to true or false
         */
        fun setIsAppBiometricEnabled(context: Context, isBiometricsEnabled: Boolean) {
            BiometricSharedPreference.setIsAppBiometricEnabled(context, isBiometricsEnabled)
        }

        /**
         * Verifies whether the App should enabled biometrics.  Typically used in a user settings
         * to enable or disable biometrics in the application.
         * @return true if App should enabled biometrics, otherwise disable biometrics
         */
        fun isAppBiometricEnabled(context: Context): Boolean {
            return BiometricSharedPreference.isAppBiometricEnabled(context)
        }

        /**
         * Verifies whether the App have registered a biometric authentication.  Typically used in
         * user registration flow to enable biometrics authentication.
         * @return true if the user have registered with biometrics authentication, otherwise false
         *  either skipped or not complete.
         */
        fun isBiometricRegistrationComplete(context: Context): Boolean {
            return BiometricSharedPreference.isBiometricRegistrationComplete(context)
        }

        /**
         * Sets the flag for isBiometricRegistrationComplete.  Typically used in user registration
         * flow to enable biometrics authentication.
         * @param context the application context
         * @param isBiometricsRegistrationComplete sets flag to true or false
         */
        fun setIsBiometricRegistrationComplete(
            context: Context,
            isBiometricsRegistrationComplete: Boolean
        ) {
            BiometricSharedPreference.setIsBiometricRegistrationComplete(
                context,
                isBiometricsRegistrationComplete
            )
        }

        @VisibleForTesting
        internal fun isNotSupportedDevice(): Boolean {
            return !Build.MANUFACTURER.toUpperCase().contains("GOOGLE")
        }
    }
}

enum class BiometricsType {
    FINGERPRINT,
    FACE_UNLOCK,
    IRIS,
    MULTIPLE,
    NONE
}

/**
 * Used for System Biometrics Prompt status callback
 * @see BiometricStatusConstants
 */
interface OnAuthenticationCallback {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
        BiometricStatusConstants.SUCCESS,
        BiometricStatusConstants.ERROR_USER_CANCELED,
        BiometricStatusConstants.ERROR_NO_HARDWARE,
        BiometricStatusConstants.ERROR_NO_BIOMETRICS,
        BiometricStatusConstants.ERROR_LOCKOUT,
        BiometricStatusConstants.ERROR_UNKNOWN,
        BiometricStatusConstants.FAILED_ATTEMPT
    )
    annotation class BiometricStatus

    fun onAuthComplete(@BiometricStatus statusCode: Int)

    companion object BiometricStatusConstants {
        /**
         * User have successfully authenticated via biometrics
         */
        const val SUCCESS = 0

        /**
         * User cancels the system dialog
         */
        const val ERROR_USER_CANCELED = 1

        /**
         * Hardware support is unavailable or missing actual hardware
         */
        const val ERROR_NO_HARDWARE = 2

        /**
         * User does not have any biometrics created on the device
         */
        const val ERROR_NO_BIOMETRICS = 3

        /**
         * Locked out due to too many attempts
         */
        const val ERROR_LOCKOUT = 4

        /**
         * Other error we don't care about right now
         */
        const val ERROR_UNKNOWN = 5

        /**
         * User failed to authenticate
         */
        const val FAILED_ATTEMPT = 99
    }
}