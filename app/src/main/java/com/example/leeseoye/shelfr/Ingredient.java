package com.example.leeseoye.shelfr;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

public class Ingredient implements Serializable {

    // Dates need to be stored
    private Calendar stored;
    private Calendar expired;
    private Calendar deleted;
    private Calendar current;
    // Information about the ingredients
    //private Food food;
    private int amount;
    private String place;
    private String name;
    private Context context;
    private int duration;
    private long expireTime;

    public Ingredient(String storage, int expiration, long expireTime, int amount, String name, Context context){
        place = storage;
        duration = expiration;
        this.expireTime = expireTime;
        this.amount = amount;
        stored = Calendar.getInstance();
        expired = Calendar.getInstance();
        expired.add(Calendar.DATE, (int) ((expireTime - Calendar.getInstance().getTimeInMillis())/86400000));
        this.name = name;
        this.context = context;
        setAlarm(context);
    }


    public Ingredient(String storage, int expiration, int amount, String name, Context context){
        // Set the dates for ingredients
        duration = expiration;
        stored = Calendar.getInstance();

        expired = Calendar.getInstance();
        expired.add(Calendar.DATE, expiration);
        expireTime = stored.getTimeInMillis();
        deleted = Calendar.getInstance();
        current = stored;
        this.context = context;
        // Set alarm for this ingredients;
        setAlarm(context);

        this.name = name;
        place = storage;
        this.amount = amount;
    }

    public int getDuration(){
        return duration;
    }

    public Calendar getExpiredCal(){
        return expired;
    }
    public int DaysLeftfromMilli(){
        return (int) ((expireTime - Calendar.getInstance().getTimeInMillis())/86400000);

    }

    public String getName(){
        return name;
    }

    public int getAmount() { return amount; }

    public String getPlace() { return place; }


    public void updateCurrent (){
        current = Calendar.getInstance();
    }



    public int daysLeft (){
        updateCurrent();
        // Calculate the remain date from Milliseconds
        return dayDiff(current, expired)+1;
    }

    public int daysPassed(){
        return duration - (daysLeft() +1);
    }

    /**
     * @oaram Calendar c1 and Calendar c2
     * @produce the difference in days of c2 and c1 (c2 - c1)
     * */
    private static int dayDiff (Calendar c1, Calendar c2){

        return (int)((c2.getTimeInMillis()-c1.getTimeInMillis())/86400000);
    }

    public void reduceAmount (){
        amount--;
    }

    public String daysLeftString(){
        int temp = daysLeft();
        return new Integer(temp).toString();
    }

    public boolean usedFood (){
        if (amount == 0)
            return true;
        return false;
    }

    public void deleteIngredient (){
        reduceAmount();
        //TODO: taking care of canceling the notification if it is used up before the expiration date
        //TODO: write the deleted and amount information to database file
    }

    public void setAlarm(Context context){
        // Set alarm time at noon (12PM) of the day before expiration date
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.setTime(expired.getTime());
        alarmTime.add(Calendar.DATE, -1);
        alarmTime.set(Calendar.AM_PM, Calendar.PM);
        alarmTime.set(Calendar.HOUR, 12);
        alarmTime.set(Calendar.MINUTE, 0);

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("foodName", getName());
        intent.putExtra("storage", place);
        intent.putExtra("left", amount);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Log.d("ingredient", "set alarm  " );
        alarm.set(alarm.RTC_WAKEUP, alarmTime.getTimeInMillis(), alarmIntent);
        Log.d("ingredient", "at" +alarmTime );

    }


}
