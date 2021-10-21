package com.roche.roche.dis.auth
interface SessionProvider {
    fun refreshTokenIfExpired()
    fun getToken(): String?
}