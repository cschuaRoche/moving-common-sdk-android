package com.roche.ssg.etlpipeline

import kotlin.coroutines.CoroutineContext

expect val testCoroutineContext: CoroutineContext
expect fun runBlockingTest(block: suspend () -> Unit)