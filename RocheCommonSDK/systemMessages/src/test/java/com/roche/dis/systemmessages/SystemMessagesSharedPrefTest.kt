package com.roche.dis.systemmessages

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.google.gson.Gson
import com.roche.dis.systemmessages.SystemMessagesSharedPref.PREF_KEY_DISMISSED_MESSAGES
import com.roche.roche.dis.utils.PreferenceUtil
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SystemMessagesSharedPrefTest : BaseMockkTest() {
    @MockK(relaxed = true)
    private lateinit var appContext: Application

    @MockK(relaxed = true)
    private lateinit var pref: SharedPreferences

    @Before
    override fun setup() {
        super.setup()

        mockkObject(SystemMessagesSharedPref)
        mockkObject(PreferenceUtil)
        mockkStatic(EncryptedSharedPreferences::class)

        every {
            appContext.getSharedPreferences(
                SystemMessagesSharedPref.SYSTEM_MESSAGES_PREFS,
                Context.MODE_PRIVATE
            )
        } returns pref

        every {
            PreferenceUtil.createOrGetPreference(
                appContext,
                SystemMessagesSharedPref.SYSTEM_MESSAGES_PREFS
            )
        } returns pref
    }

    @Test
    fun `getDismissedMessages should return value from sharedPreferences`() {
        val dismissedMessages = hashSetOf("dismissedMessageResId")
        val rawString = Gson().toJson(dismissedMessages)
        every { pref.getString(PREF_KEY_DISMISSED_MESSAGES, null) } returns rawString
        Assert.assertEquals(
            dismissedMessages,
            SystemMessagesSharedPref.getDismissedMessages(appContext)
        )
    }

    @Test
    fun `getDismissedMessages should return empty if not set`() {
        every { pref.getString(PREF_KEY_DISMISSED_MESSAGES, null) } returns null
        Assert.assertEquals(
            hashSetOf<String>(),
            SystemMessagesSharedPref.getDismissedMessages(appContext)
        )
    }

    @Test
    fun `setDismissedMessages should not crash`() {
        try {
            SystemMessagesSharedPref.setDismissedMessages(appContext, hashSetOf("resId"))
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }
}