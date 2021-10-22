package com.roche.ssg.sample.salesforce

import android.util.Log
import com.salesforce.android.chat.core.SessionInfoListener
import com.salesforce.android.chat.core.model.ChatSessionInfo

class SessionInfoImpl : SessionInfoListener {

    override fun onSessionInfoReceived(chatSessionInfo: ChatSessionInfo) {
        // TO DO: Do something with the session ID
        val sessionId = chatSessionInfo.getSessionId()
        Log.w("Salesforce", "sessionId: $sessionId")
    }
}