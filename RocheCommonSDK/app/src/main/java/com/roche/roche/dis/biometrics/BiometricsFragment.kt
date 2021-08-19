package com.roche.roche.dis.biometrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.roche.roche.dis.biometrics.callback.OnAuthenticationCallback
import com.roche.roche.dis.databinding.BiometricsFragmentBinding
import com.roche.roche.dis.rochecommon.dialogs.RocheDialogFactory
import com.roche.roche.dis.rochecommon.presentation.ViewBindingHolder
import com.roche.roche.dis.rochecommon.presentation.ViewBindingHolderImpl

class BiometricsFragment : Fragment(), OnAuthenticationCallback,
    ViewBindingHolder<BiometricsFragmentBinding> by ViewBindingHolderImpl() {

    private lateinit var biometricsManager: RocheBiometricsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initBinding(BiometricsFragmentBinding.inflate(inflater), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        biometricsManager =
            RocheBiometricsManager(requireContext(), Authenticator.STRONG)
        requireBinding {
            biometricBtn.setOnClickListener {
                // if biometrics is available then allow biometrics auth
                // Note that in some devices, Biometrics is set to unknown due to security updates,
                // therefore we check if Fingerprint hardware is supported.

                if (biometricsManager.isBiometricSupported()) {
                    biometricsManager.showAuthDialog(
                        this@BiometricsFragment,
                        this@BiometricsFragment,
                        negativeButtonText = "Cancel"
                    )
                } else {
                    Toast.makeText(requireContext(), "Biometrics is not supported for this device!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onAuthComplete(statusCode: Int) {
        Toast.makeText(requireContext(), "statusCode: $statusCode", Toast.LENGTH_SHORT).show()
        // User does not have any biometrics created on the device, go to settings
        if (OnAuthenticationCallback.ERROR_NO_BIOMETRICS == statusCode) {
            RocheDialogFactory.showCancelOrSettings(
                getString(com.roche.roche.dis.R.string.biometric_confirm_title),
                getString(com.roche.roche.dis.R.string.biometric_enable_desc),
                requireContext()
            )
        }
    }
}