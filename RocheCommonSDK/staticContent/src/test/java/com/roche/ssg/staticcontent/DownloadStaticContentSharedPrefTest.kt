package com.roche.ssg.staticcontent

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.roche.ssg.staticcontent.DownloadStaticContentSharedPref.PREF_KEY_ETAG_PREFIX
import com.roche.ssg.staticcontent.DownloadStaticContentSharedPref.PREF_KEY_FILE_PATH_PREFIX
import com.roche.ssg.staticcontent.DownloadStaticContentSharedPref.PREF_KEY_VERSION
import com.roche.ssg.sample.utils.PreferenceUtil
import com.roche.ssg.sample.utils.get
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
    fun `getVersion should return value from sharedPreferences`() {
        every { pref.get(PREF_KEY_VERSION, "") } returns APP_VERSION
        Assert.assertEquals(APP_VERSION, DownloadStaticContentSharedPref.getVersion(appContext))
    }

    @Test
    fun `getVersion should return empty if not set`() {
        every { pref.get(PREF_KEY_VERSION, "") } returns null
        Assert.assertEquals("", DownloadStaticContentSharedPref.getVersion(appContext))
    }

    @Test
    fun `setVersion should not crash`() {
        try {
            DownloadStaticContentSharedPref.setVersion(appContext, APP_VERSION)
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun `getETag should return value from sharedPreferences`() {
        every { pref.get(generateKey(PREF_KEY_ETAG_PREFIX), "") } returns getETag()
        Assert.assertEquals(
            getETag(),
            DownloadStaticContentSharedPref.getETag(appContext, APP_VERSION, LOCALE, FILE_KEY)
        )
    }

    @Test
    fun `getETag should return empty if not set`() {
        every { pref.get(generateKey(PREF_KEY_ETAG_PREFIX), "") } returns null
        Assert.assertEquals(
            "",
            DownloadStaticContentSharedPref.getETag(appContext, APP_VERSION, LOCALE, FILE_KEY)
        )
    }

    @Test
    fun `setETag should not crash`() {
        try {
            DownloadStaticContentSharedPref.setETag(
                appContext,
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                getETag()
            )
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun `getFilePath should return value from sharedPreferences`() {
        every { pref.get(generateKey(PREF_KEY_FILE_PATH_PREFIX), "") } returns getDownloadedPath()
        Assert.assertEquals(
            getDownloadedPath(),
            DownloadStaticContentSharedPref.getFilePath(appContext, APP_VERSION, LOCALE, FILE_KEY)
        )
    }

    @Test
    fun `getFilePath should return empty if not set`() {
        every { pref.get(generateKey(PREF_KEY_FILE_PATH_PREFIX), "") } returns null
        Assert.assertEquals(
            "",
            DownloadStaticContentSharedPref.getFilePath(appContext, APP_VERSION, LOCALE, FILE_KEY)
        )
    }

    @Test
    fun `setFilePath should not crash`() {
        try {
            DownloadStaticContentSharedPref.setFilePath(
                appContext,
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                getDownloadedPath()
            )
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    private fun generateKey(prefix: String) =
        "${prefix}_${APP_VERSION.replace(".", "_")}_${LOCALE}_${FILE_KEY}"

    private fun getETag() = "8dc0c63a38126f59dadde2a309805d52"
    private fun getDownloadedPath() = "DOWNLOADED_PATH"

    companion object {
        private const val APP_VERSION = "1.0.0"
        private const val LOCALE = DownloadStaticContent.LocaleType.EN_US
        private const val FILE_KEY = "user-manuals"
    }
}