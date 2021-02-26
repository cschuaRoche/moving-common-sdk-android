package com.roche.roche.dis.biometrics

import android.content.res.Resources
import android.util.Log
import androidx.annotation.Keep
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Used for showing Biometrics Dialogs
 * @param allowedAuthenticators authenticator types - BiometricManager.Authenticators
 * @param type RocheBiometricsManager.BiometricsType
 * @see "https://developer.android.com/reference/androidx/biometric/BiometricPrompt.PromptInfo.Builder#setAllowedAuthenticators(int)"
 */
class BiometricsDialogs(private val allowedAuthenticators: Int, val type: BiometricsType) {
    var biometricPrompt: BiometricPrompt? = null

    /**
     * Shows a system dialog to confirm use of biometrics
     * @param activity the Activity where the prompt dialog will be shown
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showConfirmationDialog(activity: FragmentActivity, callback: OnAuthenticationCallback) {
        if (type == BiometricsType.NONE) {
            Log.w("BiometricsManager", "No biometrics detected")
            return
        }
        val title = activity.getString(R.string.biometric_confirm_title)
        val description = getConfirmationDescription(activity.resources)
        val buttonText = activity.getString(R.string.cancel)
        showBiometricDialog(activity, PromptData(title, description, buttonText), callback)
    }

    /**
     * Shows a system dialog to confirm use of biometrics
     * @param fragment the Fragment where the prompt dialog will be shown
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showConfirmationDialog(fragment: Fragment, callback: OnAuthenticationCallback) {
        if (type == BiometricsType.NONE) {
            Log.w("BiometricsManager", "No biometrics detected")
            return
        }
        val title = fragment.getString(R.string.biometric_confirm_title)
        val description = getConfirmationDescription(fragment.resources)
        val buttonText = fragment.getString(R.string.cancel)
        showBiometricDialog(fragment, PromptData(title, description, buttonText), callback)
    }

    private fun getConfirmationDescription(resources: Resources): String {
        return when (type) {
            BiometricsType.MULTIPLE -> {
                resources.getString(R.string.biometric_confirm_desc)
            }
            BiometricsType.FINGERPRINT -> {
                resources.getString(R.string.biometric_confirm_fingerprint_desc)
            }
            BiometricsType.FACE_UNLOCK -> {
                resources.getString(R.string.biometric_confirm_face_desc)
            }
            else -> {
                resources.getString(R.string.biometric_confirm_desc)
            }
        }
    }

    /**
     * Shows a system biometric dialog for authentication
     * @param activity the Activity where the prompt dialog will be shown
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showAuthDialog(activity: FragmentActivity, callback: OnAuthenticationCallback) {
        if (type == BiometricsType.NONE) {
            Log.w("BiometricsManager", "No biometrics detected")
            return
        }
        val title = activity.getString(R.string.biometric_auth_title)
        val description = getAuthDescription(activity.resources)
        val buttonText = activity.getString(R.string.biometric_auth_cancel_label)
        showBiometricDialog(activity, PromptData(title, description, buttonText), callback)
    }

    /**
     * Shows a system biometric dialog for authentication
     * @param fragment the Fragment where the prompt dialog will be shown
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showAuthDialog(fragment: Fragment, callback: OnAuthenticationCallback) {
        if (type == BiometricsType.NONE) {
            Log.w("BiometricsManager", "No biometrics detected")
            return
        }
        val title = fragment.getString(R.string.biometric_auth_title)
        val description = getAuthDescription(fragment.resources)
        val buttonText = fragment.getString(R.string.biometric_auth_cancel_label)
        showBiometricDialog(fragment, PromptData(title, description, buttonText), callback)
    }

    private fun getAuthDescription(resources: Resources): String {
        return when (type) {
            BiometricsType.MULTIPLE -> {
                resources.getString(R.string.biometric_auth_desc)
            }
            BiometricsType.FINGERPRINT -> {
                resources.getString(R.string.biometric_auth_finger_desc)
            }
            BiometricsType.FACE_UNLOCK -> {
                resources.getString(R.string.biometric_auth_face_desc)
            }
            else -> {
                resources.getString(R.string.biometric_auth_desc)
            }
        }
    }

    /**
     * Shows the Android native biometric prompt dialog.  Use this if you do not want to use the
     * confirm dialog from BiometricsDialogs.
     * @param activity the Activity where the prompt dialog will be shown
     * @param promptData the data to be used in the UI
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showBiometricDialog(
        activity: FragmentActivity,
        promptData: PromptData,
        callback: OnAuthenticationCallback
    ) {
        biometricPrompt = getBiometricPrompt(activity, callback)
        showBiometricDialog(
            biometricPrompt,
            promptData.title,
            promptData.description,
            promptData.buttonText
        )
    }

    /**
     * Shows the Android native biometric prompt dialog.  Use this if you do not want to use the
     * confirm dialog from BiometricsDialogs.
     * @param fragment the Fragment where the prompt dialog will be shown
     * @param promptData the data to be used in the UI
     * @param callback to notify the caller of the {@link BiometricStatusConstants}
     */
    fun showBiometricDialog(
        fragment: Fragment,
        promptData: PromptData,
        callback: OnAuthenticationCallback
    ) {
        biometricPrompt = getBiometricPrompt(fragment, callback)
        showBiometricDialog(
            biometricPrompt,
            promptData.title,
            promptData.description,
            promptData.buttonText
        )
    }

