package com.roche.ssg.sample.security.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.roche.ssg.sample.BuildConfig
import com.roche.ssg.sample.R
import com.roche.ssg.sample.databinding.FragmentSecurityCheckerBinding
import com.roche.ssg.security.presentation.SecurityCheckerViewModel
import com.roche.ssg.security.presentation.SecurityCheckerViewState

class MySecurityCheckerFragment : Fragment() {
    private lateinit var binding: FragmentSecurityCheckerBinding
    private val securityViewModel : SecurityCheckerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecurityCheckerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        securityViewModel.viewState.observe(this) {
            Log.d("MySecurityCheckerFragment", "viewState: $it")
            when (it) {
                SecurityCheckerViewState.DeviceIsRooted -> {
                    binding.txtValidLicense.setText(R.string.rooted_device_dialog_desc)
                }
                SecurityCheckerViewState.InvalidLicense -> {
                    binding.txtValidLicense.setText(R.string.invalid_license_dialog_desc)
                }
                SecurityCheckerViewState.Retry -> {
                    // take action if state is Retry, which usually means a network failure.
                    // you may add retry logic and call securityViewModel.validate again.
                    binding.txtValidLicense.setText(R.string.retry_security_check)
                }
                SecurityCheckerViewState.ValidLicense -> {
                    // take action when license is valid
                    // if shouldValidateLicense is false, the IgnoreLicenseCheck will trigger instead
                    if (BuildConfig.DEBUG) {
                        binding.txtValidLicense.setText(R.string.valid_license_debug)
                    } else {
                        binding.txtValidLicense.setText(R.string.valid_license)
                    }
                }
                SecurityCheckerViewState.IgnoreLicenseCheck -> {
                    binding.txtValidLicense.setText(R.string.security_check_ignored)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // you should validate every time the UI is resumed
        securityViewModel.validate(
            BuildConfig.LICENSE_KEY,
            BuildConfig.BASE_URL,
            BuildConfig.ENABLE_SECURITY,
            BuildConfig.IS_OFFLINE_MODE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        securityViewModel.onDestroy() // make sure to call destroy to avoid memory leaks
    }
}