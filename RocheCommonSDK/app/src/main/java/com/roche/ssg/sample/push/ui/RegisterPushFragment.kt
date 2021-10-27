package com.roche.ssg.sample.push.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.roche.ssg.sample.databinding.FragmentPushRegisterBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegisterPushFragment : Fragment() {

    private lateinit var binding: FragmentPushRegisterBinding
    private val scope = MainScope()
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
        binding.btnGetCognitoToken.setOnClickListener {
            try {
                Amplify.addPlugin(AWSCognitoAuthPlugin())
                Amplify.configure(requireContext())
                Log.i("RegisterPushFragment", "Initialized Amplify")
            } catch (error: AmplifyException) {
                Log.e("RegisterPushFragment", "Could not initialize Amplify", error)
            }

            Amplify.Auth.signIn("devandroiduser@mailinator.com", "Test@1234",
                { result ->
                    if (result.isSignInComplete) {
                        Log.i("RegisterPushFragment", "Sign in succeeded")
                        Log.e(
                            "RegisterPushFragment",
                            "Token : " + AWSMobileClient.getInstance().tokens.accessToken.tokenString
                        )
                    } else {
                        Log.i("RegisterPushFragment", "Sign in not complete")
                    }
                },
                {
                    Log.e("RegisterPushFragment", "Failed to sign in", it)
                }
            )
        }
        binding.btnRegister.setOnClickListener {

        }
        binding.btnUnregister.setOnClickListener {

        }
    }
}