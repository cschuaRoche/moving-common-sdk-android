package com.roche.roche.dis.auth.model

data class AuthenticatedUserState(
    val authenticationToken: AuthenticationToken?,
    val userType: String?,
    val signupCode: String?
)