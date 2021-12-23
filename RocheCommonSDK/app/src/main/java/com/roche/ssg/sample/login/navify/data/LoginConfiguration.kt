package com.roche.ssg.sample.login.navify.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginConfiguration (
    @SerializedName("client_id")
    val clientId: String,

    @SerializedName("client_secret")
    val clientSecret: String,

    @SerializedName("scope")
    val scope: String,

    @SerializedName("redirect_uri")
    val redirectUri: String,

    @SerializedName("response_type")
    val responseType: String,

    @SerializedName("auth_url")
    val authUrl: String,

    @SerializedName("token_url")
    val tokenUrl: String,

    @SerializedName("user_info_url")
    val userInfoUrl: String,

    @SerializedName("logout_url")
    val logoutUrl: String,

    @SerializedName("cognito_logout_url")
    val cognitoLogoutUrl: String,

    @SerializedName("logout_client_id")
    val logoutClientId: String
) : Serializable
