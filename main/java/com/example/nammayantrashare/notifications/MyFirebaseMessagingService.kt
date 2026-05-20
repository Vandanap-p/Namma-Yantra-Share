package com.example.nammayantrashare.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.nammayantrashare.DashboardActivity
import com.example.nammayantrashare.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :
    FirebaseMessagingService() {

    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {

        super.onMessageReceived(remoteMessage)

        val title =
            remoteMessage.notification?.title
                ?: "NammaYantraShare"

        val message =
            remoteMessage.notification?.body
                ?: "New Notification"

        showNotification(
            title,
            message
        )
    }

    private fun showNotification(
        title: String,
        message: String
    ){

        val channelId =
            "booking_notifications"

        val notificationManager =
            getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        if(
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ){

            val channel =
                NotificationChannel(
                    channelId,
                    "Booking Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                )

            notificationManager
                .createNotificationChannel(channel)
        }

        val intent =
            Intent(
                this,
                DashboardActivity::class.java
            )

        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
        )

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val notification =
            NotificationCompat.Builder(
                this,
                channelId
            )
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        notificationManager.notify(
            1,
            notification
        )
    }

    override fun onNewToken(
        token: String
    ) {

        super.onNewToken(token)
    }
}