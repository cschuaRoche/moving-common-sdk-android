package com.roche.roche.dis.auth

import android.content.Context
import android.util.Log
import com.amazonaws.AmazonServiceException
import com.amazonaws.mobile.client.*
import com.amazonaws.mobile.client.results.SignInState
import com.amazonaws.mobile.client.results.SignUpResult
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoMfaSettings
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.exceptions.CognitoNotAuthorizedException
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException
import com.amazonaws.services.cognitoidentityprovider.model.NotAuthorizedException
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException
import com.roche.roche.dis.auth.AuthenticationMessages.ACTION_CODE_MISMATCH
import com.roche.roche.dis.auth.AuthenticationMessages.ACTION_NEW_PASSWORD_REQUIRED
import com.roche.roche.dis.auth.AuthenticationMessages.INIT_FAILED
import com.roche.roche.dis.auth.AuthenticationMessages.INIT_SUCCESSFUL
import com.roche.roche.dis.auth.AuthenticationMessages.LOGIN_FAILED
import com.roche.roche.dis.auth.AuthenticationMessages.LOGOUT_SUCCESSFUL
import com.roche.roche.dis.auth.AuthenticationMessages.NOT_AUTHORIZED
import com.roche.roche.dis.auth.AuthenticationMessages.REGISTER_SUCCESSFUL
import com.roche.roche.dis.auth.AuthenticationMessages.USER_NOT_CONFIRMED
import com.roche.roche.dis.auth.model.*
import io.split.android.client.service.executor.SplitTaskExecutionInfo.UNEXPECTED_ERROR
import org.json.JSONObject
import java.net.HttpURLConnection
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class CognitoAuthenticator : Authenticator {

    private companion object {
        private const val SIGNUP_CODE_KEY = "custom:signUpCode"
        private const val EMAIL_KEY = "email"
        private const val USER_KEY = "custom:userType"
        private const val KEY_APPLICATION_ID = "applicationId"
        private const val KEY_USER_ROLE = "userRole"
        private const val KEY_LOCALE = "locale"
    }

    private lateinit var cognitoUserPool: CognitoUserPool

    private val authClient: AWSMobileClient
        get() = AWSMobileClient.getInstance()

    private val tag = javaClass.simpleName

    private val userStateListener: (UserStateDetails) -> Unit = {
        Log.d(tag, "userStateListener called with state ${it.userState}")
        /* In case of invalid userstate, the cognito sdk is waiting for a
           manual signin and locks the caller thread(our thread).
           In this case we need to release the waiting.
           NOTE: If we are already in invalid state, trying to get the tokens again will not trigger a state change
           and the caller thread remains blocked.
           */
        if (it.userState == UserState.SIGNED_OUT_FEDERATED_TOKENS_INVALID
            || it.userState == UserState.SIGNED_OUT_USER_POOLS_TOKENS_INVALID
        ) {
            Log.d(tag, "sign out locally")
            authClient.signOut()//by signing out we clear the local tokens and we get back into a normal state
        }
    }

    /**
     * Initializes the AWS mobile client with the given context. In case of success or failure, a message is returned.
     */
    override suspend fun initialize(
        applicationContext: Context,
        configJson: JSONObject
    ): Result<String, CognitoResponse> =
        suspendCoroutine { cont ->
            val config = AWSConfiguration(configJson)

            cognitoUserPool = CognitoUserPool(applicationContext, config)

            authClient.initialize(
                applicationContext,
                config,
                object : Callback<UserStateDetails> {
                    override fun onResult(details: UserStateDetails?) {
                        cont.resume(Success(INIT_SUCCESSFUL))
                    }

                    override fun onError(e: Exception?) {
                        cont.resume(handleError(e, INIT_FAILED))
                    }
                }
            )
        }


    /**
     * Logs in the user with the provided credentials. In case of successful login the access token is returned,
     * otherwise the error message is returned
     */
    override suspend fun loginUser(
        userName: String,
        password: String,
    ): Result<AuthenticatedUserState, CognitoResponse> {
        return try {
            val singInResult = authClient.signIn(userName, password, null) ?: return handleError(
                NullPointerException(),
                UNEXPECTED_ERROR
            )

            when (singInResult.signInState) {
                SignInState.DONE -> {
                    val token = getToken()
                        ?: return Failure(CognitoResponse(responseCode = 0, message = LOGIN_FAILED))

                    val fetchResult = fetchUserAttributes()
                    Success(
                        AuthenticatedUserState(
                            token,
                            fetchResult.first,
                            fetchResult.second
                        )
                    )
                }
                SignInState.NEW_PASSWORD_REQUIRED -> {
                    Failure(
                        CognitoResponse(
                            0,
                            message = ACTION_NEW_PASSWORD_REQUIRED
                        )
                    )
                }
                else -> {
                    Failure(
                        CognitoResponse(
                            responseCode = 0,
                            message = LOGIN_FAILED
                        )
                    )
                }
            }
        } catch (e: Exception) {
            when (e) {
                is UserNotConfirmedException -> handleError(e, USER_NOT_CONFIRMED)
                is NotAuthorizedException -> handleError(e, NOT_AUTHORIZED)
                else -> handleError(e, LOGIN_FAILED)
            }
        }
    }

    private fun fetchUserAttributes(): Pair<String?, String?> {
        var userType: String? = null
        var signUpCode: String? = null
        try {
            userType = authClient.userAttributes[USER_KEY]
        } catch (e: java.lang.Exception) {
            // dont do anything
        }
        try {
            signUpCode = authClient.userAttributes[SIGNUP_CODE_KEY]
        } catch (e: java.lang.Exception) {
            // dont do anything
        }
        return Pair(userType, signUpCode)
    }

    private fun <T : Any> handleError(
        e: java.lang.Exception?,
        defaultErrorMsg: String,
    ): Result<T, CognitoResponse> {
        val errMsg = getErrorMessage(e, defaultErrorMsg)
        val errCode: String = getErrorCode(e)
        Log.e(tag, "$errCode | $e | $errMsg")
        return Failure(CognitoResponse(responseCode = 0, message = errMsg))
    }

    private fun getErrorCode(e: java.lang.Exception?) = when (e) {
        is AmazonServiceException -> e.errorCode
        else -> "null"
    }

    /**
     * Logs out the user and clears the local tokens.
     */
    override suspend fun logoutUser(): Result<String, CognitoResponse> {
        // Sign-out user from all sessions across devices
        val globalLogout = SignOutOptions.builder().signOutGlobally(true).build()

        return try {
            authClient.signOut(globalLogout)
            Success(LOGOUT_SUCCESSFUL)
        } catch (e: Exception) {
            handleSignOutError(e)
        }
    }

    private fun handleSignOutError(e: java.lang.Exception?): Result<String, CognitoResponse> {
        Log.e(tag, "Cognito sign out exception: ${e?.message}")
        return when (e) {
            is NotAuthorizedException -> {
                return when (e.statusCode) {
                    HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        //NotAuthorizedException
                        Failure(
                            CognitoResponse(
                                responseCode = e.statusCode,
                                message = e.errorMessage
                            )
                        )
                    }
                    else -> {
                        Failure(
                            CognitoResponse(
                                responseCode = 0,
                                message = e.errorMessage
                            )
                        )
                    }
                }
            }
            else ->
                Failure(
                    CognitoResponse(
                        responseCode = 0,
                        message = UNEXPECTED_ERROR
                    )
                )
        }
    }

    /**
     * Registers the user with the given credentials and signup code.
     */
    override suspend fun registerUser(
        username: String,
        password: String,
        signupCode: String,
        userType: String,
        applicationId: String,
        userRole: String,
        locale: String
    ): Result<String, CognitoResponse> = suspendCoroutine { cont ->
        signUp(username, password, signupCode, userType, applicationId, userRole, locale, cont)
    }

    /**
     * Registers the user with the given credentials.
     */
    override suspend fun registerUser(
        username: String,
        password: String,
        applicationId: String,
        userRole: String,
        locale: String
    ): Result<String, CognitoResponse> = suspendCoroutine { cont ->
        signUp(username, password, null, null, applicationId, userRole, locale, cont)
    }

    private fun signUp(
        username: String,
        password: String,
        signUpCode: String?,
        userType: String?,
        applicationId: String,
        userRole: String,
        locale: String,
        cont: Continuation<Result<String, CognitoResponse>>
    ) {
        val attributes: MutableMap<String, String> = HashMap()
        attributes[EMAIL_KEY] = username
        if (!signUpCode.isNullOrBlank()) {
            attributes[SIGNUP_CODE_KEY] = signUpCode
        }
        if (!userType.isNullOrBlank()) {
            attributes[USER_KEY] = userType
        }

        val clientMetadata: MutableMap<String, String> = HashMap()
        clientMetadata[KEY_APPLICATION_ID] = applicationId
        clientMetadata[KEY_USER_ROLE] = userRole
        clientMetadata[KEY_LOCALE] = locale

        authClient.signUp(
            username,
            password,
            attributes,
            null,
            clientMetadata,
            getSignupCallback(cont)
        )
    }

    private fun getSignupCallback(cont: Continuation<Result<String, CognitoResponse>>): Callback<SignUpResult> {
        return object : Callback<SignUpResult> {
            override fun onResult(signUpResult: SignUpResult?) {
                if (signUpResult?.confirmationState != true) {
                    val details = signUpResult?.userCodeDeliveryDetails
                    cont.resume(Success("Confirm sign-up with: +${details?.destination}"))
                } else {
                    cont.resume(Success(REGISTER_SUCCESSFUL))
                }
            }

            override fun onError(e: java.lang.Exception?) {
                cont.resume(handleError(e, UNEXPECTED_ERROR))
            }
        }
    }
    /**
     * Sets the SMS as primary MFA channel.
     *
     * @return  true, if the flow has completed successfully
     */
    private suspend fun enableTwoFactorAuthentication(): Result<Boolean, CognitoResponse> =
        suspendCoroutine { cont ->
            val element = CognitoMfaSettings(CognitoMfaSettings.SMS_MFA)
            element.isEnabled = true

            cognitoUserPool.currentUser.setUserMfaSettingsInBackground(
                listOf(element),
                object : GenericHandler {
                    override fun onSuccess() {
                        cont.resume(Success(value = true))
                    }

                    override fun onFailure(e: java.lang.Exception?) {
                        cont.resume(handleError(e, UNEXPECTED_ERROR))
                    }
                })
        }

    private fun getErrorMessage(e: Exception?, defaultErrorMsg: String): String {
        return when (e) {
            is CodeMismatchException -> ACTION_CODE_MISMATCH
            is AmazonServiceException -> e.errorMessage
            is CognitoNotAuthorizedException -> e.localizedMessage.orEmpty()
            else -> defaultErrorMsg
        }
    }

    override fun getPatientId() = try {
        authClient.tokens?.idToken?.getClaim("sub") ?: ""
    } catch (e: Exception) {
        ""
    }

    override fun getToken(): AuthenticationToken? {
        try {
            val token = authClient.tokens?.idToken ?: return null
            return AuthenticationToken(token.tokenString, token.expiration)
        } catch (e: Exception) {
            return null
        }
    }

    override fun isSignedIn(): Boolean {
        return authClient.isSignedIn
    }

    override fun getUserState(): UserState = authClient.currentUserState().userState

    override fun registerUserStateListener() {
        authClient.addUserStateListener(userStateListener)
    }

    override fun unRegisterUserStateListener() {
        authClient.removeUserStateListener(userStateListener)
    }

}
