package com.roche.roche.dis.staticcontent

import android.app.Application
import com.roche.roche.dis.utils.UnZipUtils
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DownloadStaticContentTest : BaseMockkTest() {
    @MockK(relaxed = true)
    private lateinit var appContext: Application

    @Before
    override fun setup() {
        super.setup()
        mockkObject(DownloadStaticContentSharedPref)
        mockkObject(UnZipUtils)
    }

    @Test
    fun `when unzipFile is successful then returns unzipped file path`() {
        every {
            UnZipUtils.unzipFromAppFiles(
                getZippedFilePath(),
                "",
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
                "",
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

    private fun getZippedFilePath() = "ZIPPED_FILE_PATH"
    private fun getUnzippedFilePath() = "UNZIPPED_FILE_PATH"

    companion object {
        private const val APP_VERSION = "1.0.0"
        private const val LOCALE = DownloadStaticContent.LocaleType.EN_US
    }
}