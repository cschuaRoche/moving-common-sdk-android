package com.roche.roche.dis.auth

import android.content.Context
import android.util.Log
import com.roche.roche.dis.auth.model.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicBoolean

object SessionManager : SessionProvider {

    private const val ERROR_INIT = "SessionManager is not yet initialized."

    private val tag = javaClass.simpleName
    private val authenticator: Authenticator = CognitoAuthenticator()
    var currentToken: AuthenticationToken? = null
        private set
    private var isInitialized = AtomicBoolean(false)
    private var delayedAction: Job = Job()

    private lateinit var applicationId: String
    private lateinit var userRole: String
    private lateinit var locale: String

    /**
     * In order for the authenticator client to work, we need to initialize it.
     * The result callbacks are handled on the main thread.
     */
    suspend fun initialize(
        context: Context,
        configJson: JSONObject,
        applicationId: String,
        userRole: String,
        locale: String
    ): Result<String, CognitoResponse> {
        val applicationContext = context.applicationContext
        this.applicationId = applicationId
        this.userRole = userRole
        this.locale = locale
        val result = authenticator.initialize(context, configJson)
        result.apply {
            isInitialized.set(true)
            delayedAction.start()
            delayedAction = Job()
        }
        return result
    }

    /**
     * Login using the given credentials
     * The result callbacks are handled on the main thread.
     */
    suspend fun login(
        userName: String,
        password: String,
    ): Result<Boolean, CognitoResponse> {
        val result = authenticator.loginUser(userName, password)
        result.let {
            return when (it) {
                is Success -> {
                    val authenticatedUserState = it.value
                    currentToken = authenticatedUserState.authenticationToken
                    Success(true)
                }
                is Failure -> Failure(it.value)
            }
        }
    }

    /**
     * Logout and clear the current tokens
     * The result callbacks are handled on the main thread.
     */
    suspend fun logout(): Result<String, CognitoResponse>? {
        if (!isInitialized()) {
            delayedAction =
                CoroutineScope(Dispatchers.IO).launch(start = CoroutineStart.LAZY) { logout() }
            return null
        }
        val result = authenticator.logoutUser()
        return result.let {
            when (it) {
                is Success -> {
                    invalidateToken()
                    Success(it.value)
                }
                is Failure -> Failure(it.value)
            }
        }
    }

    fun isInitialized() = isInitialized.get()

    fun isSignedIn(): Boolean {
        if (!isInitialized()) {
            return false
        }
        return authenticator.isSignedIn()
    }

    /**
     * Register the user with the given credentials and signup code & user type.
     * The result callbacks are handled on the main thread.
     */
    suspend fun register(
        userName: String,
        password: String,
        signupCode: String,
        userType: String
    ): Result<String, CognitoResponse> {
        val result = authenticator.registerUser(
            username = userName,
            password = password,
            signupCode = signupCode,
            userType = userType,
            applicationId = applicationId,
            userRole = userRole,
            locale = locale
        )
        return result.let {
            when (it) {
                is Success -> {
                    Success(it.value)
                }
                is Failure -> Failure(it.value)
            }
        }
    }

    /**
     * Register the user with the given credentials.
     */
    suspend fun register(
        userName: String,
        password: String
    ): Result<String, CognitoResponse> {
        val result = authenticator.registerUser(
            username = userName,
            password = password,
            applicationId = applicationId,
            userRole = userRole,
            locale = locale
        )
        return result.let {
            when (it) {
                is Success -> {
                    Success(it.value)
                }
                is Failure -> Failure(it.value)
            }
        }
    }

    private fun getIdToken() = authenticator.getToken()

    override fun refreshTokenIfExpired() {
        val oldToken = currentToken
        Log.i(tag, "Check if the old token's expiration time < than the current time in millis")
        if (oldToken == null || oldToken.expirationDate.time < System.currentTimeMillis()) {
            Log.i(tag, "Old token has been expired")
            val newToken: AuthenticationToken = getIdToken() ?: return
            Log.i(tag, "Get new token from SessionManager")
            currentToken = newToken
        }
    }

    private fun invalidateToken() {
        currentToken = null
    }

    override fun getToken(): String? {
        return currentToken?.tokenString
    }

    class SessionNotInitialized : IllegalStateException(ERROR_INIT)
}