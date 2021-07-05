package com.example.projectinter.firebase;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


import com.example.projectinter.R;

import org.json.JSONObject;

import java.util.UUID;

public abstract class DmsNotification {
    private Context context;
    private Bitmap largeIcon;
    private JSONObject data;
    private String channelId = "DMS_NOTIFICATION_ID";

    public static String getPackage() {
        return "com.exmaple.projectinter.firebase";
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

//    private int getSmallIcon() {
//        return R.mipmap.ic_launcher;
//    }

    private Bitmap getLargeIcon() {
//        if (largeIcon == null)
//            largeIcon = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);
        return largeIcon;
    }

    public void setLargeIcon(Bitmap largeIcon) {
        this.largeIcon = largeIcon;
    }

    public Spanned getContentText() {
        Spanned textHtml;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textHtml = Html.fromHtml(getData().optString("title", getContext().getString(R.string.app_name)), Html.FROM_HTML_MODE_LEGACY);
        } else {
            textHtml = Html.fromHtml(getData().optString("title", getContext().getString(R.string.app_name)));
        }
        return textHtml;
    }

    private String getContentTitle() {
        return context.getResources().getString(R.string.app_name);
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static DmsNotification create(Context context, JSONObject data, String notiType) {
        DmsNotification item = null;
        try {
            ClassLoader classLoader = DmsNotification.class.getClassLoader();
            Class<?> aClass = classLoader.loadClass(getPackage() + ".DmsNotification" + notiType.toUpperCase());
            item = (DmsNotification) aClass.newInstance();
            item.setContext(context);
            item.setData(data);
        } catch (Exception e) {
            Log.w("DmsNotification", "Unknown Notification code " + data.optString("notificationType"));
        }
        return item;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationChannel getNotificationChannel() {
        String name = "DMS_NOTIFICATION_CHANNEL";
        NotificationChannel mChannel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
        mChannel.enableLights(true);
        mChannel.setLightColor(R.color.colorPrimary);
        return mChannel;
    }

    public NotificationCompat.Builder getNotificationBuilder() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId)
                .setContentTitle(getContentTitle())
                .setContentText(getContentText())
//                .setSmallIcon(getSmallIcon())
                .setLargeIcon(getLargeIcon())
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setWhen(System.currentTimeMillis())
                .setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .setAutoCancel(true);
        Intent contentIntent = getContentIntent();
        if (contentIntent != null) {
            contentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(getContext(), 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);
        }
        return builder;
    }

    abstract Intent getContentIntent();

    public int getNotificationId() {
        return UUID.randomUUID().toString().hashCode();
    }
}
