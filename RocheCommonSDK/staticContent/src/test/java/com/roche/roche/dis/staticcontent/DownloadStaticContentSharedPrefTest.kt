package com.roche.roche.dis.staticcontent

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.roche.roche.dis.staticcontent.DownloadStaticContentSharedPref.PREF_KEY_ETAG_PREFIX
import com.roche.roche.dis.staticcontent.DownloadStaticContentSharedPref.PREF_KEY_FILE_PATH_PREFIX
import com.roche.roche.dis.utils.PreferenceUtil
import com.roche.roche.dis.utils.get
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DownloadStaticContentSharedPrefTest : BaseMockkTest() {
    @MockK(relaxed = true)
    private lateinit var appContext: Application

    @MockK(relaxed = true)
    private lateinit var pref: SharedPreferences

    @Before
    override fun setup() {
        super.setup()

        mockkObject(DownloadStaticContentSharedPref)
        mockkObject(PreferenceUtil)
        mockkStatic(EncryptedSharedPreferences::class)

        every {
            appContext.getSharedPreferences(
                DownloadStaticContentSharedPref.USER_MANUALS_PREFS,
                Context.MODE_PRIVATE
            )
        } returns pref

        every {
            PreferenceUtil.createOrGetPreference(
                appContext,
                DownloadStaticContentSharedPref.USER_MANUALS_PREFS
            )
        } returns pref
    }

    @Test
    fun `getETag should return value from sharedPreferences`() {
        every { pref.get(generateKey(PREF_KEY_ETAG_PREFIX), "") } returns getETag()
        Assert.assertEquals(
            getETag(),
            DownloadStaticContentSharedPref.getETag(appContext, APP_VERSION, LOCALE)
        )
    }

    @Test
    fun `getETag should return empty if not set`() {
        every { pref.get(generateKey(PREF_KEY_ETAG_PREFIX), "") } returns null
        Assert.assertEquals(
            "",
            DownloadStaticContentSharedPref.getETag(appContext, APP_VERSION, LOCALE)
        )
    }

    @Test
    fun `saveETag should not crash`() {
        try {
            DownloadStaticContentSharedPref.saveETag(appContext, APP_VERSION, LOCALE, getETag())
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun `getDownloadedFilePath should return value from sharedPreferences`() {
        every { pref.get(generateKey(PREF_KEY_FILE_PATH_PREFIX), "") } returns getDownloadedPath()
        Assert.assertEquals(
            getDownloadedPath(),
            DownloadStaticContentSharedPref.getDownloadedFilePath(appContext, APP_VERSION, LOCALE)
        )
    }

    @Test
    fun `getDownloadedFilePath should return empty if not set`() {
        every { pref.get(generateKey(PREF_KEY_FILE_PATH_PREFIX), "") } returns null
        Assert.assertEquals(
            "",
            DownloadStaticContentSharedPref.getDownloadedFilePath(appContext, APP_VERSION, LOCALE)
        )
    }

    @Test
    fun `saveDownloadedFilePath should not crash`() {
        try {
            DownloadStaticContentSharedPref.saveDownloadedFilePath(
                appContext,
                APP_VERSION,
                LOCALE,
                getDownloadedPath()
            )
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    private fun generateKey(prefix: String): String =
        prefix + UNDERSCORE + APP_VERSION.replace(".", "_") + UNDERSCORE + LOCALE

    private fun getETag() = "8dc0c63a38126f59dadde2a309805d52"
    private fun getDownloadedPath() = "DOWNLOADED_PATH"

    companion object {
        private const val UNDERSCORE = "_"
        private const val APP_VERSION = "1.0.0"
        private const val LOCALE = DownloadStaticContent.LocaleType.EN_US
    }
}