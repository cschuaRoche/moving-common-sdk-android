package com.roche.roche.dis.salesforce.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.roche.roche.dis.R
import com.salesforce.android.chat.core.ChatConfiguration
import com.salesforce.android.chat.core.ChatCore
import com.salesforce.android.chat.core.SessionStateListener
import com.salesforce.android.chat.core.model.AvailabilityState
import com.salesforce.android.chat.core.model.ChatEndReason
import com.salesforce.android.chat.core.model.ChatSessionState
import com.salesforce.android.service.common.utilities.control.Async

abstract class SalesforceBaseViewModel(app: Application) : AndroidViewModel(app), SessionStateListener, Async.ResultHandler<AvailabilityState> {
    // TODO store these securely
    private val ORG_ID = "00D0E000000EEbX"
    private val DEPLOYMENT_ID = "5720E0000004CP1"
    private val BUTTON_ID = "5730E0000004Clq"
    private val LIVE_AGENT_POD = "d.la2-c1cs-fra.salesforceliveagent.com"
    internal val chatConfiguration: ChatConfiguration =
        ChatConfiguration.Builder(ORG_ID, BUTTON_ID,
            DEPLOYMENT_ID, LIVE_AGENT_POD)
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
        val agentAvailability = ChatCore.configureAgentAvailability(chatConfiguration, requestEstimatedWaitTime)
        agentAvailability.check().onResult(this)

        initChat(activity)
    }

    override fun onSessionStateChange(state: ChatSessionState?) {
        if (state == ChatSessionState.Disconnected) {
            isShowing = false
            Toast.makeText(getApplication(), R.string.chat_session_disconnected, Toast.LENGTH_SHORT).show()
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