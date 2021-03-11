package com.roche.roche.dis.biometrics

import android.content.pm.PackageManager
import androidx.biometric.BiometricManager
import io.mockk.every
import org.junit.Assert
import org.junit.Test

class RocheBiometricsManagerTest : RocheBiometricsBaseTest() {

    override fun getAuthenticators(): Int {
        return BiometricManager.Authenticators.BIOMETRIC_STRONG
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
    fun `when hardware and system supports face unlock then isBiometricsAvailable is true`() {
        Assert.assertTrue(rocheBiometricsManager.isAvailable)
    }

    @Test
    fun `when RocheBiometricsManager is initialized with no Face Unlock, then hasFaceUnlockSetup is false`() {
        val hasFaceUnlockSetup = rocheBiometricsManager.hasFaceUnlockSetup()
        Assert.assertFalse(hasFaceUnlockSetup)
        Assert.assertEquals(BiometricsType.NONE, rocheBiometricsManager.type)
    }

    @Test
    fun `when RocheBiometricsManager is initialized with no Fingerprint Unlock, then hasFingerprintSetup is false`() {
        val hasFingerprintUnlockSetup = rocheBiometricsManager.hasFingerprintSetup()
        Assert.assertFalse(hasFingerprintUnlockSetup)
        Assert.assertEquals(BiometricsType.NONE, rocheBiometricsManager.type)
    }

    @Test
    fun `when RocheBiometricsManager is initialized with no Iris, then hasIrisSetup is false`() {
        val hasIrisUnlockSetup = rocheBiometricsManager.hasIrisSetup()
        Assert.assertFalse(hasIrisUnlockSetup)
        Assert.assertEquals(BiometricsType.NONE, rocheBiometricsManager.type)
    }

    @Test
    fun `when RocheBiometricsManager is initialized with no Biometrics, then hasMultipleBiometricsSetup is false`() {
        val hasBiometricsUnlockSetup = rocheBiometricsManager.hasMultipleBiometricsSetup()
        Assert.assertFalse(hasBiometricsUnlockSetup)
        Assert.assertEquals(BiometricsType.NONE, rocheBiometricsManager.type)
    }

    @Test
    fun `when showAuthDialog is called and no biometrics is detected, do nothing`() {
        val callback = object : OnAuthenticationCallback {
            override fun onAuthComplete(statusCode: Int) {
                Assert.fail()
            }
        }
        rocheBiometricsManager.showAuthDialog(fragment, callback)
        Assert.assertEquals(BiometricsType.NONE, rocheBiometricsManager.type)

        rocheBiometricsManager.showAuthDialog(activity, callback)
        Assert.assertEquals(BiometricsType.NONE, rocheBiometricsManager.type)
    }

    @Test
    fun `when showConfirmationDialog is called and no biometrics is detected, do nothing`() {
        val callback = object : OnAuthenticationCallback {
            override fun onAuthComplete(statusCode: Int) {
                Assert.fail()
            }
        }
        rocheBiometricsManager.showConfirmationDialog(fragment, callback)
        Assert.assertEquals(BiometricsType.NONE, rocheBiometricsManager.type)

        rocheBiometricsManager.showConfirmationDialog(activity, callback)
        Assert.assertEquals(BiometricsType.NONE, rocheBiometricsManager.type)
    }

    @Test
    fun `when dismissBiometricDialog is called but not showing`() {
        rocheBiometricsManager.dismissBiometricDialog()
        Assert.assertFalse(rocheBiometricsManager.isBiometricsDialogShowing)
    }
}