package com.roche.apprecall.mockresponses

object SamdMockResponse {
    operator fun invoke(): String =
        "{\n" +
                "    \"com.roche.ssg.test.samd.one\": {\n" +
                "        \"recall\": false\n" +
                "    },\n" +
                "    \"com.roche.ssg.test.samd.two\": {\n" +
                "        \"recall\": true\n" +
                "    }\n" +
                "}"
}