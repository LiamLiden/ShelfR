package com.example.leeseoye.shelfr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter{

    //Declare variables

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Food> arrayList;

    public ListViewAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Food>();
        this.arrayList.addAll(SearchableActivity.foodArrayList);
    }

    public class ViewHolder{
        TextView name;
    }

    @Override
    public int getCount() {
        return SearchableActivity.foodArrayList.size();
    }

    @Override
    public Food getItem(int position) {
        return SearchableActivity.foodArrayList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent){
        final ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.food_search_option, null);
            holder.name = (TextView) view.findViewById(R.id.searchFoodName);
            view.setTag(holder);
        } else{
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(SearchableActivity.foodArrayList.get(position).getName());
        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        SearchableActivity.foodArrayList.clear();
        if (charText.length() == 0){
            SearchableActivity.foodArrayList.addAll(arrayList);
        } else{
            for(Food wp : arrayList){
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    SearchableActivity.foodArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
