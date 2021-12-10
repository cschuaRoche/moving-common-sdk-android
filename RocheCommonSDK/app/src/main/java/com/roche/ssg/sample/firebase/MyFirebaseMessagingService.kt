package com.roche.ssg.sample.firebase

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.roche.ssg.sample.MainActivity
import com.roche.ssg.sample.utils.NotificationUtils
import com.roche.ssg.utils.PreferenceUtil
import com.roche.ssg.utils.get
import com.roche.ssg.utils.set


/**
 *
 * see https://firebase.google.com/docs/cloud-messaging/android/client
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // TODO display message here
        Log.d("MyFirebaseMessagingService", "Received messageId: ${message.messageId}")

        val notification: RemoteMessage.Notification? = message.notification
        val data: Map<String, String> = message.data

        NotificationUtils(this).showRocheNotification(data, getPendingIntent())
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveToken(token)
        Log.i("MyFirebaseMessagingService", "Token from onNewToken $token")
    }

    private fun saveToken(token: String) {
        val pref = PreferenceUtil.createOrGetPreference(
            this,
            FCM_PREFS
        )
        pref.set(FCM_PREFS_KEY_TOKEN, token)
    }

    companion object {
        private const val FCM_PREFS = "FCM_PREFS"
        private const val FCM_PREFS_KEY_TOKEN = "FCM_PREFS_TOKEN"

        fun getToken(context: Context): String {
            return getToken(context, FCM_PREFS_KEY_TOKEN)
        }

        private fun getToken(context: Context, key: String): String {
            val pref = PreferenceUtil.createOrGetPreference(
                context,
                FCM_PREFS
            )
            return pref.get(key, "") ?: ""
        }
    }
}