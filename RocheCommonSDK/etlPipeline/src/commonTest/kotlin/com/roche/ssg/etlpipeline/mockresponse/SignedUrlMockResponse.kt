package com.roche.ssg.etlpipeline.mockresponse

object SignedUrlMockResponse {
    operator fun invoke(): String = "{\n" +
            "\t\"url\": \"https://signed-url\",\n" +
            "\t\"encryptionKeyId\": \"abcfgfgshsh\"\n" +
            "}"
}