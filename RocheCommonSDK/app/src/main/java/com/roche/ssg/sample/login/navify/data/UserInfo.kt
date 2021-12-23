package com.roche.ssg.sample.login.navify.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    val sub: String,

    @SerializedName("email_verified")
    val emailVerified: String,

    val name: String,

    @SerializedName("preferred_username")
    val preferredUsername: String,

    @SerializedName("given_name")
    val givenName: String,

    @SerializedName("family_name")
    val familyName: String,

    val email: String
)
