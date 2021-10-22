package com.roche.ssg.sample.salesforce.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import com.roche.ssg.sample.salesforce.ChatEventImpl
import com.roche.ssg.sample.salesforce.SessionInfoImpl
import com.salesforce.android.chat.ui.ChatUI
import com.salesforce.android.chat.ui.ChatUIConfiguration
import com.salesforce.android.chat.ui.model.QueueStyle

class SalesforceChatViewModel(app: Application) : SalesforceBaseViewModel(app) {
    private val eventListener = ChatEventImpl()

    override fun initChat(activity: Activity) {
        val uiConfig = ChatUIConfiguration.Builder()
            .chatConfiguration(chatConfiguration)
            .chatEventListener(eventListener)
            .build()
        startChat(activity, uiConfig)
    }

    fun startChatInFullScreenMode(activity: Activity) {
        val uiConfig = ChatUIConfiguration.Builder()
            .chatConfiguration(chatConfiguration)
            .chatEventListener(eventListener)
            .queueStyle(QueueStyle.EstimatedWaitTime) // Use estimated wait time
            .defaultToMinimized(false) // Start in full-screen mode
            .build()
        startChat(activity, uiConfig)
    }

    private fun startChat(activity: Activity, uiConfig: ChatUIConfiguration) {
        ChatUI.configure(uiConfig)
            .createClient(activity)
            .onResult { _, chatUIClient ->
                Log.d("Salesforce", "onResult: ${chatUIClient.currentSessionState}")
                chatUIClient.addSessionStateListener(this)
                chatUIClient.addSessionInfoListener(SessionInfoImpl())
                chatUIClient.startChatSession(activity)
            }
    }
}