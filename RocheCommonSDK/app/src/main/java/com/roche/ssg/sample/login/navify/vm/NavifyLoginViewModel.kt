package com.roche.ssg.sample.login.navify.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.roche.ssg.sample.R
import com.roche.ssg.sample.login.navify.data.LoginConfiguration
import com.roche.ssg.sample.login.navify.data.Token
import com.roche.ssg.sample.login.navify.data.UserInfo
import com.roche.ssg.sample.login.navify.repo.NavifyLoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NavifyLoginViewModel @Inject constructor(
    application: Application,
    private val repository: NavifyLoginRepository
) :
    AndroidViewModel(application) {

    val navifyLoginViewState = MutableLiveData<NavifyLoginViewState>()
    private lateinit var token: Token
    lateinit var loginConfiguration: LoginConfiguration

    init {
        navifyLoginViewState.postValue(NavifyLoginViewState(NavifyLoginResult.LoginPending))
        initConfiguration()
    }

    private fun initConfiguration() {
        Log.i("Testing", "Initializing NavifyLoginViewModel")
        val inputStream =
            getApplication<Application>().resources.openRawResource(R.raw.navify_login_configuration)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        loginConfiguration = Gson().fromJson(jsonString, LoginConfiguration::class.java)
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
                try {
                    val result = repository.getToken(authCode, loginConfiguration)
                    token = result.getOrThrow()
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LoginSuccess(token)
                        )
                    )
                    getUserInfo(token)

                } catch (e: Exception) {
                    e.printStackTrace()
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LoginFailed
                        )
                    )
                }
            }
        }
    }

    private fun getUserInfo(token: Token) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = repository.getUserInfo(loginConfiguration, token)
                    val userInfo = result.getOrThrow()
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LoginUserInfo(
                                userInfo
                            )
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
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
            withContext(Dispatchers.IO) {
                try {
                    val result = repository.logout(loginConfiguration, token)
                    result.getOrThrow()
                    navifyLoginViewState.postValue(
                        NavifyLoginViewState(
                            NavifyLoginResult.LogoutSuccessful
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
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