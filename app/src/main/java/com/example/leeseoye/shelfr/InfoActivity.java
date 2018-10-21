package com.example.leeseoye.shelfr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    Button shelf, fridge, freezer;
    EditText quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        food = new Food(getIntent().getStringExtra("name"),
                getIntent().getStringExtra("fridgeLife"),
                getIntent().getStringExtra("freezerLife"),
                getIntent().getStringExtra("shelfLife"));

        TextView optimizer = findViewById(R.id.optimums);
        //optimizer.setText(findPurchase());
        TextView foodName = findViewById(R.id.name);
        foodName.setText(food.getName());


        shelf = findViewById(R.id.shelfButton);
        fridge = findViewById(R.id.fridgeButton);
        freezer = findViewById(R.id.freezerButton);
        quantity = findViewById(R.id.quantityText);


        shelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), MainActivity.class);
                intent.putExtra("expiration", food.toDays(food.getShelfLife()));
                intent.putExtra("food", food.getName());
                intent.putExtra("quantity", Character.getNumericValue(quantity.getText().toString().charAt(0)));

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Log.d("info","please send the intent to main");
                setResult(RESULT_OK,intent);

                finish();
            }
        });
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), MainActivity.class);
                intent.putExtra("expiration", food.toDays(food.getFridgeLife()));
                intent.putExtra("food", food.getName());
                intent.putExtra("quantity", Character.getNumericValue(quantity.getText().toString().charAt(0)));
                intent.putExtra("storage", "fridge");
                setResult(RESULT_OK,intent);
                finish();

            }
        });
        freezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), MainActivity.class);
                intent.putExtra("expiration", food.toDays(food.getFreezerLife()));
                intent.putExtra("food", food.getName());
                intent.putExtra("quantity", Character.getNumericValue(quantity.getText().toString().charAt(0)));
                intent.putExtra("storage", "freezer");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    /*

    public String findPurchase(){
        try {
            BufferedReader f = new BufferedReader(new InputStreamReader(getAssets().open("history_log.txt")));
            Scanner s = new Scanner(f);
            while (s.hasNextLine()){
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
                }
                else
                    s.nextLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "Shelfed: " + food.toDays(food.getShelfLife())/sA + "Refrigerated: " + food.toDays(food.getFridgeLife())/rA + "Frozen: " + food.toDays(food.getFreezerLife());
    }
    */
}
