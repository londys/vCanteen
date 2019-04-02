package com.example.vcanteen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class combinationMainListAdapter extends ArrayAdapter {


    int s;
    ArrayList<food> foodList;
    Context c;
    LayoutInflater inflater;

    combinationMainListAdapter(Context context, ArrayList<food> foodList, int s){
        super(context, R.layout.combination_main_listview , foodList);
        this.c = context;
        this.foodList = foodList;
        this.s = s;
    }

    public class ViewHolder{
        TextView mainComName;
        TextView mainComPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.combination_main_listview,parent,false);
        }

        //view holder object
        final combinationMainListAdapter.ViewHolder holder = new combinationMainListAdapter.ViewHolder();

        //initalize our view
        holder.mainComName=(TextView) convertView.findViewById(R.id.mainComName);
        holder.mainComPrice=(TextView) convertView.findViewById(R.id.mainComPrice);

        //assign data
        holder.mainComName.setText(foodList.get(position).foodName);
        holder.mainComPrice.setText("+ "+foodList.get(position).foodPrice+" Baht");

        if(position>=s) { //for sold out
            holder.mainComName.setTextColor(Color.parseColor("#E5E5E5"));
            holder.mainComPrice.setTextColor(Color.parseColor("#C4C4C4"));
        }

        return convertView;
    }

}
