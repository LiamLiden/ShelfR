package com.example.leeseoye.shelfr;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button search;
    private NotificationReceiver Receiver = new NotificationReceiver();
    private static final String ACTION_NOTIFICATION = "com.leeseoye.shelfr.ACTION_NOTIFICATION";
    private static final String CHANNEL_ID = "notification_channel";

    //kefir, pudding, pasteurized crab meat, main dishes
    // not included (package date issue might come up again)
    //Commercial brand vacuum packed dinners with USDA seal 2 weeks Does not freeze well
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.goSearch);

        registerReceiver(Receiver, new IntentFilter(ACTION_NOTIFICATION));
        createNotificationChannel(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), SearchableActivity.class);
                startActivity(intent);
            }
        });
        /*
        AlarmManager alarm = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Log.d("setalarm", "here");
        Calendar alarmTime = Calendar.getInstance();
        //alarmTime.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        //alarmTime.set(2018,10,19,2,12);
        alarmTime.add(Calendar.MINUTE, 1);
        Log.d("setalarm", alarmTime.toString() );
        alarm.set(alarm.RTC_WAKEUP, alarmTime.getTimeInMillis(), alarmIntent);

        Toast.makeText(this.getApplicationContext(), "absdfagthyjuyukikjhgf", Toast.LENGTH_LONG).show();
*/
    }


        public static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("NotificationChannel","Adding notification channel");
            CharSequence name = "name";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }




}
