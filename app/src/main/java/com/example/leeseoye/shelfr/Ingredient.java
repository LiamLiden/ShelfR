package com.example.leeseoye.shelfr;

import java.util.Calendar;
import java.util.Date;

public class Ingredient {

    private Calendar stored;
    private Calendar expired:
    private Calendar deleted;
    private Calendar current;

    private int amount;
    private String place;


    public Ingredient (String storage, int expiration, int amount){
        stored = Calendar.getInstance();
        expired = Calendar.getInstance();
        expired.add(Calendar.DATE, expiration);
        deleted = Calendar.getInstance();
        current = stored;

        place = storage;
        this.amount = amount;
    }

    public void updateCurrent (){
        current = Calendar.getInstance();
    }

    public int daysLeft (){
        updateCurrent();
        return (int)((expired.getTimeInMillis()-current.getTimeInMillis())/86400000);
    }

    public void reduceAmount (){
        amount--;
    }

    private void setAlarm(){
        //TODO connect with notification
    }



}
