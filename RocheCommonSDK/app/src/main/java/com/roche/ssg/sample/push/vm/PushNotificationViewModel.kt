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
import com.roche.ssg.pushnotification.model.DeregisterResponse
import com.roche.ssg.pushnotification.model.RegisterResponse
import com.roche.ssg.sample.firebase.MyFirebaseMessagingService
import com.roche.ssg.utils.PreferenceUtil
import com.roche.ssg.utils.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PushNotificationViewModel(application: Application) : AndroidViewModel(application) {

    val pushNotificationStates = MutableLiveData<PushNotificationViewState>()

    companion object {
        const val APP_PUSH_REGISTRATION_PREFS = "PUSH_REGISTRATION_PREFS"
        const val PREF_KEY_IS_REGISTER = "key_is_register"
    }

    init {
        initAmplify()
    }

    private fun initAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(getApplication())
        } catch (error: AmplifyException) {
            if (!(error is Amplify.AlreadyConfiguredException))
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
        appId: String = "com.roche.floodlight",
        appVersion: String = "1.3.1",
        country: String = "us",
    ) {
        if (getIsRegistration()) {
            pushNotificationStates.postValue(
                PushNotificationViewState(
                    PushNotificationResult.AlreadyRegistered
                )
            )
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = PushNotificationApi().registerDevice(
                    baseUrl,
                    appId,
                    Amplify.Auth.currentUser.userId,
                    getFirebaseToken()!!,
                    getAuthToken()!!,
                    appVersion,
                    country,
                )
                Log.i("RegisterPushFragment", "Response from Server $response")
                saveIsRegistration(true)
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
        appId: String = "com.roche.floodlight"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = PushNotificationApi().deregisterDevice(
                    baseUrl,
                    appId,
                    Amplify.Auth.currentUser.userId,
                    getAuthToken()!!,
                    getFirebaseToken()!!
                )
                Log.i("RegisterPushFragment", "Response from Server $response")
                saveIsRegistration(false)
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

        class RegistrationSuccess(val response: RegisterResponse) : PushNotificationResult()
        class RegistrationFailed(val error: Exception) : PushNotificationResult()
        object AlreadyRegistered : PushNotificationResult()

        class DeRegistrationSuccess(val response: DeregisterResponse) : PushNotificationResult()
        class DeRegistrationFailed(val error: Exception) : PushNotificationResult()
    }

    fun isNotificationsEnabled(notificationManager: NotificationManagerCompat) = when {
        notificationManager.areNotificationsEnabled().not() -> false
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            notificationManager.notificationChannels.firstOrNull { channel ->
                channel.importance == NotificationManager.IMPORTANCE_NONE
            } == null
        }
        else -> true
    }

    private fun getIsRegistration(): Boolean {
        val pref = PreferenceUtil.createOrGetPreference(
            getApplication(),
            APP_PUSH_REGISTRATION_PREFS
        )
        return pref.getBoolean(PREF_KEY_IS_REGISTER, false)
    }

    private fun saveIsRegistration(isRegister: Boolean) {
        val pref = PreferenceUtil.createOrGetPreference(
            getApplication(),
            APP_PUSH_REGISTRATION_PREFS
        )
        pref.set(PREF_KEY_IS_REGISTER, isRegister)
    }

    fun getFCMToken(): String {
        return MyFirebaseMessagingService.getToken(getApplication())
    }
}

data class PushNotificationViewState(val result: PushNotificationViewModel.PushNotificationResult)

