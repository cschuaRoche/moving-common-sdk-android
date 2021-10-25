package com.roche.ssg.security.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.roche.ssg.rochecommon.dialogs.RocheDialogFactory

abstract class SecurityCheckerActivity : AppCompatActivity() {

    private val securityViewModel: SecurityCheckerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        securityViewModel.viewState.observe(this) {
            when (it) {
                SecurityCheckerViewState.DeviceIsRooted -> {
                    onDeviceRooted()
                }
                SecurityCheckerViewState.InvalidLicense -> {
                    onInvalidLicense()
                }
                SecurityCheckerViewState.Retry -> {
                    onRetry()
                }
                SecurityCheckerViewState.ValidLicense, SecurityCheckerViewState.IgnoreLicenseCheck -> {
                    onValidLicense()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        securityViewModel.validate(
            provideLicensingKey(),
            provideBaseUrl(),
            shouldValidateLicense(),
            isOfflineMode()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        securityViewModel.onDestroy()
    }

    protected open fun onDeviceRooted() {
        RocheDialogFactory.showRootedDeviceDialog(this) {
            finish()
        }
    }

    protected open fun onInvalidLicense() {
        RocheDialogFactory.showInvalidLicenseDialog(this) {
            finish()
        }
    }

    protected open fun onRetry() {
        // We don't have a specific logic for retry, so default to network error
        RocheDialogFactory.showNonCancellableNetworkErrorDialog(this) {
            finish()
        }
    }

    protected abstract fun onValidLicense()

    protected abstract fun shouldValidateLicense(): Boolean

    protected abstract fun provideLicensingKey(): String

    protected abstract fun provideBaseUrl(): String

    protected abstract fun isOfflineMode(): Boolean
}