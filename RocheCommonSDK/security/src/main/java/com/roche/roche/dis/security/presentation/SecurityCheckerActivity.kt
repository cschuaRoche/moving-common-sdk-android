package com.roche.roche.dis.security.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.roche.roche.dis.rochecommon.dialogs.RocheDialogFactory

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
                SecurityCheckerViewState.ValidLicense, SecurityCheckerViewState.IgnoreSecurityCheck -> {
                    onValidLicense()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        securityViewModel.validate(provideLicensingKey(), provideBaseUrl(), isOfflineMode())
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

    abstract fun onValidLicense()

    abstract fun provideLicensingKey(): String

    abstract fun provideBaseUrl(): String

    abstract fun isOfflineMode(): Boolean
}