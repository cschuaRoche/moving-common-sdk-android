package com.roche.roche.dis

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import com.roche.roche.dis.biometrics.BiometricsType
import com.roche.roche.dis.biometrics.OnAuthenticationCallback
import com.roche.roche.dis.biometrics.OnAuthenticationCallback.BiometricStatusConstants
import com.roche.roche.dis.biometrics.R
import com.roche.roche.dis.biometrics.RocheBiometricsManager
import com.roche.roche.dis.databinding.ActivityMainBinding
import com.roche.roche.dis.rochecommon.dialogs.RocheDialogFactory

class MainActivity : AppCompatActivity(), OnAuthenticationCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var biometricsManager: RocheBiometricsManager
    private val TAG = "Biometrics"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val allowedAuthenticators = if (Build.VERSION.SDK_INT > 29) {
            BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG
        } else {
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        }

        biometricsManager = RocheBiometricsManager(this, allowedAuthenticators)
        Log.d(TAG, "isAvailable: ${biometricsManager.isAvailable}")
        Log.d(TAG, "type: ${biometricsManager.type}")
        Log.d(TAG, "hasFingerprintSetup: ${biometricsManager.hasFingerprintSetup()}")
        Log.d(TAG, "canSetupBiometrics: ${biometricsManager.canSetupBiometrics(this)}")


        binding.biometricBtn.setOnClickListener {
            // if biometrics is available or
            // if fingerprint hardware is supported then allow biometrics auth
            // Note that in some devices, Biometrics is set to unknown due to security updates,
            // therefore we check if Fingerprint hardware is supported.
            if (biometricsManager.isAvailable || BiometricsType.FINGERPRINT == biometricsManager.type) {
                biometricsManager.showAuthDialog(this, this)
            } else {
                Toast.makeText(this, "Biometrics is not supported for this device!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAuthComplete(statusCode: Int) {
        Toast.makeText(this, "statusCode: $statusCode", Toast.LENGTH_SHORT).show()
        // User does not have any biometrics created on the device, go to settings
        if (BiometricStatusConstants.ERROR_NO_BIOMETRICS == statusCode) {
            RocheDialogFactory.showCancelOrSettings(getString(R.string.biometric_confirm_title), getString(
                R.string.biometric_enable_desc
            ), this)
        }
    }
}