package com.roche.apprecall.mockresponses

object AppRecallResponse {
    operator fun invoke(): String =
        "{\n" +
                "    \"updateAvailable\": false,\n" +
                "    \"updateRequired\": false,\n" +
                "    \"recall\": true\n" +
                "}"
}