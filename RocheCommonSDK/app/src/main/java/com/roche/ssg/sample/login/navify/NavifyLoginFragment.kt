package com.roche.ssg.sample.login.navify

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.roche.ssg.sample.R
import com.roche.ssg.sample.databinding.FragmentNavifyLoginBinding
import com.roche.ssg.sample.login.navify.vm.NavifyLoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavifyLoginFragment : Fragment() {

    private lateinit var mBinding: FragmentNavifyLoginBinding

    private val mNavifyLoginViewModel: NavifyLoginViewModel by viewModels()
    private var isLoginFLow = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentNavifyLoginBinding.inflate(inflater, container, false)
        setLoginClickListener()
        setViewStateObserver()
        return mBinding.root
    }

    private fun setLoginClickListener() {
        mBinding.btnLogin.setOnClickListener {
            val currentState = mNavifyLoginViewModel.navifyLoginViewState.value?.result
            if (currentState is NavifyLoginViewModel.NavifyLoginResult.LoginPending
                || currentState is NavifyLoginViewModel.NavifyLoginResult.LoginFailed
            ) {
                login()
            } else {
                logout()
            }
        }
    }

    private fun login() {
        isLoginFLow = true
        launchLoginActivity(LoginActivity.FLOW_AUTH_LOGIN)
    }

    private fun logout() {
        isLoginFLow = false
        mNavifyLoginViewModel.logout()
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            Log.i("Testing", "Result code ${result.resultCode}")
            var authCode: String? = null
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent = result.data!!
                authCode = data.getStringExtra("auth_code")
                Log.i("Testing", "Received code $authCode")
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Log.i("Testing", "Authentication cancelled")
            }
            if (isLoginFLow)
                mNavifyLoginViewModel.validateAuthCode(authCode)
        }

    private fun setViewStateObserver() {
        mNavifyLoginViewModel.navifyLoginViewState.observe(viewLifecycleOwner, {
            when (it.result) {
                is NavifyLoginViewModel.NavifyLoginResult.LoginPending -> {
                    mBinding.txtStatus.text = getString(R.string.authenticate)
                    mBinding.btnLogin.text = getString(R.string.login_to_navify)
                    hideUserInfo()
                }
                is NavifyLoginViewModel.NavifyLoginResult.LoginSuccess -> {
                    mBinding.txtStatus.text = getString(R.string.authentication_successful)
                    mBinding.btnLogin.text = getString(R.string.logout)
                    hideUserInfo()
                }
                is NavifyLoginViewModel.NavifyLoginResult.LoginFailed -> {
                    mBinding.txtStatus.text = getString(R.string.authentication_failed)
                    mBinding.btnLogin.text = getString(R.string.login_to_navify)
                    hideUserInfo()
                }
                is NavifyLoginViewModel.NavifyLoginResult.LoginUserInfo -> {
                    mBinding.flowUser.visibility = View.VISIBLE
                    mBinding.user = it.result.user
                }
                is NavifyLoginViewModel.NavifyLoginResult.LoginUserInfoFailed -> {
                    hideUserInfo()
                }
                is NavifyLoginViewModel.NavifyLoginResult.LogoutSuccessful -> {
                    isLoginFLow = false
                    launchLoginActivity(LoginActivity.FLOW_AUTH_LOGOUT)
                }
                is NavifyLoginViewModel.NavifyLoginResult.LogoutFailed -> {

                }
            }
        })
    }

    private fun hideUserInfo() {
        mBinding.flowUser.visibility = View.GONE
    }

    private fun launchLoginActivity(authFlow: Int) {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.putExtra(LoginActivity.KEY_AUTH_CODE, authFlow)
        intent.putExtra(
            LoginActivity.KEY_LOGIN_CONFIGURATION,
            mNavifyLoginViewModel.loginConfiguration
        )
        resultLauncher.launch(intent)
    }
}