package com.example.vcanteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class foodListAdapter extends ArrayAdapter {

    //declarations
    int[]b={};
    String[]a={};
    Context c;
    LayoutInflater inflater;

    foodListAdapter(Context context, String[] a, int[] b){
        super(context, R.layout.food_listview , a);
        this.c=context;
        this.a=a;
        this.b=b;
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
        final ViewHolder holder = new ViewHolder();

        //initalize our view
        holder.addFoodName=(TextView) convertView.findViewById(R.id.addFoodName);
        holder.addFoodPrice=(TextView) convertView.findViewById(R.id.addFoodPrice);

        //assign data
        holder.addFoodName.setText(a[position]);
        holder.addFoodPrice.setText("+ "+b[position]+" THB");

        return convertView;
    }

}
