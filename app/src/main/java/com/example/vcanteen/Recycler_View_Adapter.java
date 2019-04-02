package com.example.vcanteen;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import bolts.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recycler_View_Adapter extends RecyclerView.Adapter<View_Holder> {

    List<orderListData> list = Collections.emptyList();
    Context context;
    String slotString="10";

    public Recycler_View_Adapter(List<orderListData> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_layout, parent, false);
        final View_Holder holder = new View_Holder(v);

        holder.cv.setOnClickListener(new View.OnClickListener(){
            TextView slotNumber;
            @Override
            public void onClick(View v) {
                System.out.println("pressed "+String.valueOf(holder.orderStatus.getText()));

                if(String.valueOf(holder.orderStatus.getText()).equals("WAITING FOR PICK UP")){
                    Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://www.json-generator.com/api/json/get/")
                            .baseUrl("http://vcanteen.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                    System.out.println("My order id is "+holder.orderId.getText().toString().substring(10));
                    int i = Integer.parseInt(holder.orderId.getText().toString().substring(10));
                    Call<pickupSlot> call =  jsonPlaceHolderApi.getPickupSlot(i);

                    call.enqueue(new Callback<pickupSlot>() {
                        @Override
                        public void onResponse(Call<pickupSlot> call, Response<pickupSlot> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(context, "CODE: "+response.code(),
                                        Toast.LENGTH_LONG).show();
                                System.out.println("PROG onResponse unsuccessful");
                                return;
                            }

                            pickupSlot slot = response.body();
                            System.out.println("SLOT A = "+response.body());
                            System.out.println("SLOT = "+Integer.toString(slot.getPickupSlot()));
                            slotString = Integer.toString(slot.getPickupSlot());
//                            holder.pickupSlot.setText("321"); //slot.getPickupSlot()

                            //display popup confirm pickup
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.popup_confirm_pickup);
                            slotNumber =(TextView)dialog.findViewById(R.id.pickup_slot_number);
                            slotNumber.setText(slotString);
                            dialog.setCancelable(true);

                            (dialog.findViewById(R.id.dismiss_btn))
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                            (dialog.findViewById(R.id.confirmPickup_btn))
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();

                                            //Send endpoint putOrderStatus



                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl("http://vcanteen.herokuapp.com/")
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();
                                            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                                            System.out.println("My order id is "+holder.orderId.getText().toString().substring(10));

//                                            orderStatus orderStatus2 = new orderStatus("COLLECTED");
//                                            Call<orderStatus> call = jsonPlaceHolderApi.putOrderStatus(orderStatus2);


//                                            Task task = new Task(1, "my task title");
//                                            Call<Task> call = taskService.createTask(task);
//                                            call.enqueue(new Callback<orderStatus>() {});


                                            int i = Integer.parseInt(holder.orderId.getText().toString().substring(10));

                                            Call<orderStatus> call =  jsonPlaceHolderApi.putOrderStatus(i);
                                            call.enqueue(new Callback<orderStatus>() {

                                                @Override
                                                public void onResponse(Call<orderStatus> call, Response<orderStatus> response) {
                                                    if(!response.isSuccessful()) {
                                                        Toast.makeText(context, "CODE: "+response.code(),
                                                                Toast.LENGTH_LONG).show();
                                                        System.out.println("PICKUP onResponse collected unsuccessful");
                                                        System.out.println("Current - orderStatus : "+String.valueOf(holder.orderStatus.getText()));
                                                        return;
                                                    }

                                                    System.out.println("Current + orderStatus : "+String.valueOf(holder.orderStatus));
                                                }

                                                @Override
                                                public void onFailure(Call<orderStatus> call, Throwable t) {

                                                }
                                            });

                                        }
                                    });

                            dialog.show();

                        }

                        @Override
                        public void onFailure(Call<pickupSlot> call, Throwable t) {
                            Toast.makeText(context, "ERROR: "+t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            System.out.println("PROG some error");
                        }
                    });

                }
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.orderId.setText("Order ID: "+list.get(position).orderId);
        holder.orderPrice.setText(list.get(position).orderPrice+".-");
        holder.orderName.setText(list.get(position).orderName);
        holder.orderNameExtra.setText(list.get(position).orderNameExtra);
        holder.vendorName.setText(list.get(position).vendorName);
        holder.orderDate.setText(list.get(position).orderDate);
        holder.orderStatus.setText(list.get(position).orderStatus);
        if (list.get(position).orderStatus.equals("COLLECTED")) {
            holder.cv.setForeground(null);
            holder.orderStatus.setTextColor(Color.parseColor("#4DC031")); //green
            holder.orderStatus.setText(list.get(position).orderStatus);
        }
        if (list.get(position).orderStatus.equals("DONE")) {

            holder.orderStatus.setTextColor(Color.parseColor("#FF0606")); // red
            holder.orderStatus.setText("WAITING FOR PICK UP");
        }
        if (list.get(position).orderStatus.equals("TIMEOUT")) {
            holder.cv.setForeground(null);
            holder.orderStatus.setTextColor(Color.parseColor("#FF0606"));// red
            holder.orderStatus.setText(list.get(position).orderStatus);
        }
        if (list.get(position).orderStatus.equals("CANCELLED")) {
            holder.cv.setForeground(null);
            holder.orderStatus.setTextColor(Color.parseColor("#FF0606"));// red
            holder.orderStatus.setText(list.get(position).orderStatus);
        }
        if (list.get(position).orderStatus.equals("COOKING")) {
            holder.cv.setForeground(null);
            holder.orderStatus.setTextColor(Color.parseColor("#757575"));
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