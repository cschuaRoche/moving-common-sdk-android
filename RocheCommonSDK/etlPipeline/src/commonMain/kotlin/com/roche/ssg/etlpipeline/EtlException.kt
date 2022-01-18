package com.roche.ssg.etlpipeline

/**
 * @param status HTTP status
 * @param error Exception while making network request
 */
class EtlException(val status: Int, private val error: Exception) : Exception(error)