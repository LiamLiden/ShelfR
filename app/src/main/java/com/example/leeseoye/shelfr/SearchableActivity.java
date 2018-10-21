package com.example.leeseoye.shelfr;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SearchableActivity extends Activity implements SearchView.OnQueryTextListener {

    public static final String NAME_KEY = "name";
    public static final String FREEZER_KEY = "freezerLife";
    public static final String FRIDGE_KEY = "fridgeLife";
    public static final String SHELF_KEY = "shelfLife";

    private ListView list;
    private ListViewAdapter adapter;
    private SearchView editSearch;
    public static ArrayList<Food> foodArrayList;

    boolean shelfID = false;
    boolean frozenID = false;

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //handleIntent(getIntent());
        setContentView(R.layout.activity_searchable);

        //foodArrayList.clear();

        try {

            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("task list", null);
            Type type = new TypeToken<ArrayList<Food>>() {}.getType();
            foodArrayList = gson.fromJson(json, type);
            if (foodArrayList == null) {
                foodArrayList = new ArrayList<Food>();
                BufferedReader f = new BufferedReader(new InputStreamReader(getAssets().open("food_db.txt")));
                Scanner s = new Scanner(f);
                String line;
                SearchableActivity searchAc = new SearchableActivity();
                while (s.hasNextLine()) {
                    line = s.nextLine();
                    foodArrayList.add(searchAc.readFood(line));
                }
                sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                json = gson.toJson(foodArrayList);
                editor.putString("task list", json);
                editor.apply();
            }
            //load

        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(foodArrayList);
        foodArrayList.remove(0);
        foodArrayList.remove(0);
        foodArrayList.remove(0);
        foodArrayList.remove(0);

        list = (ListView) findViewById(R.id.searchList);

        adapter = new ListViewAdapter(this);
        list.setAdapter(adapter);

        editSearch = (SearchView) findViewById(R.id.searchbox);
        editSearch.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SearchableActivity.this, foodArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), InfoActivity.class);

                Food currentFood = foodArrayList.get(position);
                String name = currentFood.getName();
                String freezerLife = currentFood.getFreezerLife();
                //System.out.println("FreezerLife: "+freezerLife1);
                String fridgeLife = currentFood.getFridgeLife();
                String shelfLife = currentFood.getShelfLife();

                intent.putExtra(NAME_KEY, name);
                intent.putExtra(FREEZER_KEY, freezerLife);
                intent.putExtra(FRIDGE_KEY, fridgeLife);
                intent.putExtra(SHELF_KEY, shelfLife);
                startActivityForResult(intent, 10);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    /*
        adapter = new FoodListAdapter(this, R.layout.food_search_option, foodArrayList);
        list.setAdapter(adapter);

        editSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }*/

    /*
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
    */
    private Food readFood(String line) {
        System.out.println(line);
        String temp;
        Scanner s = new Scanner(line);
        String name = "";
        String shelf = "";
        String fridge = "";
        String freezer = "";
        if (line.equals("ID:SHELF")) {
            shelfID = !shelfID;
            Food f = new Food("", "", "", "");
            return f;
        }
        if (line.equals("ID:PURCHASE_FROZEN")) {
            frozenID = !frozenID;
            Food f = new Food("", "", "", "");
            return f;
        }

        if (shelfID) {
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
                    } else {
                        shelf = temp + " " + s.next();
                    }
                    fridge = s.next() + " " + s.next();
                    freezer = s.next() + " " + s.next();
                    if (s.hasNext()) {
                        freezer = freezer + " " + s.next();
                    }
                } else {
                    if (name.equals(""))
                        name = name + temp;
                    else
                        name = name + " " + temp;

                }
            }
            Food f = new Food(name, fridge, freezer, shelf);
            return f;
        } else if (frozenID) {
            while (s.hasNext()) {
                temp = s.next();
                if (Character.isDigit(temp.charAt(0))) {
                    freezer = temp + " " + s.next();
                    fridge = s.next() + " " + s.next();
                    if (s.hasNext())
                        fridge = fridge + " " + s.next();
                } else {
                    if (name.equals(""))
                        name = name + temp;
                    else
                        name = name + " " + temp;

                }
            }
            Food f = new Food(name, fridge, freezer, shelf);
            return f;
        } else {
            while (s.hasNext()) {
                temp = s.next();
                if (Character.isDigit(temp.charAt(0))) {
                    fridge = temp + " " + s.next();
                    freezer = s.next() + " " + s.next();
                    if (s.hasNext())
                        freezer = freezer + " " + s.next();
                } else {
                    if (name.equals(""))
                        name = name + temp;
                    else
                        name = name + " " + temp;

                }
            }
            Food f = new Food(name, fridge, freezer, shelf);
            return f;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("onAR", "Reached SearchActive");
        super.onActivityResult(requestCode, resultCode, intent);
        setResult(resultCode, intent);
        Log.d("onAR", "Reached here");
        finish();
    }

}
