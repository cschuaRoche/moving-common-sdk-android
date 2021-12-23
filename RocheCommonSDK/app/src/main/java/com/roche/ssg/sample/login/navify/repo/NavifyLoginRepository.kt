package com.roche.ssg.sample.login.navify.repo

import android.util.Log
import com.google.gson.Gson
import com.roche.ssg.sample.login.navify.data.LoginConfiguration
import com.roche.ssg.sample.login.navify.data.Token
import com.roche.ssg.sample.login.navify.data.UserInfo
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class NavifyLoginRepository @Inject constructor() {

    fun getToken(authCode: String, loginConfiguration: LoginConfiguration): Result<Token> {
        Log.i("Testing", "getting token")
        val params =
            "code=$authCode" +
                    "&grant_type=authorization_code" +
                    "&redirect_uri=${loginConfiguration.redirectUri}" +
                    "&client_secret=${loginConfiguration.clientSecret}" +
                    "&client_id=${loginConfiguration.clientId}"

        val postData: ByteArray = params.toByteArray(StandardCharsets.UTF_8)
        val postDataLength = postData.size
        val connection =
            URL(loginConfiguration.tokenUrl).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-Length", postDataLength.toString())

        DataOutputStream(connection.outputStream).use { wr -> wr.write(postData) }

        return if (isSuccess(connection)) {
            val response = connection.inputStream.bufferedReader().readText()
            val token = Gson().fromJson(response, Token::class.java)
            Result.success(token)
        } else {
            val error = connection.errorStream.bufferedReader().readText()
            Result.failure(Exception(error))
        }
    }

    fun getUserInfo(loginConfiguration: LoginConfiguration, token: Token): Result<UserInfo> {
        Log.i("Testing", "getting user info")
        val connection =
            URL(loginConfiguration.userInfoUrl).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Authorization", "Bearer ${token.accessToken}")

        return if (isSuccess(connection)) {
            val response = connection.inputStream.bufferedReader().readText()
            val userInfo = Gson().fromJson(response, UserInfo::class.java)
            Result.success(userInfo)
        } else {
            Result.failure(Exception(connection.errorStream.bufferedReader().readText()))
        }
    }

    fun logout(loginConfiguration: LoginConfiguration, token: Token): Result<String> {
        Log.i("Testing", "logging out")
        val params =
            "client_secret=${loginConfiguration.clientSecret}&client_id=${loginConfiguration.clientId}&refresh_token=${token.refreshToken}"
        val postData: ByteArray = params.toByteArray(StandardCharsets.UTF_8)
        val postDataLength = postData.size
        val connection =
            URL(loginConfiguration.logoutUrl).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-Length", postDataLength.toString())
        connection.setRequestProperty("Authorization", "Bearer ${token.accessToken}")

        DataOutputStream(connection.outputStream).use { wr -> wr.write(postData) }

        return if (isSuccess(connection)) {
            Result.success(connection.inputStream.bufferedReader().readText())

        } else {
            Result.failure(Exception(connection.errorStream.bufferedReader().readText()))
        }
    }

    private fun isSuccess(connection: HttpURLConnection) =
        connection.responseCode >= HttpURLConnection.HTTP_OK
                && connection.responseCode < HttpURLConnection.HTTP_MULT_CHOICE

}