package com.roche.ssg.sample.login.navify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.roche.ssg.sample.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Testing", "inside onCreate")
        setContentView(R.layout.activity_login)

        if(intent.hasExtra("AUTH_FLOW")){
            val flowType=intent.getIntExtra("AUTH_FLOW",0)
            if(flowType==1){
                startLogin()
            }else if (flowType==2){
                startLogout()
            }
        }
    }

    // TODO: Create a configuration file with these values: redircectUri, clientId, scope, baseurl, method, etc.
    // TODO: Create a LoginViewModel where it will get these configurations and expose it back to the View - LoginActivity.
    private fun startLogin(){
        val scope = "openid"
        val redirectUri = "roche://com.roche.ssg/auth/callback"
        val clientId = "ssg-dev-reference-app-patient"

        val builtUri =
            Uri.parse("https://keycloak.appdevus.platform.navify.com/auth/realms/patients/protocol/openid-connect/auth?")
                .buildUpon()
                .appendQueryParameter("client_id", clientId)
                .appendQueryParameter("scope", scope)
                .appendQueryParameter("redirect_uri", redirectUri)
                .appendQueryParameter("response_type", "code")

        val browserIntent =
            Intent(Intent.ACTION_VIEW, builtUri.build())
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_FROM_BACKGROUND)
        startActivity(browserIntent)
    }

    // TODO: This is the work around solution we had to do because the default redirect behavior does not work with Cognito, Logout from cognito is not required
    private fun startLogout(){
        val redirectUri = "roche://com.roche.ssg/auth/callback"
        val clientId = "2o544hm96i0jsf1eie7a97ve6h"
        val scope = "openid"

        val builtUri =
            Uri.parse("https://ssg-patient-dev.auth.us-east-1.amazoncognito.com/logout?")
                .buildUpon()
                .appendQueryParameter("client_id", clientId)
                .appendQueryParameter("scope", scope)
                .appendQueryParameter("redirect_uri", redirectUri)
                .appendQueryParameter("response_type", "code")

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