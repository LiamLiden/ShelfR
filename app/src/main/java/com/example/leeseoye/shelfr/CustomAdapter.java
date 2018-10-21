package com.example.leeseoye.shelfr;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Ingredient>
{
    private Context cContext;
    private ArrayList<Ingredient> ingredientList;
    int resource;
    public CustomAdapter(@NonNull Context context, int resource, @LayoutRes ArrayList<Ingredient> list) {
        super(context, resource , list);
        this.resource = resource;
        cContext = context;
        ingredientList = list;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(cContext).inflate(R.layout.activity_listview,parent,false);

        Ingredient currentIngredient = ingredientList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.label);
        name.setText(currentIngredient.getName());

        TextView release = (TextView) listItem.findViewById(R.id.expiration);
        Log.d("ThisRandomThing", String.valueOf(currentIngredient.getAmount()));
        release.setText( currentIngredient.daysLeftString() + " days left\n"
                        + "Quantity: " +currentIngredient.getAmount());

        return listItem;
    }
}