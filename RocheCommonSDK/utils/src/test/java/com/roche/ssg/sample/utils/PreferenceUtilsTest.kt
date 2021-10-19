package com.roche.ssg.sample.utils

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class PreferenceUtilsTest : BaseMockkTest() {
    @MockK(relaxed = true)
    private lateinit var appContext: Application

    @MockK
    private lateinit var pref: SharedPreferences

    override fun setup() {
        super.setup()
        mockkObject(PreferenceUtil)
        mockkStatic("androidx.security.crypto.EncryptedSharedPreferences")
    }

    @Test
    fun `when createOrGetPreference is called then returns SharedPreferences object`() {
        val masterKey = mockk<MasterKey>(relaxed = true)
        every { PreferenceUtil.getMasterKey(appContext) } returns masterKey
        every {
            EncryptedSharedPreferences.create(
                appContext,
                getFilename(),
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } returns pref

        PreferenceUtil.createOrGetPreference(appContext, getFilename())
        verify(exactly = 1) {
            EncryptedSharedPreferences.create(
                appContext,
                getFilename(),
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
        verify(exactly = 1) { PreferenceUtil.getMasterKey(appContext) }
    }

    @Test
    fun `get should return value from getString of SharedPreferences`() {
        val expectedValue = "Roche"
        every { pref.getString(KEY, "") } returns expectedValue

        val value = pref.get(KEY, "")
        Assert.assertEquals(expectedValue, value)
        verify(exactly = 1) { pref.getString(KEY, "") }
        verify(exactly = 0) { pref.getInt(KEY, any()) }
        verify(exactly = 0) { pref.getBoolean(KEY, any()) }
        verify(exactly = 0) { pref.getFloat(KEY, any()) }
        verify(exactly = 0) { pref.getLong(KEY, any()) }
    }

    @Test
    fun `get should return value from getInt of SharedPreferences`() {
        val expectedValue = 100
        every { pref.getInt(KEY, 0) } returns expectedValue

        val value = pref.get(KEY, 0)
        Assert.assertEquals(expectedValue, value)
        verify(exactly = 1) { pref.getInt(KEY, 0) }
        verify(exactly = 0) { pref.getString(KEY, any()) }
        verify(exactly = 0) { pref.getBoolean(KEY, any()) }
        verify(exactly = 0) { pref.getFloat(KEY, any()) }
        verify(exactly = 0) { pref.getLong(KEY, any()) }
    }

    @Test
    fun `get should return value from getBoolean of SharedPreferences`() {
        val expectedValue = true
        every { pref.getBoolean(KEY, false) } returns expectedValue

        val value = pref.get(KEY, false)
        Assert.assertEquals(expectedValue, value)
        verify(exactly = 1) { pref.getBoolean(KEY, false) }
        verify(exactly = 0) { pref.getString(KEY, any()) }
        verify(exactly = 0) { pref.getInt(KEY, any()) }
        verify(exactly = 0) { pref.getFloat(KEY, any()) }
        verify(exactly = 0) { pref.getLong(KEY, any()) }
    }

    @Test
    fun `get should return value from getFloat of SharedPreferences`() {
        val expectedValue = 10.5f
        every { pref.getFloat(KEY, 0f) } returns expectedValue

        val value = pref.get(KEY, 0f)
        Assert.assertEquals(expectedValue, value)
        verify(exactly = 1) { pref.getFloat(KEY, 0f) }
        verify(exactly = 0) { pref.getString(KEY, any()) }
        verify(exactly = 0) { pref.getInt(KEY, any()) }
        verify(exactly = 0) { pref.getBoolean(KEY, any()) }
        verify(exactly = 0) { pref.getLong(KEY, any()) }
    }

    @Test
    fun `get should return value from getLong of SharedPreferences`() {
        val expectedValue = 10L
        every { pref.getLong(KEY, 0L) } returns expectedValue

        val value = pref.get(KEY, 0L)
        Assert.assertEquals(expectedValue, value)
        verify(exactly = 1) { pref.getLong(KEY, 0L) }
        verify(exactly = 0) { pref.getString(KEY, any()) }
        verify(exactly = 0) { pref.getInt(KEY, any()) }
        verify(exactly = 0) { pref.getBoolean(KEY, any()) }
        verify(exactly = 0) { pref.getFloat(KEY, any()) }
    }

    private fun getFilename() = "SharedPref"

    companion object {
        private const val KEY = "KEY"
    }
}