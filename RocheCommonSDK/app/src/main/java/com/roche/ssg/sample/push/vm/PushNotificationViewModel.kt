package com.roche.ssg.sample.push.vm

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.google.android.gms.tasks.Tasks
import com.google.firebase.messaging.FirebaseMessaging
import com.roche.ssg.pushnotification.PushNotificationException
import com.roche.ssg.pushnotification.api.PushNotificationApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PushNotificationViewModel(application: Application) : AndroidViewModel(application) {


    val pushNotificationStates = MutableLiveData<PushNotificationViewState>()

    init {
        initAmplify()
    }

    private fun initAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(getApplication())
        } catch (error: AmplifyException) {
            if(!(error is Amplify.AlreadyConfiguredException))
            pushNotificationStates.postValue(
                PushNotificationViewState(
                    PushNotificationResult.AmplifyError
                )
            )
        }
    }

    fun login(
        username: String = "devandroiduser@mailinator.com",
        password: String = "Test@1234"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Amplify.Auth.signIn(username, password, { success ->
                if (success.isSignInComplete) {
                    pushNotificationStates.postValue(
                        PushNotificationViewState(
                            PushNotificationResult.LoginSuccess
                        )
                    )
                } else {
                    pushNotificationStates.postValue(
                        PushNotificationViewState(
                            PushNotificationResult.LoginFailed
                        )
                    )
                }
            }, {
                pushNotificationStates.postValue(
                    PushNotificationViewState(
                        PushNotificationResult.LoginFailed
                    )
                )
            })
        }
    }

    private fun getAuthToken(): String? = AWSMobileClient.getInstance().tokens.idToken.tokenString

    private fun getFirebaseToken(): String? {
        val task = FirebaseMessaging.getInstance().token
        Tasks.await(task)
        return task.result
    }

    fun registerDevice(
        baseUrl: String = "https://floodlight.dhp-dev.dhs.platform.navify.com",
        appId: String = "test", appVersion: String = "1.3.1", country: String = "us",
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = PushNotificationApi().registerDevice(
                    baseUrl,
                    appId,
                    Amplify.Auth.currentUser.userId,
                    getFirebaseToken()!!,
                    appVersion,
                    country,
                    getAuthToken()!!
                )
                Log.i("RegisterPushFragment", "Response from Server $response")
                pushNotificationStates.postValue(
                    PushNotificationViewState(
                        PushNotificationResult.RegistrationSuccess(response)
                    )
                )
            } catch (e: PushNotificationException) {
                Log.e("RegisterPushFragment", "Error", e)
                pushNotificationStates.postValue(
                    PushNotificationViewState(
                        PushNotificationResult.RegistrationFailed(e)
                    )
                )
            } catch (e: Exception) {
                Log.e("RegisterPushFragment", "Error", e)
                pushNotificationStates.postValue(
                    PushNotificationViewState(
                        PushNotificationResult.RegistrationFailed(e)
                    )
                )
            }
        }
    }

    fun deregisterDevice(
        baseUrl: String = "https://floodlight.dhp-dev.dhs.platform.navify.com",
        appId: String = "test"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = PushNotificationApi().deregisterDevice(
                    baseUrl,
                    appId,
                    Amplify.Auth.currentUser.userId,
                    getFirebaseToken()!!,
                    getAuthToken()!!
                )
                Log.i("RegisterPushFragment", "Response from Server $response")
                pushNotificationStates.postValue(
                    PushNotificationViewState(
                        PushNotificationResult.DeRegistrationSuccess(response)
                    )
                )
            } catch (e: PushNotificationException) {
                Log.e("RegisterPushFragment", "Error", e)
                pushNotificationStates.postValue(
                    PushNotificationViewState(
                        PushNotificationResult.DeRegistrationFailed(e)
                    )
                )
            } catch (e: Exception) {
                Log.e("RegisterPushFragment", "Error", e)
                pushNotificationStates.postValue(
                    PushNotificationViewState(
                        PushNotificationResult.DeRegistrationFailed(e)
                    )
                )
            }
        }
    }

    sealed class PushNotificationResult {
        object AmplifyError : PushNotificationResult()
        object LoginSuccess : PushNotificationResult()
        object LoginFailed : PushNotificationResult()

        class RegistrationSuccess(val response: String) : PushNotificationResult()
        class RegistrationFailed(val error: Exception) : PushNotificationResult()

        class DeRegistrationSuccess(val response: String) : PushNotificationResult()
        class DeRegistrationFailed(val error: Exception) : PushNotificationResult()
    }

    fun areNotificationsEnabled(notificationManager: NotificationManagerCompat) = when {
        notificationManager.areNotificationsEnabled().not() -> false
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            notificationManager.notificationChannels.firstOrNull { channel ->
                channel.importance == NotificationManager.IMPORTANCE_NONE
            } == null
        }
        else -> true
    }
}

data class PushNotificationViewState(val result: PushNotificationViewModel.PushNotificationResult)

