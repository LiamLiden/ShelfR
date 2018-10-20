package com.example.leeseoye.shelfr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView optimizer = findViewById(R.id.optimums);
        optimizer.setText(findPurchase());
        TextView foodName = findViewById(R.id.name);
        foodName.setText(food.getName());
        setContentView(R.layout.activity_info);
    }

    public String findPurchase(){
        food = new Food("Butter", "1-3 months", "6-9 months", "");
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
}
