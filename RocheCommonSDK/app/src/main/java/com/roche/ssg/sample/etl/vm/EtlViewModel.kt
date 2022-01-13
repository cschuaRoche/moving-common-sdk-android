package com.roche.ssg.sample.etl.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.AmplifyConfiguration
import com.amplifyframework.core.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class EtlViewModel(application: Application) : AndroidViewModel(application) {

    val etlStates = MutableLiveData<EtlViewState>()

    init {
        initAmplify()
    }

    private fun initAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            val configuration = AmplifyConfiguration.builder(
                getApplication(),
                Resources.getRawResourceId(getApplication(), "etl_amplify_configuration")
            ).build()
            Amplify.configure(configuration, getApplication())
        } catch (error: AmplifyException) {
            if (error !is Amplify.AlreadyConfiguredException)
                etlStates.postValue(
                    EtlViewState(
                        EtlResult.AmplifyError
                    )
                )
        }
    }

    fun login(
        username: String = "ssg-etl-patient-1@putsbox.com",
        password: String = "DHPAutomation@123"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Amplify.Auth.signIn(username, password, { success ->
                if (success.isSignInComplete) {
                    etlStates.postValue(
                        EtlViewState(
                            EtlResult.LoginSuccess
                        )
                    )
                } else {
                    etlStates.postValue(
                        EtlViewState(
                            EtlResult.LoginFailed
                        )
                    )
                }
            }, {
                etlStates.postValue(
                    EtlViewState(
                        EtlResult.LoginFailed
                    )
                )
            })
        }
    }

    fun getPreSignedUrl() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("Testing", "getting user info")
            val connection =
                URL("https://alic7sdeef.execute-api.us-east-1.amazonaws.com/api/signed-url").openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("charset", "utf-8")
            connection.setRequestProperty(
                "Authorization",
                "Bearer ${AWSMobileClient.getInstance().tokens.idToken.tokenString}"
            )
            connection.setRequestProperty("dhp-assessment-id", "draw_shape")
            connection.setRequestProperty("dhp-app-id", "com.roche.dummy")
            connection.setRequestProperty("x-amz-tagging", "key1=value1&key2=value2&key3=value3")

            if (isSuccess(connection)) {
                val response = connection.inputStream.bufferedReader().readText()
                //val userInfo = Gson().fromJson(response, UserInfo::class.java)
                //Result.success(userInfo)
                Log.i("Testing", "Success $response")
            } else {
                //Result.failure(Exception(connection.errorStream.bufferedReader().readText()))
                Log.i("Testing", "failure ${connection.errorStream.bufferedReader().readText()}")
            }
        }
    }

    private fun isSuccess(connection: HttpURLConnection) =
        connection.responseCode >= HttpURLConnection.HTTP_OK
                && connection.responseCode < HttpURLConnection.HTTP_MULT_CHOICE

    sealed class EtlResult {
        object AmplifyError : EtlResult()
        object LoginSuccess : EtlResult()
        object LoginFailed : EtlResult()
    }
}

data class EtlViewState(val result: EtlViewModel.EtlResult)