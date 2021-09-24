package com.roche.roche.dis.staticcontent

import android.app.Application
import com.roche.roche.dis.utils.NetworkUtils
import com.roche.roche.dis.utils.UnZipUtils
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File
import java.net.HttpURLConnection

class DownloadStaticContentTest : BaseMockkTest() {
    @MockK(relaxed = true)
    private lateinit var appContext: Application

    @Before
    override fun setup() {
        super.setup()
        mockkObject(DownloadStaticContentSharedPref)
        mockkObject(DownloadStaticContent)
        mockkObject(UnZipUtils)
        mockkObject(NetworkUtils)
        every { NetworkUtils.hasInternetConnection(appContext) } returns true
        every { NetworkUtils.isWifiConnected(appContext) } returns true
    }

    @Test
    fun `when downloadStaticAssets is successful then returns unzipped file path`() = runBlocking {
        coEvery {
            DownloadStaticContent.getInfoFromManifest(
                appContext,
                getManifestUrl(),
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                false
            )
        } returns getZippedFileUrl()
        coEvery {
            DownloadStaticContent.downloadFromUrl(
                appContext,
                getZippedFileUrl(),
                any(),
                getSubDir(),
                false
            )
        } returns getZippedFilePath()
        every {
            DownloadStaticContent.unzipFile(
                appContext,
                getZippedFilePath(),
                getSubDir()
            )
        } returns getUnzippedFilePath()
        every {
            DownloadStaticContentSharedPref.setFilePath(
                appContext,
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                getUnzippedFilePath()
            )
        } returns Unit
        every {
            DownloadStaticContent.checkAndDeleteOldVersionData(
                appContext,
                APP_VERSION
            )
        } returns Unit

        val path = DownloadStaticContent.downloadStaticAssets(
            appContext,
            getManifestUrl(),
            APP_VERSION,
            LOCALE,
            FILE_KEY,
            ::showProgress
        )
        Assert.assertEquals(getUnzippedFilePath(), path)
        coVerify(exactly = 1) {
            DownloadStaticContent.getInfoFromManifest(
                appContext,
                getManifestUrl(),
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                false
            )
        }
        coVerify(exactly = 1) {
            DownloadStaticContent.downloadFromUrl(
                appContext,
                getZippedFileUrl(),
                any(),
                getSubDir(),
                false
            )
        }
        verify(exactly = 1) {
            DownloadStaticContent.unzipFile(
                appContext,
                getZippedFilePath(),
                getSubDir()
            )
        }
        verify(exactly = 1) {
            DownloadStaticContentSharedPref.setFilePath(
                appContext,
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                getUnzippedFilePath()
            )
        }
        verify(exactly = 1) {
            DownloadStaticContent.checkAndDeleteOldVersionData(
                appContext,
                APP_VERSION
            )
        }
    }

    @Test
    fun `when downloadStaticAssets throws EXCEPTION_INVALID_MANIFEST_FILE_FORMAT exception when file extension is not zip type`() =
        runBlocking {
            coEvery {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    false
                )
            } returns "ZippedFileUrl.txt"

