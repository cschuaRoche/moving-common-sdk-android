package com.roche.roche.dis.security.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.roche.roche.dis.rochecommon.dialogs.RocheDialogFactory
import com.roche.roche.dis.rochecommon.presentation.observeState
import javax.inject.Inject

/**
 * Automatically verifies potential security risks such as rooted device and licensing for this app.
 * When the Fragment is visible to the user, a non-cancellable error popup is shown if an error is
 * detected. Dismissing the error popup will finish the Activity.
 */
abstract class SecurityCheckerFragment : Fragment() {

    @Inject
    lateinit var securityViewModel: SecurityCheckerViewModel

    abstract fun provideLicensingKey(): String

    abstract fun provideBaseUrl(): String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState(securityViewModel)
        {
            when (it) {
                SecurityCheckerViewState.DeviceIsRooted -> {
                    RocheDialogFactory.showRootedDeviceDialog(requireContext()) { activity?.finish() }
                }
                SecurityCheckerViewState.InvalidLicense -> {
                    RocheDialogFactory.showInvalidLicenseDialog(requireContext()) {
                        activity?.finish()
                    }
                }
                SecurityCheckerViewState.Retry -> {
                    // We don't have a specific logic for retry, so default to network error
                    RocheDialogFactory.showNonCancellableNetworkErrorDialog(requireContext()) {
                        activity?.finish()
                    }
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

        securityViewModel.validate(provideLicensingKey(), provideBaseUrl())
    }
}