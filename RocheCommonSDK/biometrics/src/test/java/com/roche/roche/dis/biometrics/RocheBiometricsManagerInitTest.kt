package com.roche.roche.dis.biometrics

import android.content.pm.PackageManager
import androidx.biometric.BiometricManager
import io.mockk.every
import org.junit.Assert
import org.junit.Test

class RocheBiometricsManagerInitTest : RocheBiometricsBaseTest() {

    override fun getAuthenticators(): Int {
        return BiometricManager.Authenticators.BIOMETRIC_STRONG
    }

    @Test
    fun `when init is null then BIOMETRIC_WEAK is set`() {

    }

    @Test
    fun `when hardware or system does not support fingerprint then hasFingerPrint is false`() {
        val hasFingerPrint = RocheBiometricsManager.hasFingerPrint(context)
        Assert.assertFalse(hasFingerPrint)
    }

    @Test
    fun `when hardware and system supports fingerprint then hasFingerPrint is true`() {
        every { packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) } returns true
        val hasFingerPrint = RocheBiometricsManager.hasFingerPrint(context)
        Assert.assertTrue(hasFingerPrint)
    }

    @Test
    fun `when hardware or system does not support face unlcok then hasFaceUnlock is false`() {
        val hasFaceUnlock = RocheBiometricsManager.hasFaceUnlock(context)
        Assert.assertFalse(hasFaceUnlock)
    }

    @Test
    fun `when hardware and system supports face unlock then hasFaceUnlock is true`() {
        every { packageManager.hasSystemFeature(PackageManager.FEATURE_FACE) } returns true
        val hasFaceUnlock = RocheBiometricsManager.hasFaceUnlock(context)
        Assert.assertTrue(hasFaceUnlock)
    }

    @Test
    fun `when hardware or system does not support biometrics then isBiometricsAvailable is false`() {
        every { androidXBiometricManager.canAuthenticate(getAuthenticators()) } returns BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
        val rocheBiometricsManager = RocheBiometricsManager(context, getAuthenticators())
        Assert.assertFalse(rocheBiometricsManager.isAvailable)
    }

    @Test
    fun `when RocheBiometricsManager is initialized with Face Unlock, then hasFaceUnlockSetup is true`() {
        setFragmentWithSystemFeatures(listOf(PackageManager.FEATURE_FACE))
        val rocheBiometricsManager = RocheBiometricsManager(context, getAuthenticators())
        val hasFaceUnlockSetup = rocheBiometricsManager.hasFaceUnlockSetup()
        Assert.assertEquals(BiometricsType.FACE_UNLOCK, rocheBiometricsManager.type)
        Assert.assertTrue(hasFaceUnlockSetup)
    }

    @Test
    fun `when RocheBiometricsManager is initialized with Fingerprint Unlock, then hasFingerprintSetup is true`() {
        setFragmentWithSystemFeatures(listOf(PackageManager.FEATURE_FINGERPRINT))
        val rocheBiometricsManager = RocheBiometricsManager(context, getAuthenticators())
        val hasFingerprintUnlockSetup = rocheBiometricsManager.hasFingerprintSetup()
        Assert.assertEquals(BiometricsType.FINGERPRINT, rocheBiometricsManager.type)
        Assert.assertTrue(hasFingerprintUnlockSetup)
    }

    @Test
    fun `when RocheBiometricsManager is initialized with Iris, then hasIrisSetup is true`() {
        setFragmentWithSystemFeatures(listOf(PackageManager.FEATURE_IRIS))
        val rocheBiometricsManager = RocheBiometricsManager(context, getAuthenticators())
        val hasIrisUnlockSetup = rocheBiometricsManager.hasIrisSetup()
        Assert.assertEquals(BiometricsType.IRIS, rocheBiometricsManager.type)
        Assert.assertTrue(hasIrisUnlockSetup)
    }

    @Test
    fun `when RocheBiometricsManager is initialized with Biometrics, then hasMultipleBiometricsSetup is true`() {
        setFragmentWithSystemFeatures(
            listOf(
                PackageManager.FEATURE_FINGERPRINT,
                PackageManager.FEATURE_IRIS,
                PackageManager.FEATURE_FACE
            )
        )
        val rocheBiometricsManager = RocheBiometricsManager(context, getAuthenticators())
        val hasBiometricsUnlockSetup = rocheBiometricsManager.hasMultipleBiometricsSetup()
        Assert.assertTrue(hasBiometricsUnlockSetup)
        Assert.assertEquals(BiometricsType.MULTIPLE, rocheBiometricsManager.type)
        Assert.assertTrue(rocheBiometricsManager.isAvailable)
        Assert.assertFalse(rocheBiometricsManager.isNotEnrolled)
    }

    private fun setFragmentWithSystemFeatures(systemFeatures: List<String>) {
        for (feature in systemFeatures) {
            every { packageManager.hasSystemFeature(feature) } returns true
        }
        every { context.packageManager } returns packageManager
        every { fragment.requireContext() } returns context
    }
}