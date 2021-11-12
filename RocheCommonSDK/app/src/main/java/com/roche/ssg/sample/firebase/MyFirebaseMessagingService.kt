package com.roche.ssg.sample.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.roche.ssg.sample.MainActivity
import com.roche.ssg.sample.R
import com.roche.ssg.utils.PreferenceUtil
import com.roche.ssg.utils.get
import com.roche.ssg.utils.set
import java.io.IOException
import java.net.URL


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
        if (notification == null) {
            Log.d("MyFirebaseMessagingService", "notification is null")
        } else {
            Log.d("MyFirebaseMessagingService", "consuming notification")
            showNotification(notification, data)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveToken(token)
        Log.i("MyFirebaseMessagingService", "Token from onNewToken $token")

    }

    /**
     * Create and show a custom notification containing the received FCM message.
     *
     * @param notification FCM notification payload received.
     * @param data FCM data payload received.
     */
    private fun showNotification(
        notification: RemoteMessage.Notification,
        data: Map<String, String>
    ) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo(notification.title)
                .setColor(Color.BLUE)
                .setLights(Color.BLUE, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_baseline_notification_important)
        try {
            val pictureURL = data["picture_url"]
            if (pictureURL != null && "" != pictureURL) {
                val url = URL(pictureURL)
                val bigPicture: Bitmap =
                    BitmapFactory.decodeStream(url.openConnection().getInputStream())
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bigPicture)
                        .setSummaryText(notification.body)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "channel description"
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
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