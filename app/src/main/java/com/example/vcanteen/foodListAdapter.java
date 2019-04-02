package com.example.vcanteen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class foodListAdapter extends ArrayAdapter {

    int s;
    ArrayList<food> foodList;
    Context c;
    LayoutInflater inflater;

    foodListAdapter(Context context, ArrayList<food> foodList, int s){
        super(context, R.layout.food_listview , foodList);
        this.c = context;
        this.foodList = foodList;
        this.s = s;
    }

    public class ViewHolder{
        TextView addFoodName;
        TextView addFoodPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.food_listview,parent,false);
        }

        //view holder object
        final foodListAdapter.ViewHolder holder = new foodListAdapter.ViewHolder();

        //initalize our view
        holder.addFoodName=(TextView) convertView.findViewById(R.id.mainComName);
        holder.addFoodPrice=(TextView) convertView.findViewById(R.id.mainComPrice);

        //assign data
        holder.addFoodName.setText(foodList.get(position).foodName);
        holder.addFoodPrice.setText("+ "+foodList.get(position).foodPrice+" Baht");

        if(position>=s) { //for sold out
            holder.addFoodName.setTextColor(Color.parseColor("#E5E5E5"));
            holder.addFoodPrice.setTextColor(Color.parseColor("#C4C4C4"));
        }

        return convertView;
    }

}
