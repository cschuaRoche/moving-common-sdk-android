package com.roche.ssg.sample.auth.model

import androidx.annotation.Keep

@Keep
sealed class Result<out Success, out Failure>
@Keep
data class Success<out Success>(val value: Success) : Result<Success, Nothing>()
@Keep
data class Failure<out Failure>(val value: Failure) : Result<Nothing, Failure>()
