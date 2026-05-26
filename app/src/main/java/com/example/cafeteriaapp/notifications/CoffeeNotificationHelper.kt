package com.example.cafeteriaapp.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.cafeteriaapp.R

object CoffeeNotificationHelper {
    private const val CHANNEL_ID = "loyalty_rewards"
    private const val REWARD_READY_NOTIFICATION_BASE_ID = 1000
    private const val REWARD_CLAIMED_NOTIFICATION_BASE_ID = 2000

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Nagrody kawiarni",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Powiadomienia o nagrodach w programie lojalnosciowym"
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    fun showRewardReadyNotification(
        context: Context,
        rewardTitle: String,
        requiredStamps: Int
    ) {
        if (!canShowNotifications(context)) return

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_coffee)
            .setContentTitle("Nowa nagroda dostepna")
            .setContentText("$rewardTitle za $requiredStamps pieczatek czeka na odbior.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(REWARD_READY_NOTIFICATION_BASE_ID + requiredStamps, notification)
    }

    fun showRewardClaimedNotification(
        context: Context,
        rewardTitle: String
    ) {
        if (!canShowNotifications(context)) return

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_coffee)
            .setContentTitle("Nagroda odebrana")
            .setContentText("Odebrano: $rewardTitle. Smacznego!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(REWARD_CLAIMED_NOTIFICATION_BASE_ID + rewardTitle.hashCode(), notification)
    }

    private fun canShowNotifications(context: Context): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
    }
}