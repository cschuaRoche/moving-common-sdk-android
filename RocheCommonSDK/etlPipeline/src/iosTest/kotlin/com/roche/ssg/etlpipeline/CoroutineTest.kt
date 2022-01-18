package com.roche.ssg.etlpipeline

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
actual val testCoroutineContext: CoroutineContext =
    newSingleThreadContext("testRunner")

@ExperimentalCoroutinesApi
actual fun runBlockingTest(block: suspend () -> Unit) =
    runBlocking(testCoroutineContext) { block() }