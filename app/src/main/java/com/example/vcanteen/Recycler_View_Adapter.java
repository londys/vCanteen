package com.example.vcanteen;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
    String slotString="";
    RecyclerView recyclerView;
    pickupSlot slot;

    public Recycler_View_Adapter(List<orderListData> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_layout, parent, false);
        View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_done_layout, parent, false);
        final View_Holder holder = new View_Holder(view);
        final View_Holder holder2 = new View_Holder(v2);
        if(holder.orderStatus.getText().equals("WAITING FOR PICK UP")) {

        }

        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_layout, parent, false);
                return new View_Holder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_done_layout, parent, false);
                return new Done_View_Holder(view);
        }





        //pressed cardview
        holder.cv.setOnClickListener(new View.OnClickListener(){
            TextView slotNumber;
            @Override
            public void onClick(View v) {
                System.out.println("pressed "+String.valueOf(holder.orderStatus.getText()));

                if(String.valueOf(holder.orderStatus.getText()).equals("WAITING FOR PICK UP")){
                    Retrofit retrofit2 = new Retrofit.Builder()
//                .baseUrl("http://www.json-generator.com/api/json/get/")
                            .baseUrl("http://vcanteen.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit2.create(JsonPlaceHolderApi.class);
                    System.out.println("My order id is "+holder.orderId.getText().toString().substring(10));
                    int i = Integer.parseInt(holder.orderId.getText().toString().substring(10));
                    Call<pickupSlot> call =  jsonPlaceHolderApi.getPickupSlot(i);

                    call.enqueue(new Callback<pickupSlot>() {
                        @Override
                        public void onResponse(Call<pickupSlot> call, Response<pickupSlot> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(context, "CODE: "+response.code(),
                                        Toast.LENGTH_LONG).show();
                                System.out.println("PROG onResponse getslot unsuccessful");
                                return;
                            }


                            slot = response.body();
                            System.out.println("SLOT A = "+response.body());
                            System.out.println("SLOT = "+Integer.toString(slot.getPickupSlot()));
                            slotString = Integer.toString(slot.getPickupSlot());
//                            holder.pickupSlot.setText("321"); //slot.getPickupSlot()

                            //display popup confirm pickup
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.popup_confirm_pickup);
                            slotNumber =(TextView)dialog.findViewById(R.id.pickup_slot_number);
                            slotNumber.setText(Integer.toString(slot.getPickupSlot()));
                            dialog.setCancelable(true);

                            (dialog.findViewById(R.id.dismiss_btn))
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();

                                            slotNumber.setText("");
                                            System.out.println("current slot number = "+slotNumber.getText());
                                        }
                                    });

                            (dialog.findViewById(R.id.confirmPickup_btn))
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();

                                            slotNumber.setText("");
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

                                            Call<orderStatus> call3 =  jsonPlaceHolderApi.putOrderStatus(i);
                                            call3.enqueue(new Callback<orderStatus>() {

                                                @Override
                                                public void onResponse(Call<orderStatus> call3, Response<orderStatus> response) {
                                                    if(!response.isSuccessful()) {
                                                        Toast.makeText(context, "CODE: "+response.code(),
                                                                Toast.LENGTH_LONG).show();
                                                        System.out.println("PICKUP onResponse collected unsuccessful");
                                                        System.out.println("Current - orderStatus : "+String.valueOf(holder.orderStatus.getText()));
                                                        return;
                                                    }
//                                                    refresh();
                                                    System.out.println("Current + orderStatus : "+String.valueOf(holder.orderStatus));
//                                                    orderListData delete = holder.getAdapterPosition();
//                                                    list.remove(getAdapterPosition());
//                                                    remove();

                                                }

                                                @Override
                                                public void onFailure(Call<orderStatus> call3, Throwable t) {

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

        OrderListData object = mList.get(position);
        if (object != null) {
            switch (object.getType()) {
                case CITY_TYPE:
                    ((CityViewHolder) holder).mTitle.setText(object.getName());
                    break;
                case EVENT_TYPE:
                    ((EventViewHolder) holder).mTitle.setText(object.getName());
                    ((EventViewHolder) holder).mDescription.setText(object.getDescription());
                    break;
            }
        }




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
    List<orderListData> data;
//    public void refresh() {
//
//        data = new ArrayList<>();
//        System.out.println("Loading Progress Data");
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://vcanteen.herokuapp.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//
//        Call<List<orderProgress>> call =  jsonPlaceHolderApi.getProgress(1);
//
//        call.enqueue(new Callback<List<orderProgress>>() {
//            @Override
//            public void onResponse(Call<List<orderProgress>> call, Response<List<orderProgress>> response) {
//                if(!response.isSuccessful()) {
//                    Toast.makeText(context, "CODE: "+response.code(),
//                            Toast.LENGTH_LONG).show();
////                    mSwipeRefreshLayout.setRefreshing(false);
//                    return;
//                }
//
//                List<orderProgress> posts = response.body();
//                System.out.println(posts.toString());
//
//                for (orderProgress post : posts) {
//                    data.add(new orderListData(Integer.toString(post.getOrderId()),Integer.toString(post.getOrderPrice()),post.getOrderName(),post.getOrderNameExtra(), post.getRestaurantName(), post.getCreatedAt(), post.getOrderStatus()));
//                }
//                System.out.println("checkpoint");
//
//                Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, context);
//
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
////                mSwipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(Call<List<orderProgress>> call, Throwable t) {
//                Toast.makeText(context, "ERROR: "+t.getMessage(),
//                        Toast.LENGTH_LONG).show();
//                System.out.println("some error");
////                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }

}