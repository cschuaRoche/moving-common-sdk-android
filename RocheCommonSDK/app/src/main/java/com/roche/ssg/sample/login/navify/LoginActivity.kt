package com.roche.ssg.sample.login.navify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.roche.ssg.sample.R
import com.roche.ssg.sample.login.navify.data.LoginConfiguration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        const val KEY_AUTH_CODE = "auth_code"
        const val KEY_LOGIN_CONFIGURATION = "login_configuration"

        const val FLOW_AUTH_LOGIN = 1
        const val FLOW_AUTH_LOGOUT = 2
    }

    private lateinit var mLoginConfiguration: LoginConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Testing", "inside onCreate")
        setContentView(R.layout.activity_login)

        if (intent.hasExtra(KEY_LOGIN_CONFIGURATION)) {
            mLoginConfiguration =
                intent.getSerializableExtra(KEY_LOGIN_CONFIGURATION) as LoginConfiguration
        }

        if (intent.hasExtra(KEY_AUTH_CODE)) {
            val flowType = intent.getIntExtra(KEY_AUTH_CODE, 0)
            if (flowType == FLOW_AUTH_LOGIN) {
                startLogin()
            } else if (flowType == FLOW_AUTH_LOGOUT) {
                startLogout()
            }
        }
    }

    private fun startLogin() {

        val builtUri =
            Uri.parse("${mLoginConfiguration.authUrl}?")
                .buildUpon()
                .appendQueryParameter(
                    "client_id",
                    mLoginConfiguration.clientId
                )
                .appendQueryParameter("scope", mLoginConfiguration.scope)
                .appendQueryParameter(
                    "redirect_uri",
                    mLoginConfiguration.redirectUri
                )
                .appendQueryParameter(
                    "response_type",
                    mLoginConfiguration.responseType
                )

        val browserIntent =
            Intent(Intent.ACTION_VIEW, builtUri.build())
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_FROM_BACKGROUND)
        startActivity(browserIntent)
    }

    // TODO: This is the work around solution we had to do because the default redirect behavior does not work with Cognito, Logout from cognito is not required
    private fun startLogout() {

        val builtUri =
            Uri.parse("${mLoginConfiguration.cognitoLogoutUrl}?")
                .buildUpon()
                .appendQueryParameter(
                    "client_id",
                    mLoginConfiguration.logoutClientId
                )
                .appendQueryParameter("scope", mLoginConfiguration.scope)
                .appendQueryParameter(
                    "redirect_uri",
                    mLoginConfiguration.redirectUri
                )
                .appendQueryParameter(
                    "response_type",
                    mLoginConfiguration.responseType
                )

        val browserIntent =
            Intent(Intent.ACTION_VIEW, builtUri.build())
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_FROM_BACKGROUND)
        startActivity(browserIntent)
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Testing", "inside onRestart() calling finish")
        finish()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("Testing", "inside onNewIntent")
        if (intent != null && intent.data != null && "roche" == intent.data!!.scheme) {
            val code = intent.data?.getQueryParameter("code")
            if (code != null) {
                val resultIntent = Intent()
                resultIntent.putExtra("auth_code", code)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }
    }
}