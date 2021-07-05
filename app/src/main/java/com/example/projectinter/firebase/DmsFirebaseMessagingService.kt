package com.chipmong.dms.firebase

import android.util.Log
import com.example.projectinter.firebase.DmsNotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.lang.Exception

class DmsFirebaseMessagingService : FirebaseMessagingService() {
    private lateinit var notificationManager: DmsNotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = DmsNotificationManager(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
//        if (!TextUtils.equals(token, DmsSharePreference.getInstance(this).getFirebaseToken())
//            && IOrderSharePreference.getInstance(this).isPushNotification()){
//
//            IOrderSharePreference.getInstance(this).setFirebaseToken(token)
//
//            RegisterPushRequest(this, false).execute {
//                RegisterPushRequest(this, true).execute()
//            }
//        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(javaClass.simpleName, "From: " + remoteMessage.from)
        try {
            val data = JSONObject(remoteMessage.data["rawData"].toString())
            Log.d(javaClass.simpleName, "Message data payload JSON: " + data.toString(1))
            notificationManager.displayNotification(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}