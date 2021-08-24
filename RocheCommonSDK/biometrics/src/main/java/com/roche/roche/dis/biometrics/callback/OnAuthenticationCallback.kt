package com.roche.roche.dis.biometrics.callback

/**
 * Used for System Biometrics Prompt status callback
 *
 */
interface OnAuthenticationCallback {
    companion object {
        /**
         * User have successfully authenticated via biometrics
         */
        const val SUCCESS = 0

        /**
         * User cancels the system dialog
         */
        const val ERROR_USER_CANCELED = 1

        /**
         * Hardware support is unavailable or missing actual hardware
         */
        const val ERROR_NO_HARDWARE = 2

        /**
         * User does not have any biometrics created on the device
         */
        const val ERROR_NO_BIOMETRICS = 3

        /**
         * Locked out due to too many attempts
         */
        const val ERROR_LOCKOUT = 4

        /**
         * Other error we don't care about right now
         */
        const val ERROR_UNKNOWN = 5

        /**
         * User failed to authenticate
         */
        const val FAILED_ATTEMPT = 99
    }

    fun onAuthComplete(@BiometricStatus statusCode: Int)
}