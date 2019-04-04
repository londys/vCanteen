package com.example.vcanteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class confirmedOrderListAdapter extends ArrayAdapter {

    orderStack orderStack;
    Context c;
    LayoutInflater inflater;

    confirmedOrderListAdapter(Context context, orderStack orderStack){
        super(context, R.layout.confirmed_order_listview , orderStack.orderList);
        this.c=context;
        this.orderStack = orderStack;
    }

    public class ViewHolder{
        TextView orderName;
        TextView orderPrice;
        TextView orderNameExtra;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView==null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.confirmed_order_listview,parent,false);
        }

        //view holder object
        final confirmedOrderListAdapter.ViewHolder holder = new confirmedOrderListAdapter.ViewHolder();

        //initalize our view
        holder.orderName = (TextView) convertView.findViewById(R.id.orderName);
        holder.orderPrice = (TextView) convertView.findViewById(R.id.orderPrice);
        holder.orderNameExtra = (TextView) convertView.findViewById(R.id.orderNameExtra);

        //assign data
        holder.orderName.setText(orderStack.orderList.get(position).orderName);
        holder.orderPrice.setText(""+orderStack.orderList.get(position).orderPrice+ " Baht");

        if(orderStack.orderList.get(position).orderNameExtra==null){
            holder.orderNameExtra.setText("");
        }else{
            holder.orderNameExtra.setText(orderStack.orderList.get(position).orderNameExtra);
        }

        return convertView;
    }

}
