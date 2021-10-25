package com.roche.ssg.sample.pushnotification

/**
 * @param status HTTP status
 * @param error Exception while making network request
 */
class PushNotificationException(val status: Int, private val error: Exception) : Exception(error)