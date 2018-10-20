package com.example.leeseoye.shelfr;

import java.util.Iterator;

public class Food {

    public Food(String n, String fridge, String freeze, String shelf{
        name = n;
        fridgeLife = fridge;
        freezerLife = freeze;
        shelfLife = shelf;
    }

    public String getName(){
        return name;
    }
    public String getFreezerLife() {
        return freezerLife;
    }
    public String getFridgeLife() {
        return fridgeLife;
    }
    public String getShelfLife() {
        return shelfLife;
    }

    public Integer toDays(String life){

        String [] strs = life.split(" ");
        String [] strs2 = strs[0].split("-");

        Integer amount = Integer.parseInt(strs2[0]);

        if(strs[1].contains("week")){
            amount *= 7;
        }
        else if(strs[1].contains("month")){
            amount *= 30;
        }
        else if(strs[1].contains("year")){
            amount *= 365;
        }

        return amount;
    }


    //private field declarations
    private String name;
    private String fridgeLife;
    private String freezerLife;
    private String shelfLife;
}
