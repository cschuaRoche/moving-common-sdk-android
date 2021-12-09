package com.roche.ssg.sample.utils

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
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.roche.ssg.sample.MainActivity
import com.roche.ssg.sample.R
import java.io.IOException
import java.net.URL

class NotificationUtils(private val context: Context) {

    /**
     * Create and show a custom notification containing the received FCM message.
     *
     * @param notification FCM notification payload received.
     * @param data FCM data payload received.
     */
    fun showRocheNotification(
        notification: RemoteMessage.Notification?,
        data: Map<String, String>,
        pendingIntent: PendingIntent
    ) {

        val title: String = notification?.title ?: data["pinpoint.notification.title"] ?: ""
        val body: String = notification?.body ?: data["pinpoint.notification.body"] ?: ""
        val campaignId = data["pinpoint.campaign.campaign_id"] ?: data["picture_url"] ?: ""
        showNotification(title, body, campaignId, pendingIntent)
    }

    private fun showNotification(
        title: String,
        body: String,
        pictureURL: String,
        pendingIntent: PendingIntent
    ) {

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "channel_id")
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo(title)
                .setColor(Color.BLUE)
                .setLights(Color.BLUE, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_baseline_notification_important)

        val bigPicture = getImage(pictureURL)
        bigPicture?.let {
            notificationBuilder.setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(it)
                    .setSummaryText(body)
            )
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

    private fun getImage(pictureURL: String): Bitmap? {
        try {
            if ("" != pictureURL) {
                val url = URL(pictureURL)
                return BitmapFactory.decodeStream(url.openConnection().getInputStream())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}