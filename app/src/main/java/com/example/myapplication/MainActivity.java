package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Button move,move1;
    private static final String Channel_id = "channel";
    private static final int Notify_id = 100;
    private static final int INTERVAL_MINUTES = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

      
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        move = findViewById(R.id.p);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,plogin.class);
                startActivity(intent);
            }
        });
        move1 = findViewById(R.id.d);
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,dlogin.class);
                startActivity(intent);
            }
        });
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