package com.roche.ssg.sample.auth.model

data class CognitoResponse(
    val responseCode: Int,
    var message: String,
) {
    val isSuccess: Boolean
        get() = responseCode in 200..201

    override fun toString(): String {
        return "CognitoResponse(responseCode=$responseCode, message=$message)"
    }
}
