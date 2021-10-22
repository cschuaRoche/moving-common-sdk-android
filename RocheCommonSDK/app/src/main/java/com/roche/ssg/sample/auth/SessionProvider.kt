package com.roche.ssg.sample.auth
interface SessionProvider {
    fun refreshTokenIfExpired()
    fun getToken(): String?
}