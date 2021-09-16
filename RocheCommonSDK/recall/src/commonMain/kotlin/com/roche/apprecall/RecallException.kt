package com.roche.apprecall

/**
 * @param status HTTP status
 * @param error Exception while making network request
 */
class RecallException(val status: Int,private val error: Exception) : Exception(error)