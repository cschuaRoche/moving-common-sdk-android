package com.roche.ssg.systemmessages.data.model

data class SystemMessagesResponse(
    val meta: Meta,
    val systemMessagesList: List<SystemMessage>
)