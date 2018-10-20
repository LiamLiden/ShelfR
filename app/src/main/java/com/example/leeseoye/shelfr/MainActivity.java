package com.example.leeseoye.shelfr;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    //kefir, pudding, pasteurized crab meat, main dishes
    // not included (package date issue might come up again)
    //Commercial brand vacuum packed dinners with USDA seal 2 weeks Does not freeze well
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
