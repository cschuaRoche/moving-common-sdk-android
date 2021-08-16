package com.roche.roche.dis.biometrics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.roche.roche.dis.databinding.BiometricsFragmentBinding
import com.roche.roche.dis.rochecommon.dialogs.RocheDialogFactory
import com.roche.roche.dis.rochecommon.presentation.ViewBindingHolder
import com.roche.roche.dis.rochecommon.presentation.ViewBindingHolderImpl

class BiometricsFragment : Fragment(), OnAuthenticationCallback,
    ViewBindingHolder<BiometricsFragmentBinding> by ViewBindingHolderImpl() {

    private lateinit var biometricsManager: RocheBiometricsManager
    private val TAG = "Biometrics"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initBinding(BiometricsFragmentBinding.inflate(inflater), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val allowedAuthenticators = if (Build.VERSION.SDK_INT > 29) {
            BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG
        } else {
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        }*/

        //val allowedAuthenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG
        biometricsManager =
            RocheBiometricsManager(requireContext(), Authenticator.STRONG, negativeButtonText = "Cancel")
        Log.d(TAG, "isAvailable: ${biometricsManager.isAvailable}")
        Log.d(TAG, "type: ${biometricsManager.type}")
        Log.d(TAG, "hasFingerprintSetup: ${biometricsManager.hasFingerprintSetup()}")
        Log.d(TAG, "canSetupBiometrics: ${biometricsManager.canSetupBiometrics(requireContext())}")

        requireBinding {
            biometricBtn.setOnClickListener {
                // if biometrics is available or
                // if fingerprint hardware is supported then allow biometrics auth
                // Note that in some devices, Biometrics is set to unknown due to security updates,
                // therefore we check if Fingerprint hardware is supported.
                if (biometricsManager.isAvailable || BiometricsType.FINGERPRINT == biometricsManager.type) {
                    biometricsManager.showAuthDialog(this@BiometricsFragment, this@BiometricsFragment)
                } else {
                    /*Toast.makeText(requireContext(), "Biometrics is not supported for this device!", Toast.LENGTH_SHORT)
                        .show()*/
                    biometricsManager.enrollBiometric()
                }
            }
        }
    }

    override fun onAuthComplete(statusCode: Int) {
        Toast.makeText(requireContext(), "statusCode: $statusCode", Toast.LENGTH_SHORT).show()
        // User does not have any biometrics created on the device, go to settings
        if (OnAuthenticationCallback.ERROR_NO_BIOMETRICS == statusCode) {
            RocheDialogFactory.showCancelOrSettings(getString(R.string.biometric_confirm_title), getString(R.string.biometric_enable_desc), requireContext())
        }
    }
}