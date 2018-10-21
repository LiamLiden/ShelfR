package com.example.leeseoye.shelfr;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FoodListAdapter extends BaseAdapter{

    private Context myContext;
    LayoutInflater inflater;
    private int resource;
    private ArrayList<Food> foodList;
    //private List<Food> foodStrictList = null;

    public FoodListAdapter(Context context){
        myContext = context;
        inflater = LayoutInflater.from(myContext);
        this.foodList = new ArrayList<Food>();
        this.foodList.addAll(SearchableActivity.foodArrayList);
    }

    public class ViewHolder{
        TextView name;
    }

    /*public FoodListAdapter(@NonNull Context c, @LayoutRes int resource, ArrayList<Food> foodsArray) {
        super(c, resource, foodsArray);
        this.c = c;
        this.resource = resource;
        this.foodList = foodsArray;
        this.tempList = foodsArray;
    }*/

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        /*LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.food_search_option, null);

        TextView t = (TextView)row.findViewById(R.id.searchFoodName);

        t.setText(foodList.get(position).getName());
        return null;*/

        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(myContext).inflate(R.layout.food_search_option, parent, false);

        Food currentFood = foodList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.searchFoodName);
        name.setText(currentFood.getName().toString());
        return listItem;
    }

    @Override
    public Food getItem(int position){
        return foodList.get(position);
    }

    @Override
    public int getCount(){
        return foodList.size();
    }

    @Override
    public long getItemId(int i){
        return foodList.indexOf(getItemId(i));
    }

    /*
    @Override
    public Filter getFilter(){
        return null;
    }*/
    // Filter Class
    /*
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        foodStrictList.clear();
        if (charText.length() == 0) {
            foodStrictList.addAll(foodList);
        } else {
            for (Food current : foodList) {
                if (current.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    foodStrictList.add(current);
                }
            }
        }
        notifyDataSetChanged();
    }*/

}
