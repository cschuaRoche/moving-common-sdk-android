package com.roche.ssg.staticcontent

import android.app.Application
import com.roche.ssg.staticcontent.entity.ManifestInfo
import com.roche.ssg.utils.NetworkUtils
import com.roche.ssg.utils.UnZipUtils
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

    private fun initSuccessfulManifest() {
        coEvery {
            DownloadStaticContent.getInfoFromManifest(
                appContext,
                getManifestUrl(),
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                TARGET_SUB_DIRECTORY,
                false
            )
        } returns getManifestInfo()
    }

    private fun initSuccessfulDownloadFromUrl() {
        val httpURLConnection = mockk<HttpURLConnection>(relaxed = true)
        every { DownloadStaticContent.getUrlConnection(getZippedFileUrl()) } returns httpURLConnection
        every { appContext.filesDir } returns File(CONTEXT_FILES_DIR)
        coEvery { DownloadStaticContent.writeStream(any(), any(), any(), any()) } returns Unit
        coEvery {
            DownloadStaticContent.downloadFromUrl(
                appContext,
                getZippedFileUrl(),
                ::showProgress,
                getSubDir(),
                false
            )
        } returns getZippedFilePath()
    }

    private fun initSuccessfulUnZip() {
        every {
            UnZipUtils.unzipFromAppFiles(
                getZippedFilePath(),
                appContext,
                getSubDir()
            )
        } returns getUnzippedFilePath()

        every {
            UnZipUtils.unzipFromAppFiles(
                getZippedFilePath(),
                appContext
            )
        } returns getUnzippedFilePath()

        every {
            DownloadStaticContent.unzipFile(
                appContext,
                getZippedFilePath(),
                getSubDir()
            )
        } returns getUnzippedFilePath()
    }

    @Test
    fun `when downloadStaticAssets is successful then returns unzipped file path`() = runBlocking {
        initSuccessfulManifest()
        initSuccessfulDownloadFromUrl()
        initSuccessfulUnZip()

        every {
            DownloadStaticContentSharedPref.setFilePath(
                appContext,
                TARGET_SUB_DIRECTORY,
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                getUnzippedFilePath()
            )
        } returns Unit
        every {
            DownloadStaticContent.checkAndDeleteOldVersionData(
                appContext,
                TARGET_SUB_DIRECTORY,
                APP_VERSION
            )
        } returns Unit

        every { appContext.filesDir.usableSpace } returns ORIGINAL_FILE_SIZE + 1
        val path = DownloadStaticContent.downloadStaticAssets(
            appContext,
            getManifestUrl(),
            APP_VERSION,
            LOCALE,
            FILE_KEY,
            ::showProgress,
            TARGET_SUB_DIRECTORY
        )

        coVerify(exactly = 1) {
            DownloadStaticContent.getInfoFromManifest(
                appContext,
                getManifestUrl(),
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                TARGET_SUB_DIRECTORY,
                false
            )
        }

        coVerify(exactly = 1) {
            DownloadStaticContent.downloadFromUrl(
                appContext,
                getZippedFileUrl(),
                ::showProgress,
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
                TARGET_SUB_DIRECTORY,
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                getUnzippedFilePath()
            )
        }
        verify(exactly = 1) {
            DownloadStaticContent.checkAndDeleteOldVersionData(
                appContext,
                TARGET_SUB_DIRECTORY,
                APP_VERSION
            )
        }

        Assert.assertEquals(getUnzippedFilePath(), path)
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
                    TARGET_SUB_DIRECTORY,
                    false
                )
            } returns getManifestInfo(zippedFileUrl = "zippedFileUrl.txt")

            try {
                DownloadStaticContent.downloadStaticAssets(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    ::showProgress,
                    TARGET_SUB_DIRECTORY
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
                    TARGET_SUB_DIRECTORY,
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
    fun `when downloadStaticAssets throws EXCEPTION_INSUFFICIENT_STORAGE exception when disk space is less than compressed file size`() =
        runBlocking {
            coEvery {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    TARGET_SUB_DIRECTORY,
                    false
                )
            } returns getManifestInfo()
            every { appContext.filesDir.usableSpace } returns FILE_SIZE - 1

            try {
                DownloadStaticContent.downloadStaticAssets(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    ::showProgress,
                    TARGET_SUB_DIRECTORY
                )
                Assert.fail("downloadStaticAssets should throw EXCEPTION_INSUFFICIENT_STORAGE error")
            } catch (e: IllegalStateException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_INSUFFICIENT_STORAGE,
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
                    TARGET_SUB_DIRECTORY,
                    false
                )
            }
            coVerify(exactly = 0) {
                DownloadStaticContent.downloadFromUrl(appContext, any(), any(), any(), any())
            }
            verify(exactly = 0) {
                DownloadStaticContent.unzipFile(appContext, any(), any())
            }
            verify(exactly = 1) { appContext.filesDir.usableSpace }
        }

    @Test
    fun `when downloadStaticAssets throws EXCEPTION_INSUFFICIENT_STORAGE exception when disk space is less than original file size`() =
        runBlocking {
            coEvery {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    TARGET_SUB_DIRECTORY,
                    false
                )
            } returns getManifestInfo()
            every { appContext.filesDir.usableSpace } returns ORIGINAL_FILE_SIZE - 1
            coEvery {
                DownloadStaticContent.downloadFromUrl(
                    appContext,
                    getZippedFileUrl(),
                    any(),
                    getSubDir(),
                    false
                )
            } returns getZippedFilePath()

            try {
                DownloadStaticContent.downloadStaticAssets(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    ::showProgress,
                    TARGET_SUB_DIRECTORY
                )
                Assert.fail("downloadStaticAssets should throw EXCEPTION_INSUFFICIENT_STORAGE error")
            } catch (e: IllegalStateException) {
                Assert.assertEquals(
                    DownloadStaticContent.EXCEPTION_INSUFFICIENT_STORAGE,
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
                    TARGET_SUB_DIRECTORY,
                    false
                )
            }
            coVerify(exactly = 1) {
                DownloadStaticContent.downloadFromUrl(appContext, any(), any(), any(), any())
            }
            verify(exactly = 0) {
                DownloadStaticContent.unzipFile(appContext, any(), any())
            }
            verify(exactly = 2) { appContext.filesDir.usableSpace }
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
                    TARGET_SUB_DIRECTORY,
                    false
                )
            } throws IllegalStateException(DownloadStaticContent.EXCEPTION_NOT_MODIFIED)
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    TARGET_SUB_DIRECTORY,
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
                ::showProgress,
                TARGET_SUB_DIRECTORY
            )
            Assert.assertEquals(getUnzippedFilePath(), path)
            coVerify(exactly = 1) {
                DownloadStaticContent.getInfoFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY,
                    TARGET_SUB_DIRECTORY,
                    false
                )
            }
            verify(exactly = 1) {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    TARGET_SUB_DIRECTORY,
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
                TARGET_SUB_DIRECTORY,
                APP_VERSION,
                LOCALE,
                FILE_KEY
            )
        } returns ""
        every {
            DownloadStaticContentSharedPref.getFilePath(
                appContext,
                TARGET_SUB_DIRECTORY,
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
                TARGET_SUB_DIRECTORY,
                APP_VERSION,
                LOCALE,
                FILE_KEY,
                getETag()
            )
        } returns Unit

        val manifestInfo = DownloadStaticContent.getInfoFromManifest(
            appContext,
            getManifestUrl(),
            APP_VERSION,
            LOCALE,
            FILE_KEY,
            TARGET_SUB_DIRECTORY
        )
        Assert.assertEquals(getZippedFileUrl(), manifestInfo.path)
    }

    @Test
    fun `getUrlFromManifest throws EXCEPTION_NOT_MODIFIED when response code is HTTP_NOT_MODIFIED`() =
        runBlocking {
            val httpURLConnection = mockk<HttpURLConnection>(relaxed = true)
            every { DownloadStaticContent.getUrlConnection(getManifestUrl()) } returns httpURLConnection
            every {
                DownloadStaticContentSharedPref.getETag(
                    appContext,
                    TARGET_SUB_DIRECTORY,
                    APP_VERSION,
                    LOCALE,
                    FILE_KEY
                )
            } returns getETag()
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    TARGET_SUB_DIRECTORY,
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
                    FILE_KEY,
                    TARGET_SUB_DIRECTORY
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
                    TARGET_SUB_DIRECTORY,
                    incorrectAppVersion,
                    LOCALE,
                    FILE_KEY
                )
            } returns ""
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    TARGET_SUB_DIRECTORY,
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
                    FILE_KEY,
                    TARGET_SUB_DIRECTORY
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
                        TARGET_SUB_DIRECTORY,
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
                    TARGET_SUB_DIRECTORY,
                    APP_VERSION,
                    incorrectLocale,
                    FILE_KEY
                )
            } returns ""
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    TARGET_SUB_DIRECTORY,
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
                    FILE_KEY,
                    TARGET_SUB_DIRECTORY
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
                        TARGET_SUB_DIRECTORY,
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
                    TARGET_SUB_DIRECTORY,
                    APP_VERSION,
                    LOCALE,
                    incorrectFileKey
                )
            } returns ""
            every {
                DownloadStaticContentSharedPref.getFilePath(
                    appContext,
                    TARGET_SUB_DIRECTORY,
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
                    incorrectFileKey,
                    TARGET_SUB_DIRECTORY
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
                        TARGET_SUB_DIRECTORY,
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
                    FILE_KEY,
                    TARGET_SUB_DIRECTORY
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
                    TARGET_SUB_DIRECTORY,
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
            ::showProgress,
            getSubDir()
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
                    ::showProgress,
                    getSubDir()
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
                    getSubDir(),
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
                appContext,
                getSubDir()
            )
        } returns getUnzippedFilePath()

        val unzippedPath =
            DownloadStaticContent.unzipFile(appContext, getZippedFilePath(), getSubDir())
        Assert.assertEquals(getUnzippedFilePath(), unzippedPath)
        verify(exactly = 1) {
            UnZipUtils.unzipFromAppFiles(
                getZippedFilePath(),
                appContext,
                getSubDir()
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
            DownloadStaticContent.unzipFile(appContext, getZippedFilePath(), getSubDir())
            Assert.fail("unzipFile didn't throw EXCEPTION_UNZIPPING_FILE error")
        } catch (e: IllegalStateException) {
            Assert.assertEquals(DownloadStaticContent.EXCEPTION_UNZIPPING_FILE, e.message)
        }
    }

    @Test
    fun `test checkAndDeleteOldVersionData when existing version in SharedPref doesn't exist`() {
        val existingVersion = ""
        every {
            DownloadStaticContentSharedPref.getVersion(
                appContext,
                TARGET_SUB_DIRECTORY
            )
        } returns existingVersion
        every {
            DownloadStaticContentSharedPref.setVersion(
                appContext,
                TARGET_SUB_DIRECTORY,
                APP_VERSION
            )
        } returns Unit
        DownloadStaticContent.checkAndDeleteOldVersionData(
            appContext,
            TARGET_SUB_DIRECTORY,
            APP_VERSION
        )
        verify(exactly = 1) {
            DownloadStaticContentSharedPref.setVersion(
                appContext,
                TARGET_SUB_DIRECTORY,
                APP_VERSION
            )
        }
        verify(exactly = 0) {
            DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(
                appContext,
                TARGET_SUB_DIRECTORY,
                existingVersion
            )
        }
    }

    @Test
    fun `test checkAndDeleteOldVersionData when newer version is different than existing version`() {
        val existingVersion = "1.0.0"
        val newerVersion = "2.0.0"
        every {
            DownloadStaticContentSharedPref.getVersion(
                appContext,
                TARGET_SUB_DIRECTORY
            )
        } returns existingVersion
        every {
            DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(
                appContext,
                TARGET_SUB_DIRECTORY,
                existingVersion
            )
        } returns Unit
        every {
            DownloadStaticContentSharedPref.setVersion(
                appContext,
                TARGET_SUB_DIRECTORY,
                newerVersion
            )
        } returns Unit

        DownloadStaticContent.checkAndDeleteOldVersionData(
            appContext,
            TARGET_SUB_DIRECTORY,
            newerVersion
        )
        verify(exactly = 1) {
            DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(
                appContext,
                TARGET_SUB_DIRECTORY,
                existingVersion
            )
        }
        verify(exactly = 1) {
            DownloadStaticContentSharedPref.setVersion(
                appContext,
                TARGET_SUB_DIRECTORY,
                newerVersion
            )
        }
    }

    @Test
    fun `test checkAndDeleteOldVersionData when newer version is similar to the existing version`() {
        val existingVersion = "1.0.0"
        val newerVersion = "1.0.0"
        every {
            DownloadStaticContentSharedPref.getVersion(
                appContext,
                TARGET_SUB_DIRECTORY
            )
        } returns existingVersion

        DownloadStaticContent.checkAndDeleteOldVersionData(
            appContext,
            TARGET_SUB_DIRECTORY,
            newerVersion
        )
        verify(exactly = 0) {
            DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(
                appContext,
                TARGET_SUB_DIRECTORY,
                existingVersion
            )
        }
        verify(exactly = 0) {
            DownloadStaticContentSharedPref.setVersion(
                appContext,
                TARGET_SUB_DIRECTORY,
                newerVersion
            )
        }
    }

    private fun getManifestUrl() = "https://domain-name/docs/manifest.json"
    private fun getZippedFileUrl() = "https://domain-name/docs/zipped-file.zip"
    private fun getManifestInfo(zippedFileUrl: String = getZippedFileUrl()) =
        ManifestInfo(zippedFileUrl, FILE_SIZE, ORIGINAL_FILE_SIZE)

    private fun getZippedFilePath() = CONTEXT_FILES_DIR + File.separator + getSubDir() + File.separator + "zipped-file.zip"
    private fun getUnzippedFilePath() = CONTEXT_FILES_DIR + File.separator + getSubDir()
    private fun getETag() = "ETAG"
    private fun getManifestContent(): String {
        return "{\n" +
            "   \"1.0.0\":{\n" +
            "      \"en_US\":{\n" +
            "         \"user-manuals\":{\n" +
            "            \"path\":\"${getZippedFileUrl()}\",\n" +
            "            \"fileSize\":$FILE_SIZE,\n" +
            "            \"originalSize\":$ORIGINAL_FILE_SIZE\n" +
            "         }\n" +
            "      }\n" +
            "   }\n" +
            "}"
    }

    private fun showProgress(progress: Int) {
        // Do nothing
    }

    private fun getSubDir(): String {
        return TARGET_SUB_DIRECTORY + File.separator + APP_VERSION
    }

    companion object {
        private const val APP_VERSION = "1.0.0"
        private const val LOCALE = DownloadStaticContent.LocaleType.EN_US
        private const val FILE_KEY = "user-manuals"
        private const val TARGET_SUB_DIRECTORY = "subDirectory"
        private const val HEADER_KEY_ETAG = "ETag"
        private const val CONTEXT_FILES_DIR = "data/com.test/files"
        private const val FILE_SIZE = 100L
        private const val ORIGINAL_FILE_SIZE = 500L
    }
}