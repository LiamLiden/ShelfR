package com.example.leeseoye.shelfr;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class Ingredient {

    // Dates need to be stored
    private Calendar stored;
    private Calendar expired;
    private Calendar deleted;
    private Calendar current;
    // Information about the ingredients
    private Food food;
    private int amount;
    private String place;


    public Ingredient (Food f, String storage, int expiration, int amount){
        // Set the dates for ingredients
        stored = Calendar.getInstance();
        expired = Calendar.getInstance();
        expired.add(Calendar.DATE, expiration);
        deleted = Calendar.getInstance();
        current = stored;

        food = f;
        place = storage;
        this.amount = amount;
    }

    public void updateCurrent (){
        current = Calendar.getInstance();
    }

    public int daysLeft (){
        updateCurrent();
        // Calculate the remain date from Milliseconds
        return (int)((expired.getTimeInMillis()-current.getTimeInMillis())/86400000);
    }

    public void reduceAmount (){
        amount--;
    }

    public boolean usedFood (){
        if (amount == 0)
            return true;
        return false;
    }

    public void deleteIngredient (){
        //TODO: taking care of canceling the notification if it is used up before the expiration date
        //TODO: write the deleted and amount information to database file
    }

    private void setAlarm(Context context){
        // Set alarm time at noon (12PM) of the day before expiration date
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.setTime(expired.getTime());
        alarmTime.add(Calendar.DATE, -1);
        alarmTime.set(Calendar.AM_PM, Calendar.PM);
        alarmTime.set(Calendar.HOUR, 12);
        alarmTime.set(Calendar.MINUTE, 0);

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("foodName", food.getName());
        intent.putExtra("storage", place);
        intent.putExtra("left", amount);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarm.set(alarm.RTC_WAKEUP, alarmTime.getTimeInMillis(), alarmIntent);

    }


}
