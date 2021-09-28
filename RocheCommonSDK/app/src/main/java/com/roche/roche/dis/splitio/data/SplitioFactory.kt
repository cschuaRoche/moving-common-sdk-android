package com.roche.roche.dis.splitio.data

import android.content.Context
import io.split.android.client.SplitClient
import io.split.android.client.SplitClientConfig
import io.split.android.client.SplitFactoryBuilder
import io.split.android.client.api.Key
import java.security.AccessControlContext

object SplitioFactory {

    fun getSplitClient(context: Context,userKey: String) : SplitClient{
        // val apikey = "a8kp11v5ek4sggmu6g4bldv9r7tcotvjvr66" // Prod-Default Client
        val apikey = "lustbo1tjk27dlml1483ndq2irfph36j5blj" // Staging-Default
        // Build SDK configuration by default
        val config = SplitClientConfig.builder()
            .build()
        // Create a new user key to be evaluated
        val matchingKey = userKey
        val k = Key(matchingKey)
        // Create factory
        val splitFactory = SplitFactoryBuilder.build(
            apikey, k, config,
            context?.applicationContext
        )
        // Get Split Client instance
        return splitFactory.client()
    }
}