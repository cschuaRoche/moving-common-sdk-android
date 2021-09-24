package com.roche.apprecall

import kotlin.coroutines.CoroutineContext

expect val testCoroutineContext: CoroutineContext
expect fun runBlockingTest(block: suspend () -> Unit)
