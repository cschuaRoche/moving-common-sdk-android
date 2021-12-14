package com.roche.ssg.sample.login.navify.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.roche.ssg.sample.login.navify.data.Token
import com.roche.ssg.sample.login.navify.data.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets


class NavifyLoginViewModel : ViewModel() {

    val navifyLoginViewState = MutableLiveData<NavifyLoginViewState>()
    private lateinit var token: Token

    init {
        navifyLoginViewState.postValue(NavifyLoginViewState(NavifyLoginResult.LoginPending))
    }

    fun validateAuthCode(authCode: String?) {
        if (authCode == null) {
            navifyLoginViewState.postValue(NavifyLoginViewState(NavifyLoginResult.LoginFailed))
        } else {
            getToken(authCode)
        }
    }

    private fun getToken(authCode: String) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.i("Testing", "getting token")
                val params =
                    "code=$authCode&grant_type=authorization_code&redirect_uri=roche://com.roche.ssg/auth/callback&client_secret=aec38cd1-24fe-4ed9-8990-9af376100930&client_id=ssg-dev-reference-app-patient"
                val postData: ByteArray = params.toByteArray(StandardCharsets.UTF_8)
                val postDataLength = postData.size
                val connection =
                    URL("https://keycloak.appdevus.platform.navify.com/auth/realms/patients/protocol/openid-connect/token").openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                connection.setRequestProperty("charset", "utf-8")
                connection.setRequestProperty("Content-Length", postDataLength.toString())

                DataOutputStream(connection.outputStream).use { wr -> wr.write(postData) }

                if (connection.responseCode >= HttpURLConnection.HTTP_OK
                    && connection.responseCode < HttpURLConnection.HTTP_MULT_CHOICE
                ) {
                    val response = connection.inputStream.bufferedReader().readText()

                    token = Gson().fromJson(
                        response,
                        Token::class.java
                    )
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LoginSuccess(token)
                        )
                    )

                    getUserInfo(token)
                } else {
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LoginFailed
                        )
                    )
                    connection.errorStream.bufferedReader().readText()
                }
            }
        }
    }

    private fun getUserInfo(token: Token) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val connection =
                    URL("https://keycloak.appdevus.platform.navify.com/auth/realms/patients/protocol/openid-connect/userinfo").openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("charset", "utf-8")
                connection.setRequestProperty("Authorization", "Bearer ${token.accessToken}")

                if (connection.responseCode >= HttpURLConnection.HTTP_OK
                    && connection.responseCode < HttpURLConnection.HTTP_MULT_CHOICE
                ) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val userInfo = Gson().fromJson(
                        response,
                        UserInfo::class.java
                    )
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LoginUserInfo(
                                userInfo
                            )
                        )
                    )
                } else {
                    connection.errorStream.bufferedReader().readText()
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LoginUserInfoFailed
                        )
                    )
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                Log.i("Testing", "getting token")
                val params =
                    "client_secret=aec38cd1-24fe-4ed9-8990-9af376100930&client_id=ssg-dev-reference-app-patient&refresh_token=${token.refreshToken}"
                val postData: ByteArray = params.toByteArray(StandardCharsets.UTF_8)
                val postDataLength = postData.size
                val connection =
                    URL("https://keycloak.appdevus.platform.navify.com/auth/realms/patients/protocol/openid-connect/logout").openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                connection.setRequestProperty("charset", "utf-8")
                connection.setRequestProperty("Content-Length", postDataLength.toString())
                connection.setRequestProperty("Authorization", "Bearer ${token.accessToken}")

                DataOutputStream(connection.outputStream).use { wr -> wr.write(postData) }

                if (connection.responseCode >= HttpURLConnection.HTTP_OK
                    && connection.responseCode < HttpURLConnection.HTTP_MULT_CHOICE
                ) {
                    val response = connection.inputStream.bufferedReader().readText()
                    Log.i("Testing","Logout successful $response")
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LogoutSuccessful
                        )
                    )
                } else {
                    val response = connection.errorStream.bufferedReader().readText()
                    Log.i("Testing","Logout failed $response")
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LogoutFailed
                        )
                    )
                }
            }
        }
    }

    sealed class NavifyLoginResult {
        object LoginPending : NavifyLoginResult()
        class LoginSuccess(val token: Token) : NavifyLoginResult()
        object LoginFailed : NavifyLoginResult()
        class LoginUserInfo(val user: UserInfo) : NavifyLoginResult()
        object LoginUserInfoFailed : NavifyLoginResult()
        object LogoutSuccessful : NavifyLoginResult()
        object LogoutFailed : NavifyLoginResult()
    }
}

data class NavifyLoginViewState(val result: NavifyLoginViewModel.NavifyLoginResult)