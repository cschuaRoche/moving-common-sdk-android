package com.roche.ssg.sample.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.roche.ssg.sample.R
import java.io.IOException
import java.net.URL

class NotificationUtils(private val context: Context) {

    private fun getRocheNotificationBuilder(
        data: Map<String, String>
    ): NotificationCompat.Builder {
        return getNotificationBuilder(data)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setColor(Color.BLUE)
            .setLights(Color.BLUE, 1000, 300)
            .setSmallIcon(R.drawable.ic_baseline_notification_important)
            .setDefaults(Notification.DEFAULT_VIBRATE)
    }

    private fun getNotificationBuilder(
        data: Map<String, String>
    ): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle(data["pinpoint.notification.title"])
            .setContentText(data["pinpoint.notification.body"])
            .setContentInfo(data["pinpoint.notification.title"])
        val bigPicture =
            getImage(data["pinpoint.campaign.campaign_id"] ?: data["picture_url"] ?: "")
        bigPicture?.let {
            builder.setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(it)
                    .setSummaryText(data["pinpoint.notification.body"])
            )
        }
        return builder
    }

    /**
     * Create and show a custom notification containing the received FCM message.
     *
     * @param data FCM data payload received.
     * @param pendingIntent information of notification click
     */
    fun showRocheNotification(
        data: Map<String, String>,
        pendingIntent: PendingIntent
    ) {
        val notificationBuilder =
            getRocheNotificationBuilder(data)
                .setContentIntent(pendingIntent)

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