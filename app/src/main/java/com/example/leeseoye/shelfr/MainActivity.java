package com.example.leeseoye.shelfr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Food> foodArrayList;
    boolean shelfID = false;
    boolean frozenID = false;

    //kefir, pudding, pasteurized crab meat, main dishes
    // not included (package date issue might come up again)
    //Commercial brand vacuum packed dinners with USDA seal 2 weeks Does not freeze well
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean readFood(String line){
        if(line.equals("ID:SHELF"))
            shelfID = !shelfID;
        if(line.equals("ID:PURCHASE_FROZEN"))
            frozenID = !frozenID;

        if(shelfID){

        }

    }
}
