package com.roche.ssg.sample.push.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.roche.ssg.pushnotification.PushNotificationException
import com.roche.ssg.pushnotification.api.PushNotificationApiClient
import com.roche.ssg.sample.databinding.FragmentPushRegisterBinding
import com.roche.ssg.sample.firebase.MyFirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterPushFragment : Fragment() {

    private lateinit var binding: FragmentPushRegisterBinding
    private lateinit var mAuthToken: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPushRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAmplify()
        setLoginListener()
        setRegisterListener()
        setUnregisterListener()
    }

    private fun initAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(requireContext())
            Log.i("RegisterPushFragment", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("RegisterPushFragment", "Could not initialize Amplify", error)
        }
    }

    private fun setLoginListener() {
        binding.btnGetCognitoToken.setOnClickListener {
            val uiScope = CoroutineScope(Dispatchers.Main)
            Amplify.Auth.signIn("devandroiduser@mailinator.com", "Test@1234",
                { result ->
                    if (result.isSignInComplete) {
                        mAuthToken = AWSMobileClient.getInstance().tokens.idToken.tokenString

                        uiScope.launch {
                            binding.btnRegister.isEnabled = true
                            binding.btnUnregister.isEnabled = true
                            showMessage("Login Successful")
                        }

                    } else {
                        Log.i("RegisterPushFragment", "Sign in not complete")

                        uiScope.launch {
                            binding.btnRegister.isEnabled = false
                            binding.btnUnregister.isEnabled = false
                            showMessage("Login Failed")
                        }

                    }
                }, {
                    Log.e("RegisterPushFragment", "Failed to sign in", it)
                    uiScope.launch {
                        binding.btnRegister.isEnabled = false
                        binding.btnUnregister.isEnabled = false
                        showMessage("Login Failed")
                    }
                }
            )
        }
    }

    private fun setRegisterListener() {
        binding.btnRegister.setOnClickListener {
            if (this::mAuthToken.isInitialized) {
                val firebaseToken = getFirebaseToken()
                if (firebaseToken != null) {

                    val coroutineScope = CoroutineScope(Dispatchers.IO)
                    coroutineScope.launch {
                        try {
                            val result = PushNotificationApiClient().registerDevice(
                                "https://floodlight.dhp-dev.dhs.platform.navify.com",
                                "test",
                                Amplify.Auth.currentUser.userId,
                                firebaseToken,
                                "1.3.1",
                                "us",
                                mAuthToken
                            )
                            Log.i("RegisterPushFragment", "Response from Server $result")
                            showMessage("Registration successful")
                        } catch (e: PushNotificationException) {
                            Log.e("RegisterPushFragment", "Error", e)
                            showMessage("Registration Failed")
                        } catch (e: Exception) {
                            Log.e("RegisterPushFragment", "Error", e)
                            showMessage("Registration Failed")
                        }
                    }

                } else {
                    Log.i("RegisterPushFragment", "Firebase token is not generated")
                    showMessage("Firebase token is not generated")
                }
            } else {
                Log.i("RegisterPushFragment", "Authentication Required")
                showMessage("Authentication Required, Please login first")
            }
        }
    }

    private fun setUnregisterListener() {
        binding.btnUnregister.setOnClickListener {

            if (this::mAuthToken.isInitialized) {
                val firebaseToken = getFirebaseToken()
                if (firebaseToken != null) {
                    val coroutineScope = CoroutineScope(Dispatchers.IO)
                    coroutineScope.launch {
                        try {
                            val result = PushNotificationApiClient().deregisterDevice(
                                "https://floodlight.dhp-dev.dhs.platform.navify.com",
                                "test",
                                Amplify.Auth.currentUser.userId,
                                firebaseToken,
                                mAuthToken
                            )
                            Log.i("RegisterPushFragment", "Response from Server $result")
                            showMessage("Un-registration Successful")
                        } catch (e: PushNotificationException) {
                            Log.e("RegisterPushFragment", "Error", e)
                            showMessage("Un-registration Failed")
                        } catch (e: Exception) {
                            Log.e("RegisterPushFragment", "Error", e)
                            showMessage("Un-registration Failed")
                        }
                    }

                } else {
                    Log.i("RegisterPushFragment", "Firebase token is not generated")
                    showMessage("Firebase token is not generated")
                }
            } else {
                Log.i("RegisterPushFragment", "Authentication Required")
                showMessage("Authentication Required, Please login first")
            }
        }
    }

    private fun getFirebaseToken(): String? {
        val pref = activity?.getSharedPreferences(
            MyFirebaseMessagingService.PREF_PUSH_NOTIFICATION,
            Context.MODE_PRIVATE
        )
        return pref?.getString(MyFirebaseMessagingService.KEY_FIREBASE_TOKEN, null)
    }

    private fun showMessage(message: String) {
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}