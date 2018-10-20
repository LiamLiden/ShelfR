package com.example.leeseoye.shelfr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter adapter;
    ArrayList<Ingredient> ingredientList;
    SharedPreferences sharedPref;
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

        //        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
////        String name = null;
////        String fileName = sharedPref.getString("file", name);
////        if (fileName != null) {
////            try {
////                Scanner scan = new Scanner(new File(fileName));
////                while(scan.hasNext()){
////                stringList.add(scan.nextLine());
////                }
////            }
////            catch (IOException e){
////                Toast.makeText(this, "ERROR: FILE COULD NOT BE READ", Toast.LENGTH_LONG);
////            }
////        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), SearchableActivity.class);
                startActivity(intent);
            }
        });

        ingredientList = new ArrayList<Ingredient>();
        ingredientList.add(new Ingredient("fridge", 10, 1, "Pie"));
        ingredientList.add(new Ingredient("fridge", 10, 1, "Test2"));
        listView = findViewById(R.id.LV);

        refresh();

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


    public void refresh(){
        adapter = new CustomAdapter(this, R.layout.activity_listview, ingredientList);
        listView.setAdapter(adapter);
    }

    public void addNewItem(Ingredient item){
        ingredientList.add(item);
        refresh();
    }




}
