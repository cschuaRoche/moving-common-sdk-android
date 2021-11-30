package com.roche.ssg.sample.security.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return binding.root
    }

    override fun shouldValidateLicense(): Boolean {
        // to enable GooglePlay license checker, return true.
        // SUGGESTION: to control the behavior throughout the app, use a build time config or
        // retrieve a flag value from the BE.
        return true
    }

    override fun provideLicensingKey(): String {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1s/ZHx3eZZa1hdJCRjnW3jbl7Dj0nRIos1M/Vj1OnN08U9+RS40jBp7539naRiPHHcEBQtPLVQRCPef3Whco8NTegGUB7TJ1Je8bsv20Q/r68Sh7hRp5HPqxRg3sVYbcmsoNC0i8D3HLGpiUrKYgzFAYFN5Y2qD9Om40OT4xZWtTOkl6C+pIl6+XtpY0MLjzYoE6TpcMs2J7CgOEOZBlTjuTRvnVCc3sum/FBulQXaldBE188CA+LrrHAZy6Qdgv7Kho8NwtpsyReZ6JIB3bjx4I8UyEhrTkUGifoMLYHS+QRczavRUEpIo8BQxJRH6tT0j01/p7NlQHYyu/BUB+GQIDAQAB"
    }

    override fun provideBaseUrl(): String {
        return "https://alic7sdeef.execute-api.us-east-1.amazonaws.com/api/v1/"
    }

    override fun isOfflineMode(): Boolean {
        return false
    }

    override fun onValidLicense() {
        Log.d("MySecurityCheckerFragment", "onValidLicense")
    }
}