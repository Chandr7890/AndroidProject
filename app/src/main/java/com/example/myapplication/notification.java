package com.example.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class notification extends AppCompatActivity {
 private static final String Channel_id = "channel";
    private static final int Notify_id = 100;
    private static final int INTERVAL_MINUTES = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.img,null);
        BitmapDrawable bitmapDrawable=(BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        scheduleNotification();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon).setSmallIcon(R.drawable.img)
                    .setContentText("new message")
                    .setSubText("Do the Exercise ")
                    .setChannelId(Channel_id)
                    .build();
             nm.createNotificationChannel(new NotificationChannel(Channel_id,"new channel",NotificationManager.IMPORTANCE_HIGH  ));
        }else{
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon).setSmallIcon(R.drawable.img)
                    .setContentText("new message")
                    .setSubText("new message from app")
                    .build();
        }
        nm.notify(Notify_id,notification);
    }
    private void scheduleNotification() {
        Intent notificationIntent = new Intent(this, ddashboard.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long triggerTime = System.currentTimeMillis() + INTERVAL_MINUTES * 60 * 1000;
            Log.d("trigger time", String.valueOf(triggerTime));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            }
        }
    }
}



