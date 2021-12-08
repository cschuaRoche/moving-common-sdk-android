package com.roche.ssg.sample.salesforce.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.roche.ssg.sample.BuildConfig
import com.roche.ssg.sample.R
import com.salesforce.android.chat.core.ChatConfiguration
import com.salesforce.android.chat.core.ChatCore
import com.salesforce.android.chat.core.SessionStateListener
import com.salesforce.android.chat.core.model.AvailabilityState
import com.salesforce.android.chat.core.model.ChatEndReason
import com.salesforce.android.chat.core.model.ChatSessionState
import com.salesforce.android.service.common.utilities.control.Async

abstract class SalesforceBaseViewModel(app: Application) : AndroidViewModel(app),
    SessionStateListener, Async.ResultHandler<AvailabilityState> {

    internal val chatConfiguration: ChatConfiguration =
        ChatConfiguration.Builder(
            BuildConfig.SALESFORCE_ORG_ID, BuildConfig.SALESFORCE_BUTTON_ID,
            BuildConfig.SALESFORCE_DEPLOYMENT_ID, BuildConfig.SALESFORCE_LIVE_AGENT_POD
        )
            .build()

    internal abstract fun initChat(activity: Activity)

    var isShowing = false

    fun startChat(activity: Activity) {
        if (isShowing) {
            Log.w("Salesforce", "Start is ignored.  Salesforce Chat has already started!")
            return
        }
        isShowing = true

        val requestEstimatedWaitTime = true
        val agentAvailability =
            ChatCore.configureAgentAvailability(chatConfiguration, requestEstimatedWaitTime)
        agentAvailability.check().onResult(this)

        initChat(activity)
    }

    override fun onSessionStateChange(state: ChatSessionState?) {
        if (state == ChatSessionState.Disconnected) {
            isShowing = false
            Toast.makeText(getApplication(), R.string.chat_session_disconnected, Toast.LENGTH_SHORT)
                .show()
        }
        Log.w("Salesforce", "onSessionStateChange: $state")
    }

    override fun onSessionEnded(endReason: ChatEndReason?) {
        if (endReason == ChatEndReason.EndedByAgent) {
            Toast.makeText(getApplication(), R.string.chat_session_ended, Toast.LENGTH_SHORT).show()
        }
        Log.w("Salesforce", "onSessionEnded: $endReason")
    }

    override fun handleResult(operation: Async<*>?, result: AvailabilityState) {
        when (result.status) {
            AvailabilityState.Status.AgentsAvailable -> {
                // Optionally, use the estimatedWaitTime to
                // show an estimated wait time until an agent
                // is available. This value is only valid
                // if you request it from the
                // configureAgentAvailability call above.
                // Estimate is returned in seconds.
                val ewt = result.estimatedWaitTime
                Log.w("Salesforce", "AvailabilityState: AgentsAvailable - ewt: $ewt")
            }
            AvailabilityState.Status.NoAgentsAvailable -> {
                Log.w("Salesforce", "AvailabilityState: NoAgentsAvailable")
            }
            else -> {
                // unknown state, unable to query the status of agents.
                // consider waiting...
                Log.w("Salesforce", "AvailabilityState: Unknown")
            }
        }
    }
}