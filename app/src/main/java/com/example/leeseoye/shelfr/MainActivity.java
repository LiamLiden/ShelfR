package com.example.leeseoye.shelfr;

import android.app.DialogFragment;
import android.app.FragmentManager;

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
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity implements AddDialog.NoticeDialogListener{

    ListView listView;
    CustomAdapter adapter;
    ArrayList <Ingredient> ingredientList;
    SharedPreferences sharedPref;
    Toolbar myToolBar;
    int index;

    private NotificationReceiver Receiver = new NotificationReceiver();
    private static final String ACTION_NOTIFICATION = "com.leeseoye.shelfr.ACTION_NOTIFICATION";
    private static final String CHANNEL_ID = "notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("main", "before create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("main", "after super");

        myToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);

        registerReceiver(Receiver, new IntentFilter(ACTION_NOTIFICATION));
        createNotificationChannel(this);

        ingredientList = new ArrayList<Ingredient>();
        ingredientList.add(new Ingredient("fridge", 10, 1, "Pie", this));
        ingredientList.add(new Ingredient("fridge", 10, 1, "Test2", this));
        //Testing for notification
        //ingredientList.add(new)

        listView = (ListView) findViewById(R.id.LV);

        myToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //myToolBar.setBackgroundColor(Color.RED);
                switch (item.getItemId()) {
                    case R.id.action_favorite:
                        // User chose the "Settings" item, show the app settings UI...
                        myToolBar.setTitle("Change");
                        showAddDialog();

                        return true;

                    case R.id.action_search:
                       // Intent intent = new Intent (MainActivity.this, SearchableActivity.class);
                        Intent intent = new Intent (MainActivity.this, SearchableActivity.class);
                       /*intent.putExtra("name","Butter");
                       intent.putExtra("fridgeLife", "1-3 days");
                       intent.putExtra("freezerLife", "5 days");
                       intent.putExtra("shelfLife", "9 days");*/
                        startActivityForResult(intent,10);
                        return true;

                    default:
                        // If we got here, the user's action was not recognized.
                        // Invoke the superclass to handle it.
                        return false;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ingredient currentEvent = ingredientList.get(i);
                if(i ==0){
                    //myToolBar.setBackgroundColor(Color.YELLOW);
                }
                else if(i == 1){
                    //myToolBar.setBackgroundColor(Color.GREEN);
                }
                myToolBar.setTitle(currentEvent.getName());
            }
        });
        Button shortcut = findViewById(R.id.button);
        shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), InfoActivity.class);
                intent.putExtra("name", "Butter");
                intent.putExtra("fridgeLife", "1-3 days");
                intent.putExtra("freezerLife", "5 days");
                intent.putExtra("shelfLife", "9 days");
                startActivityForResult(intent, 10);
            }
        });

        refresh();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void refresh(){
        adapter = new CustomAdapter(this, R.layout.activity_listview, ingredientList);
        listView.setAdapter(adapter);
    }

    public void showAddDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new AddDialog();
        FragmentManager f = getFragmentManager();
        dialog.show(f, "AddDialog");

    }


    @Override
    public void onDialogPositiveClick() {
        //String a = ((EditText) s.getDialog().getOwnerActivity().findViewById(R.id.text_box)).getText().toString();
        // addNewItem(AddDialog.editText.getText().toString());
        //  AddDialog.editText = null;
        //   addNewItem(s);
        ingredientList.get(index).reduceAmount();
        refresh();
    }

    @Override
    public void onDialogNeutralClick(){
        ingredientList.remove(index);
        refresh();
    }

    @Override
    public void onDialogNegativeClick() {

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


    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {

        if (resultCode == RESULT_OK) {
            Log.d("main", "onactivityresult");
            if (requestCode == 10) {
                Bundle bundle = intent.getExtras();

                ingredientList.add(new Ingredient(bundle.getString("storage"), bundle.getInt("expiration")
                        , bundle.getInt("quantity"), bundle.getString("food"), this));
                adapter = new CustomAdapter(this, R.layout.activity_listview, ingredientList);
                listView.setAdapter(adapter);
            }
            super.onActivityResult(requestCode, resultCode, intent);

        }
    }
}
