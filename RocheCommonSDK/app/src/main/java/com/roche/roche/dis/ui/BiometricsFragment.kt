package com.roche.roche.dis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.roche.roche.dis.R
import com.roche.roche.dis.biometrics.Authenticator
import com.roche.roche.dis.biometrics.RocheBiometricsManager
import com.roche.roche.dis.biometrics.callback.OnAuthenticationCallback
import com.roche.roche.dis.databinding.BiometricsFragmentBinding
import com.roche.roche.dis.rochecommon.dialogs.RocheDialogFactory
import com.roche.roche.dis.rochecommon.presentation.ViewBindingHolder
import com.roche.roche.dis.rochecommon.presentation.ViewBindingHolderImpl

class BiometricsFragment : Fragment(), OnAuthenticationCallback,
    ViewBindingHolder<BiometricsFragmentBinding> by ViewBindingHolderImpl(), View.OnClickListener {

    private lateinit var biometricsManager: RocheBiometricsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initBinding(BiometricsFragmentBinding.inflate(inflater), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireBinding {
            btnFingerprintSupported.setOnClickListener(this@BiometricsFragment)
            btnFaceSupported.setOnClickListener(this@BiometricsFragment)
            btnIrisSupported.setOnClickListener(this@BiometricsFragment)
            btnBiometricEnrolled.setOnClickListener(this@BiometricsFragment)
            btnEnrollBiometric.setOnClickListener(this@BiometricsFragment)
            btnAuthenticate.setOnClickListener(this@BiometricsFragment)
        }
        biometricsManager =
            RocheBiometricsManager(requireContext(), Authenticator.STRONG)
    }

    override fun onAuthComplete(statusCode: Int) {
        Toast.makeText(requireContext(), "statusCode: $statusCode", Toast.LENGTH_SHORT).show()
        requireBinding {
            txtStatusAuthenticate.text = "$statusCode"
        }
        // User does not have any biometrics created on the device, go to settings
        if (OnAuthenticationCallback.ERROR_NO_BIOMETRICS == statusCode) {
            RocheDialogFactory.showCancelOrSettings(
                getString(R.string.biometric_confirm_title),
                getString(R.string.biometric_enable_desc),
                requireContext()
            )
        }
    }

    override fun onClick(v: View?) {
        requireBinding {
            when (v?.id) {
                btnFingerprintSupported.id -> {
                    val status =
                        if (biometricsManager.isFingerprintSupported()) getString(R.string.status_true) else getString(R.string.status_false)
                    txtStatusFingerprintSupported.text = status

                }
                btnFaceSupported.id -> {
                    val status =
                        if (biometricsManager.isFaceSupported()) getString(R.string.status_true) else getString(R.string.status_false)
                    txtStatusFaceSupported.text = status
                }
                btnIrisSupported.id -> {
                    val status =
                        if (biometricsManager.isIrisSupported()) getString(R.string.status_true) else getString(R.string.status_false)
                    txtStatusIrisSupported.text = status
                }
                btnBiometricEnrolled.id -> {
                    val status =
                        if (biometricsManager.isBiometricsEnrolled()) getString(R.string.status_true) else getString(R.string.status_false)
                    txtStatusBiometricEnrolled.text = status
                }
                btnEnrollBiometric.id -> {
                    if (!biometricsManager.isBiometricsEnrolled())
                        biometricsManager.enrollBiometric()
                    else
                        Toast.makeText(requireContext(), "Biometric is already enrolled", Toast.LENGTH_SHORT).show()
                }
                btnAuthenticate.id -> {
                    biometricsManager.showAuthDialog(this@BiometricsFragment, this@BiometricsFragment)
                }
            }
        }

    }
}