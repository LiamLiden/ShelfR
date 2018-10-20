package com.example.leeseoye.shelfr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationReceiver extends BroadcastReceiver {
    //private NotificationManager notificationManager;
    //private static final int NOTIFICATION_ID = 0;

    private NotificationManager notificationManager;
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String CHANNEL_ID = "notification_channel";
    private Context context;
    private static final String ACTION_NOTIFICATION = "com.leeseoye.shelfr.ACTION_NOTIFICATION";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("tag","receive");
        this.context = context;
        createNotificationChannel();
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // send notification
        sendNotification(context);

    }

    /**
     * OnClick method for the "Cancel Me!" button. Cancels the notification.
     */
    public void cancelNotification() {
        // Cancel the notification.
        //TODO change the NOTIFICATIONID to unique for each alarm
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only API 26+ (no supporting library for other API)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "name", importance);
            channel.setDescription("description");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Builds and delivers the notification.
     *
     * @param context, activity context.
     */
    private void sendNotification(Context context) {
       // Intent contentIntent = new Intent(context, MainActivity.class);
        Intent contentIntent = new Intent(ACTION_NOTIFICATION);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        // Build the notification
        //TODO: change the contentTitle and contentText
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, CHANNEL_ID)
                .setSmallIcon(R.drawable.fridge)
                .setContentTitle("title")
                .setContentText("text")
                //.setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Notify the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}