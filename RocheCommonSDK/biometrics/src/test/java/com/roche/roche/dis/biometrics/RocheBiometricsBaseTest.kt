package com.roche.roche.dis.biometrics

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.roche.roche.dis.BaseMockkTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.junit.Before

abstract class RocheBiometricsBaseTest : BaseMockkTest() {
    @MockK(relaxed = true)
    internal lateinit var fragment: Fragment

    @MockK(relaxed = true)
    internal lateinit var activity: FragmentActivity

    @MockK(relaxed = true)
    internal lateinit var context: Context

    @MockK(relaxed = true)
    internal lateinit var packageManager: PackageManager

    @MockK(relaxed = true)
    internal lateinit var androidXBiometricManager: BiometricManager

    internal lateinit var rocheBiometricsManager: RocheBiometricsManager

    internal abstract fun getAuthenticators(): Int

    @Before
    override fun setup() {
        super.setup()
        every { context.packageManager } returns packageManager
        every { fragment.requireContext() } returns context

        mockkStatic(BiometricManager::class)
        every { BiometricManager.from(context) } returns androidXBiometricManager

        every { androidXBiometricManager.canAuthenticate(getAuthenticators()) } returns BiometricManager.BIOMETRIC_SUCCESS

        mockkObject(RocheBiometricsManager)
        every { RocheBiometricsManager.isNotSupportedDevice() } returns false
        every { RocheBiometricsManager.hasFaceIrisAPISupport() } returns true

        mockkStatic(Log::class)
        every { Log.w("BiometricsManager", "No biometrics detected") } returns 0

        rocheBiometricsManager = RocheBiometricsManager(context, getAuthenticators())
    }
}