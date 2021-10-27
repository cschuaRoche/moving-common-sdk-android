package com.roche.ssg.security.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.roche.ssg.rochecommon.dialogs.RocheDialogFactory
import com.roche.ssg.rochecommon.presentation.observeState
import javax.inject.Inject

/**
 * Automatically verifies potential security risks such as rooted device and licensing for this app.
 * When the Fragment is visible to the user, a non-cancellable error popup is shown if an error is
 * detected. Dismissing the error popup will finish the Activity.
 */
abstract class SecurityCheckerFragment : Fragment() {

    @Inject
    lateinit var securityViewModel: SecurityCheckerViewModel

    protected abstract fun shouldValidateLicense(): Boolean

    protected abstract fun provideLicensingKey(): String

    protected abstract fun provideBaseUrl(): String

    protected abstract fun isOfflineMode(): Boolean

    protected abstract fun onValidLicense()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState(securityViewModel)
        {
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

    override fun onDestroy() {
        super.onDestroy()
        securityViewModel.onDestroy()
    }

    override fun onResume() {
        super.onResume()

        securityViewModel.validate(
            provideLicensingKey(),
            provideBaseUrl(),
            shouldValidateLicense(),
            isOfflineMode()
        )
    }

    protected open fun onDeviceRooted() {
        RocheDialogFactory.showRootedDeviceDialog(requireContext()) {
            activity?.finish()
        }
    }

    protected open fun onInvalidLicense() {
        RocheDialogFactory.showInvalidLicenseDialog(requireContext()) {
            activity?.finish()
        }
    }

    protected open fun onRetry() {
        // We don't have a specific logic for retry, so default to network error
        RocheDialogFactory.showNonCancellableNetworkErrorDialog(requireContext()) {
            activity?.finish()
        }
    }
}