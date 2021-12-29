package com.roche.ssg.sample.login.navify.data

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("expires_in")
    val expiresIn: Long,

    @SerializedName("refresh_expires_in")
    val refreshExpiresIn: Long,

    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("id_token")
    val idToken: String,

    @SerializedName("not-before-policy")
    val notBeforePolicy: Long,

    @SerializedName("session_state")
    val sessionState: String,
    val scope: String
)