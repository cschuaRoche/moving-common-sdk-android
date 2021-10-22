package com.roche.ssg.sample.auth.model

import java.util.Date


data class AuthenticationToken(val tokenString: String, val expirationDate: Date) : Any()