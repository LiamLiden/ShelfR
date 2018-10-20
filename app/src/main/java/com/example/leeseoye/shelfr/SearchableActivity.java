package com.example.leeseoye.shelfr;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchableActivity extends Activity {


    ArrayList<Food> foodArrayList;

    boolean shelfID = false;
    boolean frozenID = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        */
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        //(R.layout.activity_main);
        //setContentView(R.menu.search_menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent (Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            search(query);
        }
    }

    private void search (String query){
        try {
            BufferedReader f = new BufferedReader(new InputStreamReader(getAssets().open("food_db.txt")));
            Scanner s = new Scanner(f);
            String line;
            while (s.hasNextLine()) {
                line = s.nextLine();
                foodArrayList.add(readFood(line));
            }
        }
        catch(IOException e){
                e.printStackTrace();
            }

    }

    public Food readFood(String line){
        String temp;
        Scanner s = new Scanner(line);
        String name = "";
        String shelf = "";
        String fridge = "";
        String freezer = "";
        if(line.equals("ID:SHELF"))
            shelfID = !shelfID;
        if(line.equals("ID:PURCHASE_FROZEN"))
            frozenID = !frozenID;

        if(shelfID){
            /* Weird idea to parse the data
            for (char c : arr){
                if (Character.isDigit(c)) {
                    shelf.concat(Character.toString(c));
                    break;
                }
                else
                    name.concat(Character.toString(c));
            }
            */
            while (s.hasNext()) {
                temp = s.next();
                if (Character.isDigit(temp.charAt(0))) {
                    if (temp.charAt(0) == '0') {
                        shelf = "Do not shelf";
                    }
                    else {
                        shelf = temp.concat(s.next());
                    }
                    fridge = s.next().concat(s.next());
                    freezer = s.next().concat(s.next());
                    if (s.hasNext()) {
                        freezer.concat(s.next());
                    }
                }
                else
                    name.concat(temp);
            }
            Food f = new Food(name, shelf, fridge, freezer);
            return f;
        }
        else if(frozenID){
            while (s.hasNext()) {
                temp = s.next();
                if (Character.isDigit(temp.charAt(0))) {
                    freezer = temp.concat(s.next());
                    fridge = s.next().concat(s.next());
                    if (s.hasNext())
                        fridge.concat(s.next());
                } else
                    name.concat(temp);
            }
            Food f = new Food(name, shelf, fridge, freezer);
            return f;
        }
        else {
            while (s.hasNext()) {
                temp = s.next();
                if (Character.isDigit(temp.charAt(0))) {
                    fridge = temp.concat(s.next());
                    freezer = s.next().concat(s.next());
                    if (s.hasNext())
                        freezer.concat(s.next());
                } else
                    name.concat(temp);
            }
            Food f = new Food(name, shelf, fridge, freezer);
            return f;
        }
    }
}
