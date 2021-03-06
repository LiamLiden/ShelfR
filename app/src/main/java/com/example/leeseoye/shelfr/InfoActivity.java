package com.example.leeseoye.shelfr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InfoActivity extends AppCompatActivity {
    //Quantities that should be purchased if shelved, refrigerated, or frozen
    int sQ = 0, rQ = 0, fQ = 0, temp;
    Food food;
    String tempName = "";
    String name = "";
    double sA = 0, rA = 0, fA = 0;
    String title;



    Button shelf, fridge, freezer;
    EditText quantity;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        food = new Food(getIntent().getStringExtra("name"),
                getIntent().getStringExtra("fridgeLife"),
                getIntent().getStringExtra("freezerLife"),
                getIntent().getStringExtra("shelfLife"));

        String nam = food.getName();
        String she = food.getShelfLife();
        String fri = food.getFridgeLife();
        String fre = food.getFreezerLife();
        String str = "";
        int countA = 0;
        int countB = 0;
        int countC = 0;

        if(she.matches(".*\\d+.*")){
            countA++;
        }
        if(fri.matches(".*\\d+.*")){
            countB++;
        }
        if(fre.matches(".*\\d+.*")){
            countC++;
        }

        if(she.matches(".*\\d+.*")) {
            str = str + "Shelf: " + food.getShelfLife();
            if((countA+countB+countC)>1)
                str = str + ", ";
        }
        if(fri.matches(".*\\d+.*")) {
            str = str + "Fridge: " + food.getFridgeLife();
            if(countC==1)
                str = str + ", ";
            if((countA+countB+countC)==3)
                str = str+"\n";
        }
        if(fre.matches(".*\\d+.*"))
            str = str+"Freezer: "+food.getFreezerLife();


        TextView optimizer = findViewById(R.id.optimums);
        //optimizer.setText(findPurchase());


        title = getIntent().getStringExtra("name");
        imageView = findViewById(R.id.foodImage);

        optimizer.setText(str);

        TextView foodName = findViewById(R.id.name);
        foodName.setText(food.getName());
        loadFoodImage();


        shelf = findViewById(R.id.shelfButton);
        fridge = findViewById(R.id.fridgeButton);
        freezer = findViewById(R.id.freezerButton);
        quantity = findViewById(R.id.quantityText);

        if(!(str.contains("Shelf")))
            shelf.setVisibility(View.GONE);
        if(!(str.contains("Fridge")))
            fridge.setVisibility(View.GONE);
        if(!(str.contains("Freezer")))
            freezer.setVisibility(View.GONE);

        shelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = quantity.getText().toString();


                if (!temp.equals("")) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("expiration", food.toDays(food.getShelfLife()));
                    intent.putExtra("food", food.getName());
                    intent.putExtra("quantity", Character.getNumericValue(temp.charAt(0)));
                    intent.putExtra("storage", "S");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Log.d("info", "please send the intent to main");
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please input a quantity", Toast.LENGTH_LONG)
                            .show();


                }
            }

        });
        fridge.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                String temp = quantity.getText().toString();


                if (!temp.equals("")) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("expiration", food.toDays(food.getFridgeLife()));
                    intent.putExtra("food", food.getName());
                    intent.putExtra("quantity", Character.getNumericValue(temp.charAt(0)));
                    intent.putExtra("storage", "R");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Log.d("info", "please send the intent to main");
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please input a quantity", Toast.LENGTH_LONG)
                            .show();


                }
            }

        });
        freezer.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                String temp = quantity.getText().toString();


                if (!temp.equals("")) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("expiration", food.toDays(food.getFreezerLife()));
                    intent.putExtra("food", food.getName());
                    intent.putExtra("quantity", Character.getNumericValue(temp.charAt(0)));
                    intent.putExtra("storage", "F");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Log.d("info", "please send the intent to main");
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please input a quantity", Toast.LENGTH_LONG)
                            .show();


                }

            }

        });
    }

    private void loadFoodImage(){

        String nameI = title.toLowerCase();

        if (nameI.contains("juice"))
            imageView.setImageResource(R.drawable.juice);
        else if (nameI.contains("cheese")||nameI.contains("milk")||nameI.contains("cream")
                ||nameI.contains("parmesan")||nameI.contains("cheddar")||nameI.contains("butter")
                ||nameI.contains("margarine")||nameI.contains("yogurt"))
            imageView.setImageResource(R.drawable.dairy);
        else if (nameI.contains("egg"))
            imageView.setImageResource(R.drawable.eggs);
        else if (nameI.contains("fish")||nameI.contains("sea")||nameI.contains("shrimp")||nameI.contains("crab")
                ||nameI.contains("clam")||nameI.contains("lobster"))
            imageView.setImageResource(R.drawable.fish);
        else if (nameI.contains("beef")||nameI.contains("meat")||nameI.contains("pork")||nameI.contains("chicken")
                ||nameI.contains("bacon")||nameI.contains("ham")||nameI.contains("turkey")||nameI.contains("duckling"))
            imageView.setImageResource(R.drawable.eggs);
        else if (nameI.contains("sausage")||nameI.contains("hot dog"))
            imageView.setImageResource(R.drawable.sausage);
        else if (nameI.contains("apple")||nameI.contains("banana")||nameI.contains("berries")||nameI.contains("pear"))
            imageView.setImageResource(R.drawable.fruit);
        else
            imageView.setImageResource(R.drawable.food);

    }


    /*
    public String findPurchase() {

        try {
            BufferedReader f = new BufferedReader(new InputStreamReader(getAssets().open("history_log.txt")));
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                tempName = s.next();
                if (Character.isLetter(tempName.charAt(0)))
                    name.concat(tempName);
                else if (name.equals(food.getName())) {
                    temp = Character.getNumericValue(s.next().charAt(0));
                    if (s.next().equals("S")) {
                        sA = sQ * sA;
                        sQ += temp;
                        sA += Character.getNumericValue(s.next().charAt(0));
                        sA = sA / sQ;
                    } else if (s.next().equals("R")) {
                        rA = rQ * rA;
                        rQ += temp;
                        rA += Character.getNumericValue(s.next().charAt(0));
                        rA = rA / rQ;
                    } else {
                        fA = fQ * fA;
                        fQ += temp;
                        fA += Character.getNumericValue(s.next().charAt(0));
                        fA = fA / fQ;
                    }
                } else
                    s.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int freezeLife;
        int shelfLife;
        int fridgeLife;
        if(food.toDays(food.getFreezerLife()) == -1){
            freezeLife = 0;
        }
        else{
            freezeLife = food.toDays(food.getFreezerLife());
        }
        if(food.toDays(food.getFreezerLife()) == -1){
            fridgeLife = 0;
        }
        else{
            fridgeLife = food.toDays(food.getFridgeLife());
        }
        if(food.toDays(food.getShelfLife())==-1){
            shelfLife = 0;
        }
        else{
            shelfLife = food.toDays(food.getShelfLife());
        }

        return "Shelfed: " + shelfLife / sA + "Refrigerated: " + fridgeLife / rA + "Frozen: " + food.toDays(food.getFreezerLife())/fA;
    }
    */

}