    /**
     * Dismiss the current Biometrics Dialog on screen.
     */
    fun dismissBiometricDialog() {
        biometricPrompt?.cancelAuthentication()
    }

    private fun showBiometricDialog(
        biometricPrompt: BiometricPrompt?,
        title: String,
        description: String,
        button: String
    ) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setNegativeButtonText(button)
            .setConfirmationRequired(false)
            .setAllowedAuthenticators(allowedAuthenticators)
            .build()
        biometricPrompt?.authenticate(promptInfo)
    }

    private fun getBiometricPrompt(
        fragment: Fragment,
        authCallback: OnAuthenticationCallback
    ): BiometricPrompt? {
        val context = fragment.requireContext()
        val executor = ContextCompat.getMainExecutor(context)
        return BiometricPrompt(fragment, executor, BiometricPromptCallback(authCallback))
    }

    private fun getBiometricPrompt(
        activity: FragmentActivity,
        authCallback: OnAuthenticationCallback
    ): BiometricPrompt? {
        val executor = ContextCompat.getMainExecutor(activity)
        return BiometricPrompt(activity, executor, BiometricPromptCallback(authCallback))
    }
}

internal class BiometricPromptCallback(private val callback: OnAuthenticationCallback) :
    BiometricPrompt.AuthenticationCallback() {
    override fun onAuthenticationError(
        errorCode: Int,
        errString: CharSequence
    ) {
        super.onAuthenticationError(errorCode, errString)
        processAuthError(errorCode, callback)
    }

    override fun onAuthenticationSucceeded(
        result: BiometricPrompt.AuthenticationResult
    ) {
        super.onAuthenticationSucceeded(result)
        callback.onAuthComplete(OnAuthenticationCallback.SUCCESS)
    }

    // called when an attempt to authenticate with biometrics fails
    // i.e. invalid fingerprint
    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        callback.onAuthComplete(OnAuthenticationCallback.FAILED_ATTEMPT)
    }

    private fun processAuthError(errorCode: Int, callback: OnAuthenticationCallback) {
        when (errorCode) {
            BiometricPrompt.ERROR_NEGATIVE_BUTTON, BiometricPrompt.ERROR_USER_CANCELED -> {
                callback.onAuthComplete(OnAuthenticationCallback.ERROR_USER_CANCELED)
            }
            BiometricPrompt.ERROR_HW_UNAVAILABLE, BiometricPrompt.ERROR_HW_NOT_PRESENT -> {
                callback.onAuthComplete(OnAuthenticationCallback.ERROR_NO_HARDWARE)
            }
            BiometricPrompt.ERROR_NO_BIOMETRICS -> {
                callback.onAuthComplete(OnAuthenticationCallback.ERROR_NO_BIOMETRICS)
            }
            BiometricPrompt.ERROR_LOCKOUT, BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> {
                callback.onAuthComplete(OnAuthenticationCallback.ERROR_LOCKOUT)
            }
            BiometricPrompt.ERROR_TIMEOUT, BiometricPrompt.ERROR_UNABLE_TO_PROCESS,
            BiometricPrompt.ERROR_NO_SPACE, BiometricPrompt.ERROR_VENDOR,
            BiometricPrompt.ERROR_CANCELED, BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL -> {
                callback.onAuthComplete(OnAuthenticationCallback.ERROR_UNKNOWN)
            }
        }
    }
}

/**
 * Used to customize the values of System Biometrics Prompt
 */
@Keep
data class PromptData(
    val title: String,
    val description: String,
    val buttonText: String
)