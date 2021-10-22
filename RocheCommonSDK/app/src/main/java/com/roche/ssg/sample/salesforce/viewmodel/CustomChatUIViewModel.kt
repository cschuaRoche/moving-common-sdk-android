package com.roche.ssg.sample.salesforce.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import com.roche.ssg.sample.salesforce.SessionInfoImpl
import com.salesforce.android.chat.core.AgentListener
import com.salesforce.android.chat.core.ChatClient
import com.salesforce.android.chat.core.ChatCore
import com.salesforce.android.chat.core.QueueListener
import com.salesforce.android.chat.core.model.AgentInformation
import com.salesforce.android.chat.core.model.ChatMessage

class CustomChatUIViewModel(app: Application) : SalesforceBaseViewModel(app), AgentListener,
    QueueListener {
    private var chatClient: ChatClient? = null

    override fun initChat(activity: Activity) {
        val core = ChatCore.configure(chatConfiguration)
        core.createClient(getApplication())
            .onResult { operation, chatClient ->
                Log.d("Salesforce", "isCancelled:${operation.isCancelled} isComplete:${operation.isComplete}")
                this.chatClient = chatClient
                    .addSessionStateListener(this)
                    .addSessionInfoListener(SessionInfoImpl())
                    .addAgentListener(this)
                    .addQueueListener(this)
            }
    }

    // AgentListeners
    override fun onAgentJoined(agentInformation: AgentInformation) {
        Log.w("Salesforce", "onAgentJoined: $agentInformation")
    }

    override fun onChatTransferred(agentInformation: AgentInformation?) {
        Log.w("Salesforce", "onChatTransferred: $agentInformation")
    }

    override fun onChatMessageReceived(chatMessage: ChatMessage) {
        Log.w("Salesforce", "onChatMessageReceived: $chatMessage")
    }

    override fun onAgentIsTyping(isUserTyping: Boolean) {
        Log.w("Salesforce", "isTyping: $isUserTyping")
    }

    override fun onTransferToButtonInitiated() {
        Log.w("Salesforce", "onTransferToButtonInitiated")
    }

    override fun onAgentJoinedConference(value: String?) {
        Log.w("Salesforce", "onAgentJoinedConference: $value")
    }

    override fun onAgentLeftConference(value: String?) {
        Log.w("Salesforce", "onAgentLeftConference: $value")
    }

    // QueueListeners
    override fun onQueuePositionUpdate(value: Int) {
        Log.w("Salesforce", "onQueuePositionUpdate: $value")
    }

    override fun onQueueEstimatedWaitTimeUpdate(p0: Int, p1: Int) {
        Log.w("Salesforce", "onQueueEstimatedWaitTimeUpdate: $p0, $p1")
    }
}