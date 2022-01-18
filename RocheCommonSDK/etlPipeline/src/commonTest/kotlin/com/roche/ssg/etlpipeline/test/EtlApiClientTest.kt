package com.roche.ssg.etlpipeline.test

import com.roche.ssg.etlpipeline.EtlException
import com.roche.ssg.etlpipeline.api.EtlApiClient
import com.roche.ssg.etlpipeline.runBlockingTest
import com.roche.ssg.etlpipeline.util.ApiMockEngine
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class EtlApiClientTest {
    private val apiMockEngine = ApiMockEngine()
    private val apiMock = EtlApiClient(apiMockEngine.get())

    @Test
    fun shouldGetValidResponse() = runBlockingTest {

        val xAmzTags: HashMap<String, String> =
            hashMapOf(
                "Key1" to "Value1",
                "Key2" to "Value2",
                "Key3" to "Value3",
                "Key4" to "Value4",
            )

        val response = apiMock.getPreSignedUrl(
            "https://baseurl",
            "valid_auth_token",
            "draw_shape",
            "com.roche.dummy",
            xAmzTags
        )
        assertNotNull(response)
    }

    @Test
    fun shouldThrowExceptionIfAuthTokenIsWrong() = runBlockingTest {
        val xAmzTags: HashMap<String, String> =
            hashMapOf(
                "Key1" to "Value1",
                "Key2" to "Value2",
                "Key3" to "Value3",
                "Key4" to "Value4",
            )

        assertFailsWith<EtlException> {
            apiMock.getPreSignedUrl(
                "https://baseurl",
                "invalid_mock_auth_token",
                "draw_shape",
                "com.roche.dummy",
                xAmzTags
            )
        }
    }

    @Test
    fun shouldThrowExceptionIfDhpAssessmentIdIsEmpty() = runBlockingTest {
        val xAmzTags: HashMap<String, String> =
            hashMapOf(
                "Key1" to "Value1",
                "Key2" to "Value2",
                "Key3" to "Value3",
                "Key4" to "Value4",
            )

        assertFailsWith<EtlException> {
            apiMock.getPreSignedUrl(
                "https://baseurl",
                "valid_auth_token",
                "",
                "com.roche.dummy",
                xAmzTags
            )
        }
    }

    @Test
    fun shouldThrowExceptionIfDhpAAppIdIsEmpty() = runBlockingTest {
        val xAmzTags: HashMap<String, String> =
            hashMapOf(
                "Key1" to "Value1",
                "Key2" to "Value2",
                "Key3" to "Value3",
                "Key4" to "Value4",
            )

        assertFailsWith<EtlException> {
            apiMock.getPreSignedUrl(
                "https://baseurl",
                "valid_auth_token",
                "draw_shape",
                "",
                xAmzTags
            )
        }
    }
}