package com.roche.roche.dis.security.presentation

import android.app.Application
import android.provider.Settings
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.vending.licensing.AESObfuscator
import com.google.android.vending.licensing.LicenseChecker
import com.google.android.vending.licensing.LicenseCheckerCallback
import com.google.android.vending.licensing.Policy
import com.google.android.vending.licensing.ServerManagedPolicy
import com.roche.roche.dis.rochecommon.presentation.ViewEventHolder
import com.roche.roche.dis.rochecommon.presentation.ViewEventHolderImpl
import com.roche.roche.dis.rochecommon.presentation.ViewStateHolder
import com.roche.roche.dis.rochecommon.presentation.ViewStateHolderImpl
import com.roche.roche.dis.security.utils.RootDetectUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Verifies potential security risks such as rooted device and licensing for this app.
 */
class SecurityCheckerViewModel @Inject constructor(app: Application) : AndroidViewModel(app),
    LicenseCheckerCallback,
    ViewStateHolder<SecurityCheckerViewState> by ViewStateHolderImpl(),
    ViewEventHolder by ViewEventHolderImpl() {

    // TODO This should be stored in the BE or stored securely, but since our code is obfuscated, this is acceptable for now
    private val SALT = byteArrayOf(
        -46, 65, 30, -128, -103, -57, 74, -64, 51, 88,
        -95, -45, 77, -117, -36, -113, -11, 32, -64, 89
    )

    private lateinit var checker: LicenseChecker

    /**
     * validates potential security risks
     * @param publicKey the key provided by GooglePlay
     * @param baseUrl Base url for validating server side license
     * @param shouldValidateLicense By default it's true
     * @param isOfflineMode By default it's false
     */
    fun validate(
        publicKey: String,
        baseUrl: String,
        shouldValidateLicense: Boolean = true,
        isOfflineMode: Boolean = false
    ) {
        if (RootDetectUtil.isDeviceRooted()) {
            onRootedDeviceFound()
        } else if (shouldValidateLicense) {
            checker = getLicenseChecker(publicKey, baseUrl)
            checker.checkAccess(this, isOfflineMode)
        } else {
            // for Debug, QA, Support env, we ignore the security check
            updateState {
                SecurityCheckerViewState.IgnoreLicenseCheck
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getServerManagedPolicy(): ServerManagedPolicy {
        val app: Application = getApplication()
        return ServerManagedPolicy(
            app,
            getAESObfuscator()
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getAESObfuscator(): AESObfuscator {
        val app: Application = getApplication()
        return AESObfuscator(SALT, app.packageName, Settings.Secure.ANDROID_ID)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getLicenseChecker(publicKey: String, baseUrl: String): LicenseChecker {
        val app: Application = getApplication()
        return LicenseChecker(
            app,
            getServerManagedPolicy(),
            publicKey,
            baseUrl
        )
    }

    fun onDestroy() {
        if (this::checker.isInitialized) {
            checker.onDestroy()
        }
    }

    /**
     * Allow use. App should proceed as normal.
     *
     * @param reason Policy.LICENSED or Policy.RETRY typically. (although in
     *            theory the policy can return Policy.NOT_LICENSED here as well)
     */
    override fun allow(reason: Int) {
        checkReason(reason)
    }

    /**
     * Don't allow use. App should inform user and take appropriate action.
     *
     * @param reason Policy.NOT_LICENSED or Policy.RETRY. (although in theory
     *            the policy can return Policy.LICENSED here as well ---
     *            perhaps the call to the LVL took too long, for example)
     */
    override fun dontAllow(reason: Int) {
        checkReason(reason)
    }

    /**
     * Error in application code. Caller did not call or set up license checker correctly.
     *
     * @param errorCode ERROR_INVALID_PACKAGE_NAME, ERROR_NON_MATCHING_UID, ERROR_NOT_MARKET_MANAGED,
     * ERROR_CHECK_IN_PROGRESS, ERROR_INVALID_PUBLIC_KEY, ERROR_MISSING_PERMISSION
     */
    override fun applicationError(errorCode: Int) {
        onInvalidLicense()
    }

    /**
     * Check reason and display error messages if Policy.NOT_LICENSED or Policy.RETRY.
     */
    private fun checkReason(reason: Int) {
        when (reason) {
            // If the reason received from the policy is RETRY, it was probably
            // due to a loss of connection with the service.  For security reasons, it was decided
            // to simply show a network error.
            Policy.RETRY -> {
                onRetry()
            }
            Policy.NOT_LICENSED -> {
                onInvalidLicense()
            }
            Policy.LICENSED -> {
                onValidLicense()
            }
        }
    }

    private fun onValidLicense() {
        if (!RootDetectUtil.isDeviceRooted()) {
            viewModelScope.launch(Dispatchers.Main) {
                updateState {
                    SecurityCheckerViewState.ValidLicense
                }
            }
        }
    }

    private fun onRootedDeviceFound() {
        updateState {
            SecurityCheckerViewState.DeviceIsRooted
        }
    }

    private fun onInvalidLicense() {
        viewModelScope.launch(Dispatchers.Main) {
            updateState {
                SecurityCheckerViewState.InvalidLicense
            }
        }
    }

    private fun onRetry() {
        viewModelScope.launch(Dispatchers.Main) {
            updateState {
                SecurityCheckerViewState.Retry
            }
        }
    }

    companion object {
        const val ENABLED = "ENABLED"
    }
}

sealed class SecurityCheckerViewState {
    object ValidLicense : SecurityCheckerViewState()
    object InvalidLicense : SecurityCheckerViewState()
    object DeviceIsRooted : SecurityCheckerViewState()
    object Retry : SecurityCheckerViewState()
    object IgnoreLicenseCheck : SecurityCheckerViewState()
}