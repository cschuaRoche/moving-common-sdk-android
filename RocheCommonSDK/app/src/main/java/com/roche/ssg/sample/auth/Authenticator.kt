package com.roche.ssg.sample.auth

import android.content.Context
import com.amazonaws.mobile.client.UserState
import com.roche.ssg.sample.auth.model.AuthenticatedUserState
import com.roche.ssg.sample.auth.model.AuthenticationToken
import com.roche.ssg.sample.auth.model.CognitoResponse
import com.roche.ssg.sample.auth.model.Result
import org.json.JSONObject

interface Authenticator {

    suspend fun initialize(
        applicationContext: Context,
        configJson: JSONObject
    ): Result<String, CognitoResponse>

    suspend fun loginUser(
        userName: String,
        password: String
    ): Result<AuthenticatedUserState, CognitoResponse>

    suspend fun logoutUser(): Result<String, CognitoResponse>

    suspend fun registerUser(
        username: String,
        password: String,
        signupCode: String,
        userType: String,
        applicationId: String,
        userRole: String,
        locale: String
    ): Result<String, CognitoResponse>

    suspend fun registerUser(
        username: String,
        password: String,
        applicationId: String,
        userRole: String,
        locale: String
    ): Result<String, CognitoResponse>


    fun getPatientId(): String?

    fun getToken(): AuthenticationToken?

    fun getUserState(): UserState

    fun isSignedIn(): Boolean

    fun registerUserStateListener()

    fun unRegisterUserStateListener()

}