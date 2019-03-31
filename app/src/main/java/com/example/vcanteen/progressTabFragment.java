package com.example.vcanteen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class progressTabFragment extends Fragment  {
    private static final String TAG = "ProgressTabFragment";
//    String text = "";
    List<orderListData> data = new ArrayList<>();
//    private String orderId = "0";
//    private String orderName = "Test";
//    private String orderNameExtra = "TestExtra";
//    private String foodImage = "";
//    private String orderPrice = "20";
//    private String restaurantName = "Stall";
//    private String restaurantNumber = ""; //number or id
//    private String orderStatus = "DONE";
//    private String createdAt = "1/1/18";

    private boolean pass = false;

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_tab_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.progress_recycler);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.json-generator.com/api/json/get/")
//                .baseUrl("http://vcanteen.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<orderProgress>> call =  jsonPlaceHolderApi.getProgress();
//        Call<JSONResponse> call = jsonPlaceHolderApi.getJSON();

        call.enqueue(new Callback<List<orderProgress>>() {
            @Override
            public void onResponse(Call<List<orderProgress>> call, Response<List<orderProgress>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "CODE: "+response.code(),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                List<orderProgress> posts = response.body();
                showData(response.body());
                for (orderProgress post : posts) {
                    data.add(new orderListData(Integer.toString(post.getOrderId()),Integer.toString(post.getOrderPrice()),post.getOrderName(),post.getOrderNameExtra(), post.getRestaurantName(), post.getCreatedAt(), post.getOrderStatus()));

                }
            }

            @Override
            public void onFailure(Call<List<orderProgress>> call, Throwable t) {
                Toast.makeText(getActivity(), "ERROR: "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println("some error");
            }
        });




         System.out.println("object added..");

//        while(!pass){
//        }
        System.out.println("my array: " +data.toString());
        data = fill_with_data();


//        Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, this.getActivity());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return view;
    }

    private void showData(List<orderProgress> body) {
        Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, this.getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }


    public List<orderListData> fill_with_data() {

//        List<orderListData> data = new ArrayList<>();

//        data.add(new orderListData(text+"-","10","kao mun kai", "No Veg", "Noodle Stall", "01/02/2019", "COOKING"));
//        data.add(new orderListData(orderId,orderPrice,orderName,orderNameExtra, restaurantName, createdAt, orderStatus));
        return data;
    }
}
