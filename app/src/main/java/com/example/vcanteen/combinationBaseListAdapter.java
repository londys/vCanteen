package com.example.vcanteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ListView;
import java.util.ArrayList;

public class combinationBaseListAdapter extends ArrayAdapter {

    ArrayList<food> foodList;
    Context c;
    LayoutInflater inflater;

    combinationBaseListAdapter(Context context, ArrayList<food> foodList){
        super(context, R.layout.combination_base_listview, foodList);
        this.c = context;
        this.foodList = foodList;
    }

    public class ViewHolder{
        TextView baseComName;
        TextView baseComPrice;
        RadioButton selectedBase;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.combination_base_listview,parent,false);
        }

        //view holder object
        final combinationBaseListAdapter.ViewHolder holder = new combinationBaseListAdapter.ViewHolder();

        //initalize our view
        holder.baseComName=(TextView) convertView.findViewById(R.id.baseComName);
        holder.baseComPrice=(TextView) convertView.findViewById(R.id.baseComPrice);
        holder.selectedBase=(RadioButton)convertView.findViewById(R.id.selectedBase);

        //assign data
        holder.baseComName.setText(foodList.get(position).foodName);
        holder.baseComPrice.setText("+ "+foodList.get(position).foodPrice+" Baht");


        return convertView;
    }


}
