package com.example.vcanteen;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public class Recycler_View_Adapter extends RecyclerView.Adapter<View_Holder> {

    List<orderListData> list = Collections.emptyList();
    Context context;

    public Recycler_View_Adapter(List<orderListData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.orderId.setText("Order ID: "+list.get(position).orderId);
        holder.orderPrice.setText(list.get(position).orderPrice+".-");
//        holder.imageView.setImageResource(list.get(position).imageId);
        holder.orderName.setText(list.get(position).orderName);
        holder.orderNameExtra.setText(list.get(position).orderNameExtra);
        holder.vendorName.setText(list.get(position).vendorName);
        holder.orderDate.setText(list.get(position).orderDate);
        if (list.get(position).orderStatus.equals("COLLECTED")) {
            holder.orderStatus.setTextColor(Color.GREEN);
        } else if (list.get(position).orderStatus.matches("CANCELLED|TIMEOUT|DONE")) {
            holder.orderStatus.setTextColor(Color.RED);
        }
        holder.orderStatus.setText(list.get(position).orderStatus);
        if (list.get(position).orderStatus.equals("DONE")) {
            holder.orderStatus.setText("WAITING FOR PICKUP");
        }

        //animate(holder);

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, orderListData data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(orderListData data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

}