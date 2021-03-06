package com.roche.apprecall

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

actual val testCoroutineContext: CoroutineContext =
    Executors.newSingleThreadExecutor().asCoroutineDispatcher()

actual fun runBlockingTest(block: suspend () -> Unit) =
    runBlocking(testCoroutineContext) { block() }
