package com.roche.ssg.systemmessages

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.roche.ssg.sample.utils.PreferenceUtil
import com.roche.ssg.sample.utils.set

internal object SystemMessagesSharedPref {
    @VisibleForTesting
    internal const val SYSTEM_MESSAGES_PREFS = "SYSTEM_MESSAGES_PREFS"

    @VisibleForTesting
    internal const val PREF_KEY_DISMISSED_MESSAGES = "key_dismissed_messages"

    fun setDismissedMessages(context: Context, dismissedMessages: HashSet<String>) {
        val pref = PreferenceUtil.createOrGetPreference(context, SYSTEM_MESSAGES_PREFS)
        pref.set(PREF_KEY_DISMISSED_MESSAGES, Gson().toJson(dismissedMessages))
    }

    fun getDismissedMessages(context: Context): HashSet<String> {
        val pref = PreferenceUtil.createOrGetPreference(context, SYSTEM_MESSAGES_PREFS)
        val rawString = pref.getString(PREF_KEY_DISMISSED_MESSAGES, null) ?: return hashSetOf()
        return Gson().fromJson(rawString, object : TypeToken<HashSet<String>>() {}.type)
    }
}