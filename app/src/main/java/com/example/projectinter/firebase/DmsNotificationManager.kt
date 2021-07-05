package com.example.projectinter.firebase

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import org.json.JSONObject

class DmsNotificationManager(private val mContext: Context) {
    fun displayNotification(data: JSONObject) {
        val notification = DmsNotification.create(mContext, data, data.optString("referenceType"))
        if (notification != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notification.notificationChannel)
                    notificationManager.notify(notification.notificationId, notification.notificationBuilder.build())
                }
            } else {
                NotificationManagerCompat.from(mContext).notify(notification.notificationId, notification.notificationBuilder.build())
            }
        }
    }

}