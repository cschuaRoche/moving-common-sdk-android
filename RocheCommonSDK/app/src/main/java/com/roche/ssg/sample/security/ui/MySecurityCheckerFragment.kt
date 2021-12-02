package com.roche.ssg.sample.security.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roche.ssg.sample.BuildConfig
import com.roche.ssg.sample.R
import com.roche.ssg.sample.databinding.FragmentSecurityCheckerBinding
import com.roche.ssg.security.presentation.SecurityCheckerFragment
import com.roche.ssg.security.presentation.SecurityCheckerViewModel

class MySecurityCheckerFragment : SecurityCheckerFragment() {
    private lateinit var binding: FragmentSecurityCheckerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        securityViewModel = SecurityCheckerViewModel(requireActivity().application)
        binding = FragmentSecurityCheckerBinding.inflate(inflater, container, false)
        resetTxtValidLicense()
        return binding.root
    }

    override fun shouldValidateLicense(): Boolean {
        return BuildConfig.ENABLE_SECURITY
    }

    override fun provideLicensingKey(): String {
        return BuildConfig.LICENSE_KEY
    }

    override fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun isOfflineMode(): Boolean {
        return BuildConfig.IS_OFFLINE_MODE
    }

    override fun onValidLicense() {
        Log.d("MySecurityCheckerFragment", "onValidLicense")
        if (BuildConfig.DEBUG) {
            binding.txtValidLicense.setText(R.string.valid_license_debug)
        }
        binding.txtValidLicense.visibility = View.VISIBLE
    }

    private fun resetTxtValidLicense() {
        binding.txtValidLicense.visibility = View.GONE
        binding.txtValidLicense.setText(R.string.valid_license)
    }
}