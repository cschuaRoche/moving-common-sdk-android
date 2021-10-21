package com.roche.roche.dis.salesforce

import android.util.Log
import com.salesforce.android.chat.core.model.*
import com.salesforce.android.chat.ui.ChatEventListener

class ChatEventImpl : ChatEventListener {
    override fun agentJoined(agentInformation: AgentInformation) {
        // Handle agent joined
        Log.w("Salesforce", "agentJoined: $agentInformation")
    }

    override fun processedOutgoingMessage(message: String) {
        // Handle outgoing message processed
        Log.w("Salesforce", "processedOutgoingMessage: $message")
    }

    override fun didSelectMenuItem(menuItem: ChatWindowMenu.MenuItem) {
        // Handle chatbot menu selected
        Log.w("Salesforce", "didSelectMenuItem: $menuItem")
    }

    override fun didSelectButtonItem(buttonItem: ChatWindowButtonMenu.Button) {
        // Handle chatbot button selected
        Log.w("Salesforce", "didSelectButtonItem: $buttonItem")
    }

    override fun didSelectFooterMenuItem(footerMenuItem: ChatFooterMenu.MenuItem) {
        // Handle chatboot footer menu selected
        Log.w("Salesforce", "didSelectFooterMenuItem: $footerMenuItem")
    }

    override fun didReceiveMessage(chatMessage: ChatMessage) {
        // Handle received message
        Log.w("Salesforce", "didReceiveMessage: $chatMessage")
    }

    override fun transferToButtonInitiated() {
        // Handle transfer to agent
        Log.w("Salesforce", "transferToButtonInitiated")
    }

    override fun agentIsTyping(isUserTyping: Boolean) {
        // Handle typing update
        Log.w("Salesforce", "isUserTyping: $isUserTyping")
    }
}