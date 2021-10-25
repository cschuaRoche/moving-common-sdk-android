package com.roche.ssg.biometrics.callback

import androidx.annotation.IntDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    OnAuthenticationCallback.SUCCESS,
    OnAuthenticationCallback.ERROR_USER_CANCELED,
    OnAuthenticationCallback.ERROR_NO_HARDWARE,
    OnAuthenticationCallback.ERROR_NO_BIOMETRICS,
    OnAuthenticationCallback.ERROR_LOCKOUT,
    OnAuthenticationCallback.ERROR_UNKNOWN,
    OnAuthenticationCallback.FAILED_ATTEMPT
)

internal annotation class BiometricStatus()
