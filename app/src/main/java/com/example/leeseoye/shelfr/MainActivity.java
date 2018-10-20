package com.example.leeseoye.shelfr;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private NotificationReceiver Receiver = new NotificationReceiver();
    private static final String ACTION_NOTIFICATION = "com.leeseoye.shelfr.ACTION_NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(Receiver, new IntentFilter(ACTION_NOTIFICATION));

        AlarmManager alarm = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationManager.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Log.d("setalarm", "here");
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        alarmTime.set(2018,10,19,2,12);
        Log.d("setalarm", alarmTime.toString() );
        alarm.set(alarm.RTC_WAKEUP, alarmTime.getTimeInMillis(), alarmIntent);

        Toast.makeText(this.getApplicationContext(), "absdfagthyjuyukikjhgf", Toast.LENGTH_LONG).show();
    }





}
