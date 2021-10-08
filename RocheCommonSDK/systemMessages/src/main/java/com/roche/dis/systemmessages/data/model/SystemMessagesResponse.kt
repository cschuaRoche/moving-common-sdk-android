package com.roche.dis.systemmessages.data.model

data class SystemMessagesResponse(
    val meta: Meta,
    val systemMessagesList: List<SystemMessage>
)