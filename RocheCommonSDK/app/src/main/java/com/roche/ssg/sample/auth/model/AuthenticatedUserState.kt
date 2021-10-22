package com.roche.ssg.sample.auth.model

data class AuthenticatedUserState(
    val authenticationToken: AuthenticationToken?,
    val userType: String?,
    val signupCode: String?
)