            try {
                DownloadStaticContent.downloadStaticAssets(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    ::showProgress
                )
                Assert.fail("downloadStaticAssets should throw EXCEPTION_INVALID_MANIFEST_FILE_FORMAT error")
            } catch (e: IllegalStateException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_INVALID_MANIFEST_FILE_FORMAT,
                    e.message
                )
            }
            coVerify(exactly = 1) {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    false
                )
            }
            coVerify(exactly = 0) {
                DownloadStaticContent.downloadFromUrl(appContext, any(), any(), any(), any())
            }
            verify(exactly = 0) {
                DownloadStaticContent.unzipFile(appContext, any(), any())
            }
        }

    @Test
    fun `when downloadStaticAssets returns existing unzipped file path if EXCEPTION_NOT_MODIFIED received`() =
        runBlocking {
            coEvery {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    false
                )
            } throws IllegalStateException(DownloadStaticContent.EXCEPTION_NOT_MODIFIED)
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY
                )
            } returns getUnzippedFilePath()

            val path = DownloadStaticContent.downloadStaticAssets(
                appContext,
                getManifestUrl(),
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                ::showProgress
            )
            Assert.assertEquals(getUnzippedFilePath(), path)
            coVerify(exactly = 1) {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    false
                )
            }
            verify(exactly = 1) {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY
                )
            }
            coVerify(exactly = 0) {
                DownloadStaticContent.downloadFromUrl(appContext, any(), any(), any(), any())
            }
            verify(exactly = 0) {
                DownloadStaticContent.unzipFile(appContext, any(), any())
            }
        }

    @Test
    fun `when getUrlFromManifest is successful then returns zipped asset url`() = runBlocking {
        val httpURLConnection = mockk<HttpURLConnection>(relaxed = true)
        every { DownloadStaticContent.getUrlConnection(getManifestUrl()) } returns httpURLConnection
        every {
            DownloadStaticContentSharedPref.getETag(
                appContext,
                APP_VERSION,
                LOCALE,
                FILE_KEY
            )
        } returns ""
        every {
            DownloadStaticContentSharedPref.getFilePath(
                appContext,
                APP_VERSION,
                LOCALE,
                FILE_KEY
            )
        } returns ""
        every { httpURLConnection.responseCode } returns HttpURLConnection.HTTP_OK
        every { httpURLConnection.headerFields } returns mapOf(HEADER_KEY_ETAG to listOf(getETag()))
        every { DownloadStaticContent.readStream(any()) } returns getManifestContent()
        every {
            DownloadStaticContentSharedPref.setETag(
                appContext,
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                getETag()
            )
        } returns Unit

        val zippedUrl = DownloadStaticContent.getInfoFromManifest(
            appContext,
            getManifestUrl(),
            APP_VERSION,
            LOCALE,
            FILE_KEY
        )
        Assert.assertEquals(getZippedFileUrl(), zippedUrl)
    }

    @Test
    fun `getUrlFromManifest throws EXCEPTION_NOT_MODIFIED when response code is HTTP_NOT_MODIFIED`() =
        runBlocking {
            val httpURLConnection = mockk<HttpURLConnection>(relaxed = true)
            every { DownloadStaticContent.getUrlConnection(getManifestUrl()) } returns httpURLConnection
            every {
                DownloadStaticContentSharedPref.getETag(
                    appContext,
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY
                )
            } returns getETag()
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY
                )
            } returns getUnzippedFilePath()
            every { httpURLConnection.responseCode } returns HttpURLConnection.HTTP_NOT_MODIFIED

            try {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY
                )
                Assert.fail("unzipFile should have thrown EXCEPTION_NOT_MODIFIED error")
            } catch (e: IllegalStateException) {
                Assert.assertEquals(DownloadStaticContent.EXCEPTION_NOT_MODIFIED, e.message)
            }
        }

    @Test
    fun `getUrlFromManifest throws EXCEPTION_MANIFEST_APP_VERSION_NOT_FOUND exception when incorrect app version is provided`() =
        runBlocking {
            val incorrectAppVersion = "incorrectAppVersion"
            val httpURLConnection = mockk<HttpURLConnection>(relaxed = true)
            every { DownloadStaticContent.getUrlConnection(getManifestUrl()) } returns httpURLConnection
            every {
                DownloadStaticContentSharedPref.getETag(
                    appContext,
                    incorrectAppVersion,
                    LOCALE,
                    FILE_KEY
                )
            } returns ""
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    incorrectAppVersion,
                    LOCALE,
                    FILE_KEY
                )
            } returns ""
            every { httpURLConnection.responseCode } returns HttpURLConnection.HTTP_OK
            every { httpURLConnection.headerFields } returns mapOf(HEADER_KEY_ETAG to listOf(getETag()))
            every { DownloadStaticContent.readStream(any()) } returns getManifestContent()

            try {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    incorrectAppVersion,
                    LOCALE,
                    FILE_KEY
                )
                Assert.fail("getUrlFromManifest should have thrown EXCEPTION_MANIFEST_APP_VERSION_NOT_FOUND error")
            } catch (e: IllegalArgumentException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_MANIFEST_APP_VERSION_NOT_FOUND,
                    e.message
                )
                verify(exactly = 0) {
                    DownloadStaticContentSharedPref.setETag(
                        appContext,
                        incorrectAppVersion,
                        LOCALE,
                        FILE_KEY,
                        getETag()
                    )
                }
            }
        }

    @Test
    fun `getUrlFromManifest throws EXCEPTION_MANIFEST_LOCALE_NOT_FOUND exception when incorrect locale is provided`() =
        runBlocking {
            val incorrectLocale = "incorrectLocale"
            val httpURLConnection = mockk<HttpURLConnection>(relaxed = true)
            every { DownloadStaticContent.getUrlConnection(getManifestUrl()) } returns httpURLConnection
            every {
                DownloadStaticContentSharedPref.getETag(
                    appContext,
                    APP_VERSION,
                    incorrectLocale,
                    FILE_KEY
                )
            } returns ""
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    APP_VERSION,
                    incorrectLocale,
                    FILE_KEY
                )
            } returns ""
            every { httpURLConnection.responseCode } returns HttpURLConnection.HTTP_OK
            every { httpURLConnection.headerFields } returns mapOf(HEADER_KEY_ETAG to listOf(getETag()))
            every { DownloadStaticContent.readStream(any()) } returns getManifestContent()

            try {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    incorrectLocale,
                    FILE_KEY
                )
                Assert.fail("getUrlFromManifest should have thrown EXCEPTION_MANIFEST_LOCALE_NOT_FOUND error")
            } catch (e: IllegalArgumentException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_MANIFEST_LOCALE_NOT_FOUND,
                    e.message
                )
                verify(exactly = 0) {
                    DownloadStaticContentSharedPref.setETag(
                        appContext,
                        APP_VERSION,
                        incorrectLocale,
                        FILE_KEY,
                        getETag()
                    )
                }
            }
        }

    @Test
    fun `getUrlFromManifest throws EXCEPTION_MANIFEST_FILE_KEY_NOT_FOUND exception when incorrect file key is provided`() =
        runBlocking {
            val incorrectFileKey = "incorrectFileKey"
            val httpURLConnection = mockk<HttpURLConnection>(relaxed = true)
            every { DownloadStaticContent.getUrlConnection(getManifestUrl()) } returns httpURLConnection
            every {
                DownloadStaticContentSharedPref.getETag(
                    appContext,
                    APP_VERSION,
                    LOCALE,
                    incorrectFileKey
                )
            } returns ""
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    APP_VERSION,
                    LOCALE,
                    incorrectFileKey
                )
            } returns ""
            every { httpURLConnection.responseCode } returns HttpURLConnection.HTTP_OK
            every { httpURLConnection.headerFields } returns mapOf(HEADER_KEY_ETAG to listOf(getETag()))
            every { DownloadStaticContent.readStream(any()) } returns getManifestContent()

            try {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    incorrectFileKey
                )
                Assert.fail("getUrlFromManifest should have thrown EXCEPTION_MANIFEST_FILE_KEY_NOT_FOUND error")
            } catch (e: IllegalArgumentException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_MANIFEST_FILE_KEY_NOT_FOUND,
                    e.message
                )
                verify(exactly = 0) {
                    DownloadStaticContentSharedPref.setETag(
                        appContext,
                        APP_VERSION,
                        LOCALE,
                        incorrectFileKey,
                        getETag()
                    )
                }
            }
        }

    @Test
    fun `getUrlFromManifest throws EXCEPTION_NETWORK_NOT_AVAILABLE exception when network is not available`() =
        runBlocking {
            every { NetworkUtils.hasInternetConnection(appContext) } returns false
            try {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY
                )
                Assert.fail("getUrlFromManifest should have thrown EXCEPTION_NETWORK_NOT_AVAILABLE error")
            } catch (e: IllegalStateException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_NETWORK_NOT_AVAILABLE,
                    e.message
                )
                verify(exactly = 1) { NetworkUtils.hasInternetConnection(appContext) }
                verify(exactly = 0) { NetworkUtils.isWifiConnected(appContext) }
            }
        }

    @Test
    fun `getUrlFromManifest throws EXCEPTION_WIFI_NOT_AVAILABLE exception when wifi is not available`() =
        runBlocking {
            every { NetworkUtils.isWifiConnected(appContext) } returns false
            try {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    true
                )
                Assert.fail("getUrlFromManifest should have thrown EXCEPTION_NETWORK_NOT_AVAILABLE error")
            } catch (e: IllegalStateException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_WIFI_NOT_AVAILABLE,
                    e.message
                )
                verify(exactly = 1) { NetworkUtils.isWifiConnected(appContext) }
                verify(exactly = 0) { NetworkUtils.hasInternetConnection(appContext) }
            }
        }

    @Test
    fun `when downloadFromUrl is successful then returns zipped file path`() = runBlocking {
        val httpURLConnection = mockk<HttpURLConnection>(relaxed = true)
        every { DownloadStaticContent.getUrlConnection(getZippedFileUrl()) } returns httpURLConnection
        every { appContext.filesDir } returns File(CONTEXT_FILES_DIR)
        coEvery { DownloadStaticContent.writeStream(any(), any(), any(), any()) } returns Unit

        val zippedPath = DownloadStaticContent.downloadFromUrl(
            appContext,
            getZippedFileUrl(),
            ::showProgress
        )
        Assert.assertEquals(getZippedFilePath(), zippedPath)
    }

    @Test
    fun `downloadFromUrl throws EXCEPTION_NETWORK_NOT_AVAILABLE exception when network is not available`() =
        runBlocking {
            every { NetworkUtils.hasInternetConnection(appContext) } returns false
            try {
                DownloadStaticContent.downloadFromUrl(
                    appContext,
                    getZippedFileUrl(),
                    ::showProgress
                )
                Assert.fail("downloadFromUrl should have thrown EXCEPTION_NETWORK_NOT_AVAILABLE error")
            } catch (e: IllegalStateException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_NETWORK_NOT_AVAILABLE,
                    e.message
                )
                verify(exactly = 1) { NetworkUtils.hasInternetConnection(appContext) }
                verify(exactly = 0) { NetworkUtils.isWifiConnected(appContext) }
            }
        }

    @Test
    fun `downloadFromUrl throws EXCEPTION_WIFI_NOT_AVAILABLE exception when wifi is not available`() =
        runBlocking {
            every { NetworkUtils.isWifiConnected(appContext) } returns false
            try {
                DownloadStaticContent.downloadFromUrl(
                    appContext,
                    getZippedFileUrl(),
                    ::showProgress,
                    allowWifiOnly = true
                )
                Assert.fail("downloadFromUrl should have thrown EXCEPTION_NETWORK_NOT_AVAILABLE error")
            } catch (e: IllegalStateException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_WIFI_NOT_AVAILABLE,
                    e.message
                )
                verify(exactly = 1) { NetworkUtils.isWifiConnected(appContext) }
                verify(exactly = 0) { NetworkUtils.hasInternetConnection(appContext) }
            }
        }

    @Test
    fun `when unzipFile is successful then returns unzipped file path`() {
        every {
            UnZipUtils.unzipFromAppFiles(
                getZippedFilePath(),
                appContext
            )
        } returns getUnzippedFilePath()

        val unzippedPath =
            DownloadStaticContent.unzipFile(appContext, getZippedFilePath())
        Assert.assertEquals(getUnzippedFilePath(), unzippedPath)
        verify(exactly = 1) {
            UnZipUtils.unzipFromAppFiles(
                getZippedFilePath(),
                appContext
            )
        }
    }

    @Test
    fun `when unzipFile is failed then throws error`() {
        every {
            UnZipUtils.unzipFromAppFiles(
                getZippedFilePath(),
                appContext
            )
        } returns null

        try {
            DownloadStaticContent.unzipFile(appContext, getZippedFilePath())
            Assert.fail("unzipFile didn't throw EXCEPTION_UNZIPPING_FILE error")
        } catch (e: IllegalStateException) {
            Assert.assertEquals(DownloadStaticContent.EXCEPTION_UNZIPPING_FILE, e.message)
        }
    }

    @Test
    fun `test checkAndDeleteOldVersionData when existing version in SharedPref doesn't exist`() {
        val existingVersion = ""
        every { DownloadStaticContentSharedPref.getVersion(appContext) } returns existingVersion
        every { DownloadStaticContentSharedPref.setVersion(appContext, APP_VERSION) } returns Unit
        DownloadStaticContent.checkAndDeleteOldVersionData(appContext, APP_VERSION)
        verify(exactly = 1) { DownloadStaticContentSharedPref.setVersion(appContext, APP_VERSION) }
        verify(exactly = 0) {
            DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(
                appContext,
                existingVersion
            )
        }
    }

    @Test
    fun `test checkAndDeleteOldVersionData when newer version is different than existing version`() {
        val existingVersion = "1.0.0"
        val newerVersion = "2.0.0"
        every { DownloadStaticContentSharedPref.getVersion(appContext) } returns existingVersion
        every {
            DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(
                appContext,
                existingVersion
            )
        } returns Unit
        every { DownloadStaticContentSharedPref.setVersion(appContext, newerVersion) } returns Unit

        DownloadStaticContent.checkAndDeleteOldVersionData(appContext, newerVersion)
        verify(exactly = 1) {
            DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(
                appContext,
                existingVersion
            )
        }
        verify(exactly = 1) { DownloadStaticContentSharedPref.setVersion(appContext, newerVersion) }
    }

    @Test
    fun `test checkAndDeleteOldVersionData when newer version is similar to the existing version`() {
        val existingVersion = "1.0.0"
        val newerVersion = "1.0.0"
        every { DownloadStaticContentSharedPref.getVersion(appContext) } returns existingVersion

        DownloadStaticContent.checkAndDeleteOldVersionData(appContext, newerVersion)
        verify(exactly = 0) {
            DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(
                appContext,
                existingVersion
            )
        }
        verify(exactly = 0) { DownloadStaticContentSharedPref.setVersion(appContext, newerVersion) }
    }

    private fun getManifestUrl() = "https://domain-name/docs/manifest.json"
    private fun getZippedFileUrl() = "https://domain-name/docs/zipped-file.zip"
    private fun getZippedFilePath() = CONTEXT_FILES_DIR + File.separator + "zipped-file.zip"
    private fun getUnzippedFilePath() = CONTEXT_FILES_DIR
    private fun getETag() = "ETAG"
    private fun getManifestContent(): String {
        return "{\n" +
            "   \"1.0.0\":{\n" +
            "      \"en_US\":{\n" +
            "         \"user-manuals\":{\n" +
            "            \"path\":\"${getZippedFileUrl()}\",\n" +
            "            \"fileSize\":123456,\n" +
            "            \"uncompressedSize\":1233113312\n" +
            "         }\n" +
            "      }\n" +
            "   }\n" +
            "}"
    }

    private fun showProgress(progress: Int) {
        // Do nothing
    }

    private fun getSubDir(): String {
        return APP_VERSION
    }

    companion object {
        private const val APP_VERSION = "1.0.0"
        private const val LOCALE = DownloadStaticContent.LocaleType.EN_US
        private const val FILE_KEY = "user-manuals"
        private const val HEADER_KEY_ETAG = "ETag"
        private const val CONTEXT_FILES_DIR = "data/com.test/files"
    }
}