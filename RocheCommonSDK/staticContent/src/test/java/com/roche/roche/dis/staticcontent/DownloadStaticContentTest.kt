package com.roche.roche.dis.staticcontent

import android.app.Application
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
    }

    @Test
    fun `when downloadStaticAssets is successful then returns unzipped file path`() = runBlocking {
        coEvery {
            DownloadStaticContent.getUrlFromManifest(
                appContext,
                getManifestUrl(),
                APP_VERSION,
                LOCALE
            )
        } returns getZippedFileUrl()
        coEvery {
            DownloadStaticContent.downloadFromUrl(
                appContext,
                getZippedFileUrl(),
                any()
            )
        } returns getZippedFilePath()
        every {
            DownloadStaticContent.unzipFile(
                appContext,
                APP_VERSION,
                LOCALE,
                getZippedFilePath(),
                getDirectoryName()
            )
        } returns getUnzippedFilePath()

        val path = DownloadStaticContent.downloadStaticAssets(
            appContext,
            getManifestUrl(),
            APP_VERSION,
            LOCALE,
            ::showProgress
        )
        Assert.assertEquals(getUnzippedFilePath(), path)
        coVerify(exactly = 1) {
            DownloadStaticContent.getUrlFromManifest(
                appContext,
                getManifestUrl(),
                APP_VERSION,
                LOCALE
            )
        }
        coVerify(exactly = 1) {
            DownloadStaticContent.downloadFromUrl(
                appContext,
                getZippedFileUrl(),
                any()
            )
        }
        verify(exactly = 1) {
            DownloadStaticContent.unzipFile(
                appContext,
                APP_VERSION,
                LOCALE,
                getZippedFilePath(),
                getDirectoryName()
            )
        }
    }

    @Test
    fun `when downloadStaticAssets throws EXCEPTION_INVALID_MANIFEST_FILE_FORMAT exception when file extension is not zip type`() =
        runBlocking {
            coEvery {
                DownloadStaticContent.getUrlFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE
                )
            } returns "ZippedFileUrl.txt"

            try {
                DownloadStaticContent.downloadStaticAssets(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE,
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
                DownloadStaticContent.getUrlFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE
                )
            }
            coVerify(exactly = 0) {
                DownloadStaticContent.downloadFromUrl(appContext, any(), any())
            }
            verify(exactly = 0) {
                DownloadStaticContent.unzipFile(appContext, any(), any(), any(), any())
            }
        }

    @Test
    fun `when downloadStaticAssets returns existing unzipped file path if EXCEPTION_NOT_MODIFIED received`() =
        runBlocking {
            coEvery {
                DownloadStaticContent.getUrlFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE
                )
            } throws IllegalStateException(DownloadStaticContent.EXCEPTION_NOT_MODIFIED)
            every {
                DownloadStaticContentSharedPref.getDownloadedFilePath(
                    appContext,
                    APP_VERSION,
                    LOCALE
                )
            } returns getUnzippedFilePath()

            val path = DownloadStaticContent.downloadStaticAssets(
                appContext,
                getManifestUrl(),
                APP_VERSION,
                LOCALE,
                ::showProgress
            )
            Assert.assertEquals(getUnzippedFilePath(), path)
            coVerify(exactly = 1) {
                DownloadStaticContent.getUrlFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE
                )
            }
            verify(exactly = 1) {
                DownloadStaticContentSharedPref.getDownloadedFilePath(
                    appContext,
                    APP_VERSION,
                    LOCALE
                )
            }
            coVerify(exactly = 0) {
                DownloadStaticContent.downloadFromUrl(appContext, any(), any())
            }
            verify(exactly = 0) {
                DownloadStaticContent.unzipFile(appContext, any(), any(), any(), any())
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
                LOCALE
            )
        } returns ""
        every {
            DownloadStaticContentSharedPref.getDownloadedFilePath(
                appContext,
                APP_VERSION,
                LOCALE
            )
        } returns ""
        every { httpURLConnection.responseCode } returns HttpURLConnection.HTTP_OK
        every { httpURLConnection.headerFields } returns mapOf(HEADER_KEY_ETAG to listOf(getETag()))
        every { DownloadStaticContent.readStream(any()) } returns getManifestContent()
        every {
            DownloadStaticContentSharedPref.saveETag(
                appContext,
                APP_VERSION,
                LOCALE,
                getETag()
            )
        } returns Unit

        val zippedUrl = DownloadStaticContent.getUrlFromManifest(
            appContext,
            getManifestUrl(),
            APP_VERSION,
            LOCALE
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
                    LOCALE
                )
            } returns getETag()
            every {
                DownloadStaticContentSharedPref.getDownloadedFilePath(
                    appContext,
                    APP_VERSION,
                    LOCALE
                )
            } returns getUnzippedFilePath()
            every { httpURLConnection.responseCode } returns HttpURLConnection.HTTP_NOT_MODIFIED

            try {
                DownloadStaticContent.getUrlFromManifest(
                    appContext,
                    getManifestUrl(),
                    APP_VERSION,
                    LOCALE
                )
                Assert.fail("unzipFile should have thrown EXCEPTION_NOT_MODIFIED error")
            } catch (e: IllegalStateException) {
                Assert.assertEquals(DownloadStaticContent.EXCEPTION_NOT_MODIFIED, e.message)
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
    fun `when unzipFile is successful then returns unzipped file path`() {
        every {
            UnZipUtils.unzipFromAppFiles(
                getZippedFilePath(),
                appContext
            )
        } returns getUnzippedFilePath()
        every {
            DownloadStaticContentSharedPref.saveDownloadedFilePath(
                appContext,
                APP_VERSION,
                LOCALE,
                getUnzippedFilePath()
            )
        } returns Unit

        val unzippedPath =
            DownloadStaticContent.unzipFile(appContext, APP_VERSION, LOCALE, getZippedFilePath())
        verify(exactly = 1) {
            DownloadStaticContentSharedPref.saveDownloadedFilePath(
                appContext,
                APP_VERSION,
                LOCALE,
                getUnzippedFilePath()
            )
        }
        Assert.assertEquals(getUnzippedFilePath(), unzippedPath)
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
            DownloadStaticContent.unzipFile(appContext, APP_VERSION, LOCALE, getZippedFilePath())
            Assert.fail("unzipFile didn't throw EXCEPTION_UNZIPPING_FILE error")
        } catch (e: IllegalStateException) {
            Assert.assertEquals(DownloadStaticContent.EXCEPTION_UNZIPPING_FILE, e.message)
        }
        verify(exactly = 0) {
            DownloadStaticContentSharedPref.saveDownloadedFilePath(
                appContext,
                APP_VERSION,
                LOCALE,
                getUnzippedFilePath()
            )
        }
    }

    private fun getManifestUrl() = "https://domain-name/docs/manifest.json"
    private fun getZippedFileUrl() = "https://domain-name/docs/zipped-file.zip"
    private fun getZippedFilePath() = CONTEXT_FILES_DIR + File.separator + "zipped-file.zip"
    private fun getUnzippedFilePath() = CONTEXT_FILES_DIR
    private fun getETag() = "ETAG"
    private fun getContentLength() = 100
    private fun getManifestContent(): String {
        return "{\n" +
            " \"1.2.1\": {\n" +
            "     \"en-us\": \"https://domain-name/docs/zipped-file.zip\"\n" +
            "  }\n" +
            "}"
    }

    private fun showProgress(progress: Int) {
        // Do nothing
    }

    private fun getDirectoryName(): String {
        val zippedFilePath = getZippedFilePath()
        return zippedFilePath.substring(
            zippedFilePath.lastIndexOf("/") + 1,
            zippedFilePath.lastIndexOf(".")
        )
    }

    companion object {
        private const val APP_VERSION = "1.2.1"
        private const val LOCALE = DownloadStaticContent.LocaleType.EN_US
        private const val HEADER_KEY_ETAG = "ETag"
        private const val CONTEXT_FILES_DIR = "data/com.test/files"
    }
}