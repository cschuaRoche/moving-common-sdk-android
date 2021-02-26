package com.roche.roche.dis.biometrics

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import com.roche.roche.dis.biometrics.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), OnAuthenticationCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var biometricsManager: RocheBiometricsManager

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
        binding.biometricBtn.setOnClickListener {
            biometricsManager.showAuthDialog(this, this)
        }
    }

    override fun onAuthComplete(statusCode: Int) {
        Toast.makeText(this, "statusCode: $statusCode", Toast.LENGTH_SHORT).show()
    }
}