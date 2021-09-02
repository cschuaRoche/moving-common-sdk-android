package com.roche.roche.dis.biometrics

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Field
import java.lang.reflect.Modifier


class RocheBiometricsManagerTest {

    @MockK(relaxed = true)
    private lateinit var fragment: Fragment

    @MockK(relaxed = true)
    private lateinit var context: Context

    @MockK(relaxed = true)
    private lateinit var packageManager: PackageManager

    @MockK(relaxed = true)
    private lateinit var androidXBiometricManager: BiometricManager

    companion object {
        private const val allowedAuthenticators =
            BiometricManager.Authenticators.BIOMETRIC_STRONG
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        every { context.packageManager } returns packageManager
        every { fragment.requireContext() } returns context

        mockkStatic(BiometricManager::class)
        every { BiometricManager.from(context) } returns androidXBiometricManager

        setFinalStatic(Build::class.java.getField("MANUFACTURER"), "GOOGLE")
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), 29)
    }

    @After
    fun finish() {
        unmockkAll()
    }

    @Throws(Exception::class)
    fun setFinalStatic(field: Field, newValue: Any) {
        field.isAccessible = true

        val modifiersField = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())

        field.set(null, newValue)
    }

    @Test
    fun `when hardware or system does not support Biometric then isBiometricSupported is false`() {
        Assert.assertFalse(RocheBiometricsManager(context, Authenticator.STRONG).isBiometricSupported())
    }

    @Test
    fun `when hardware or system supports Biometric then isBiometricSupported is true`() {
        every { packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) } returns true
        Assert.assertTrue(RocheBiometricsManager(context, Authenticator.STRONG).isBiometricSupported())
    }

    @Test
    fun `when hardware or system does not support fingerprint then isFingerprintSupported is false`() {
        Assert.assertFalse(RocheBiometricsManager(context, Authenticator.STRONG).isFingerprintSupported())
    }

    @Test
    fun `when hardware or system supports fingerprint then isFingerprintSupported is true`() {
        every { packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) } returns true
        Assert.assertTrue(RocheBiometricsManager(context, Authenticator.STRONG).isFingerprintSupported())
    }

    @Test
    fun `when hardware or system does not support Face Unlock then isFaceSupported is false`() {
        Assert.assertFalse(RocheBiometricsManager(context, Authenticator.STRONG).isFaceSupported())
    }

    @Test
    fun `when hardware or system supports Face Unlock then isFaceSupported is true`() {
        every { packageManager.hasSystemFeature(PackageManager.FEATURE_FACE) } returns true
        Assert.assertTrue(RocheBiometricsManager(context, Authenticator.STRONG).isFaceSupported())
    }

    @Test
    fun `when hardware or system does not support IRIS unlock then isIrisSupported is false`() {
        Assert.assertFalse(RocheBiometricsManager(context, Authenticator.STRONG).isIrisSupported())
    }

    @Test
    fun `when hardware and system supports Face unlock then isIrisSupported is true`() {
        every { packageManager.hasSystemFeature(PackageManager.FEATURE_IRIS) } returns true
        Assert.assertTrue(RocheBiometricsManager(context, Authenticator.STRONG).isIrisSupported())
    }

    @Test
    fun `when biometrics is not enrolled on device then isBiometricsEnrolled is false`() {
        every { androidXBiometricManager.canAuthenticate(allowedAuthenticators) } returns BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
        Assert.assertFalse(RocheBiometricsManager(context, Authenticator.STRONG).isBiometricsEnrolled())
    }

    @Test
    fun `when biometrics is enrolled on device then isBiometricsEnrolled is true`() {
        every { androidXBiometricManager.canAuthenticate(allowedAuthenticators) } returns BiometricManager.BIOMETRIC_SUCCESS
        Assert.assertTrue(RocheBiometricsManager(context, Authenticator.STRONG).isBiometricsEnrolled())
    }

    @Test
    fun `when biometrics is not enrolled on device then canSetupBiometrics is true`() {
        every { androidXBiometricManager.canAuthenticate(allowedAuthenticators) } returns BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
        Assert.assertTrue(RocheBiometricsManager(context, Authenticator.STRONG).canSetupBiometrics())
    }

    @Test
    fun `when biometrics is enrolled on device then canSetupBiometrics is false`() {
        every { androidXBiometricManager.canAuthenticate(allowedAuthenticators) } returns BiometricManager.BIOMETRIC_SUCCESS
        Assert.assertFalse(RocheBiometricsManager(context, Authenticator.STRONG).canSetupBiometrics())
    }


    @Test
    fun `when BiometricsManager is initialized with no Face Unlock, then hasFaceUnlockSetup is false`() {
        val biometricsManager = RocheBiometricsManager(context, Authenticator.STRONG)
        val hasFaceUnlockSetup = biometricsManager.hasFaceUnlockSetup()
        Assert.assertFalse(hasFaceUnlockSetup)
        Assert.assertEquals(BiometricsType.NONE, biometricsManager.type)
    }

    @Test
    fun `when BiometricsManager is initialized with Face Unlock, then hasFaceUnlockSetup is true`() {
        //setFragmentWithSystemFeatures(listOf(PackageManager.FEATURE_FACE))
        setFragmentWithSystemFeatures(listOf(PackageManager.FEATURE_FACE))
        val biometricsManager = RocheBiometricsManager(context, Authenticator.STRONG)
        val hasFaceUnlockSetup = biometricsManager.hasFaceUnlockSetup()
        Assert.assertTrue(hasFaceUnlockSetup)
        Assert.assertEquals(BiometricsType.FACE_UNLOCK, biometricsManager.type)
    }

    @Test
    fun `when BiometricsManager is initialized with no Fingerprint Unlock, then hasFingerprintSetup is false`() {
        val biometricsManager = RocheBiometricsManager(context, Authenticator.STRONG)
        val hasFingerprintUnlockSetup = biometricsManager.hasFingerprintSetup()
        Assert.assertFalse(hasFingerprintUnlockSetup)
        Assert.assertEquals(BiometricsType.NONE, biometricsManager.type)
    }

    @Test
    fun `when BiometricsManager is initialized with Fingerprint Unlock, then hasFingerprintSetup is true`() {
        setFragmentWithSystemFeatures(listOf(PackageManager.FEATURE_FINGERPRINT))
        val biometricsManager = RocheBiometricsManager(context, Authenticator.STRONG)

        val hasFingerprintUnlockSetup = biometricsManager.hasFingerprintSetup()
        Assert.assertTrue(hasFingerprintUnlockSetup)
        Assert.assertEquals(BiometricsType.FINGERPRINT, biometricsManager.type)
    }

    @Test
    fun `when BiometricsManager is initialized with no Iris, then hasIrisSetup is false`() {
        val biometricsManager = RocheBiometricsManager(context, Authenticator.STRONG)
        val hasIrisUnlockSetup = biometricsManager.hasIrisSetup()
        Assert.assertFalse(hasIrisUnlockSetup)
        Assert.assertEquals(BiometricsType.NONE, biometricsManager.type)
    }

    @Test
    fun `when BiometricsManager is initialized with Iris, then hasIrisSetup is true`() {
        setFragmentWithSystemFeatures(listOf(PackageManager.FEATURE_IRIS))
        val biometricsManager = RocheBiometricsManager(context, Authenticator.STRONG)
        val hasIrisUnlockSetup = biometricsManager.hasIrisSetup()
        Assert.assertTrue(hasIrisUnlockSetup)
        Assert.assertEquals(BiometricsType.IRIS, biometricsManager.type)
    }

    @Test
    fun `when BiometricsManager is initialized with no Biometrics, then hasMultipleBiometricsSetup is false`() {
        val biometricsManager = RocheBiometricsManager(context, Authenticator.STRONG)
        val hasBiometricsUnlockSetup = biometricsManager.hasMultipleBiometricsSetup()
        Assert.assertFalse(hasBiometricsUnlockSetup)
        Assert.assertEquals(BiometricsType.NONE, biometricsManager.type)
    }

    @Test
    fun `when BiometricsManager is initialized with Biometrics, then hasMultipleBiometricsSetup is true`() {
        setFragmentWithSystemFeatures(
            listOf(
                PackageManager.FEATURE_FINGERPRINT,
                PackageManager.FEATURE_IRIS,
                PackageManager.FEATURE_FACE
            )
        )
        val biometricsManager = RocheBiometricsManager(context, Authenticator.STRONG)

        val hasBiometricsUnlockSetup = biometricsManager.hasMultipleBiometricsSetup()
        Assert.assertTrue(hasBiometricsUnlockSetup)
        Assert.assertEquals(BiometricsType.MULTIPLE, biometricsManager.type)
        Assert.assertTrue(biometricsManager.isAvailable)
    }

    @Test
    fun `when dismissBiometricDialog is called but not showing`() {
        val biometricsManager = RocheBiometricsManager(context, Authenticator.STRONG)
        biometricsManager.dismissBiometricDialog()
        Assert.assertFalse(biometricsManager.isBiometricsDialogShowing)
    }

    private fun setFragmentWithSystemFeatures(systemFeatures: List<String>) {
        for (feature in systemFeatures) {
            every { packageManager.hasSystemFeature(feature) } returns true
        }
        every { context.packageManager } returns packageManager
        every { fragment.requireContext() } returns context
    }


}