package com.example.vcanteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class orderListAdapter extends ArrayAdapter {

    //declarations
    int[]b={}; //price
    String[]a={}; // name
    String[]d={}; // info
    Context c;
    LayoutInflater inflater;

    orderListAdapter(Context context, String[] a, int[] b, String[] d){
        super(context, R.layout.order_listview , a);
        this.c=context;
        this.a=a;
        this.b=b;
        this.d=d;
    }

    public class ViewHolder{
        TextView orderName;
        TextView orderPrice;
        TextView orderInfo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_listview,parent,false);
        }

        //view holder object
        final orderListAdapter.ViewHolder holder = new orderListAdapter.ViewHolder();

        //initalize our view
        holder.orderName=(TextView) convertView.findViewById(R.id.orderName);
        holder.orderPrice=(TextView) convertView.findViewById(R.id.orderPrice);
        holder.orderInfo = (TextView) convertView.findViewById(R.id.orderInfo);

        //assign data
        holder.orderName.setText(a[position]);
        holder.orderPrice.setText(""+b[position]+" ฿");
        holder.orderInfo.setText(d[position]);

        return convertView;
    }

}
