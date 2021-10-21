package com.roche.roche.dis.auth
object AuthenticationMessages {
    //ERRORS
    const val LOGIN_FAILED = "Login failed."
    const val NOT_AUTHORIZED = "NotAuthorizedException"
    const val INIT_FAILED = "Initialization failed."

    //SUCCESS MESSAGES
    const val USER_NOT_CONFIRMED = "User is not confirmed."
    const val INIT_SUCCESSFUL = "Initialization successful."
    const val LOGOUT_SUCCESSFUL = "Log out successful."
    const val REGISTER_SUCCESSFUL = "User registered! Check email for confirmation code!"

    //ACTIONS
    const val ACTION_NEW_PASSWORD_REQUIRED = "new_password_required"
    const val ACTION_SMS_MFA = "sms_mfa"
    const val ACTION_CODE_MISMATCH = "code_mismatch"
}