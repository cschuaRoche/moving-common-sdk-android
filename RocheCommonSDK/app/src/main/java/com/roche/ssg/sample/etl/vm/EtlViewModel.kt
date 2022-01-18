package com.roche.ssg.sample.etl.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.AmplifyConfiguration
import com.amplifyframework.core.Resources
import com.roche.ssg.etlpipeline.EtlException
import com.roche.ssg.etlpipeline.api.EtlApiClient
import com.roche.ssg.etlpipeline.model.SignedUrlResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EtlViewModel(application: Application) : AndroidViewModel(application) {

    val etlStates = MutableLiveData<EtlViewState>()
    private val xAmzTags: HashMap<String, String> =
        hashMapOf(
            "Key1" to "Value1",
            "Key2" to "Value2",
            "Key3" to "Value3",
            "Key4" to "Value4",
        )

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
        username: String,
        password: String
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

            try {
                val response = EtlApiClient().getPreSignedUrl(
                    "https://alic7sdeef.execute-api.us-east-1.amazonaws.com/api",
                    AWSMobileClient.getInstance().tokens.idToken.tokenString,
                    "draw_shape",
                    "com.roche.dummy",
                    xAmzTags
                )
                etlStates.postValue(
                    EtlViewState(
                        EtlResult.SignedUrlSuccess(response)
                    )
                )
            } catch (e: EtlException) {
                etlStates.postValue(
                    EtlViewState(
                        EtlResult.SignedUrlFailed(e)
                    )
                )
            } catch (e: Exception) {
                etlStates.postValue(
                    EtlViewState(
                        EtlResult.SignedUrlFailed(e)
                    )
                )
            }
        }
    }

    sealed class EtlResult {
        object AmplifyError : EtlResult()
        object LoginSuccess : EtlResult()
        object LoginFailed : EtlResult()
        class SignedUrlSuccess(val response: SignedUrlResponse) : EtlResult()
        class SignedUrlFailed(val error: Exception) : EtlResult()
    }
}

data class EtlViewState(val result: EtlViewModel.EtlResult)