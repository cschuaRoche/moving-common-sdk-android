package com.roche.dis.systemmessages

/**
 * Exception from API with [statusCode] given by server
 */
data class SystemMessagesException(val statusCode: Int, val error: Exception) : Exception()