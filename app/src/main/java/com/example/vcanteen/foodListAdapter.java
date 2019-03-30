package com.example.vcanteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class foodListAdapter extends ArrayAdapter {

    foodListAdapter(Context context, String[] a){

        super(context, R.layout.food_listview , a);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater foodListInflater = LayoutInflater.from(getContext());
        View customView = foodListInflater.inflate(R.layout.food_listview, parent, false);

        String singleItem = (String) getItem(position);
        TextView extraFood = (TextView) customView.findViewById(R.id.extraFood);

        extraFood.setText(singleItem);
        return customView;
    }

}
