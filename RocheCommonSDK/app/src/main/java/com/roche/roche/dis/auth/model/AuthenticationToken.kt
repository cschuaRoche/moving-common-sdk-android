package com.roche.roche.dis.auth.model

import java.util.*


data class AuthenticationToken(val tokenString: String, val expirationDate: Date) : Any()