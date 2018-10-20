package com.example.leeseoye.shelfr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import android.view.View;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter adapter;
    ArrayList<Ingredient> ingredientList;
    SharedPreferences sharedPref;
    Toolbar myToolBar;

    private NotificationReceiver Receiver = new NotificationReceiver();
    private static final String ACTION_NOTIFICATION = "com.leeseoye.shelfr.ACTION_NOTIFICATION";
    private static final String CHANNEL_ID = "notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolBar = findViewById(R.id.my_toolbar);
        myToolBar.setBackgroundColor(Color.BLUE);
        setSupportActionBar(myToolBar);

        //listView header design
        TextView textHeader = new TextView(this);
        textHeader.setText(R.string.app_name);
        textHeader.setTextSize(24);
        textHeader.setTypeface(null, Typeface.BOLD);

        ingredientList = new ArrayList<Ingredient>();
        ingredientList.add(new Ingredient("fridge", 10, 1, "Pie", this));
        ingredientList.add(new Ingredient("fridge", 10, 1, "Test2", this));

        listView = (ListView) findViewById(R.id.LV);
        listView.addHeaderView(textHeader);
        /*
        myToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                myToolBar.setBackgroundColor(Color.RED);
                switch (item.getItemId()) {
                    case R.id.action_favorite:
                        // User chose the "Settings" item, show the app settings UI...
                        myToolBar.setTitle("Change");
                        showAddDialog();
                        return true;

                    case R.id.action_search:
                        Intent intent = new Intent(MainActivity.this, TestActivity.class);
                        startActivity(intent);
                        // User chose the "Favorite" action, mark the current item
                        // as a favorite...
                        return true;

                    default:
                        // If we got here, the user's action was not recognized.
                        // Invoke the superclass to handle it.
                        return false;
                }
            }
        });
        */

        registerReceiver(Receiver, new IntentFilter(ACTION_NOTIFICATION));
        createNotificationChannel(this);

//        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//        String name = null;
//        String fileName = sharedPref.getString("file", name);
//        if (fileName != null) {
//            try {
//                Scanner scan = new Scanner(new File(fileName));
//                while(scan.hasNext()){
//                stringList.add(scan.nextLine());
//                }
//            }
//            catch (IOException e){
//                Toast.makeText(this, "ERROR: FILE COULD NOT BE READ", Toast.LENGTH_LONG);
//            }
//        }


        //refresh();


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

    public void onReceive(View view){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Ingredient i = new Ingredient(bundle.getString("storage"), bundle.getInt("expiration")
                , bundle.getInt("quantity"), bundle.getString("food"), this);

        ingredientList.add(i);
        refresh();
    }




}
