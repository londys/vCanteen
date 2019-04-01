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

public class historyTabFragment extends Fragment {
    private static final String TAG = "HistoryTabFragment";
    List<orderListData> data = new ArrayList<>();
    RecyclerView recyclerView ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_tab_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.history_recycler);
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://www.json-generator.com/api/json/get/")
                .baseUrl("https://vcanteen.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<orderHistory>> call =  jsonPlaceHolderApi.getHistory();

        call.enqueue(new Callback<List<orderHistory>>() {
            @Override
            public void onResponse(Call<List<orderHistory>> call, Response<List<orderHistory>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "CODE: "+response.code(),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                List<orderHistory> posts = response.body();
                showData(response.body());

                for (orderHistory post : posts) {
                    data.add(new orderListData(Integer.toString(post.getOrderId()),Integer.toString(post.getOrderPrice()),post.getOrderName(),post.getOrderNameExtra(), post.getRestaurantName(), post.getCreatedAt(), post.getOrderStatus()));

                }

            }

            @Override
            public void onFailure(Call<List<orderHistory>> call, Throwable t) {
                Toast.makeText(getActivity(), "ERROR: "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println("some error");
            }
        });

//        data = fill_with_data2();

        System.out.println("object added..");
        System.out.println("my array: " +data.toString());
//        recyclerView = (RecyclerView) view.findViewById(R.id.history_recycler);
//        Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, this.getActivity());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return view;
    }

    private void showData(List<orderHistory> body) {

        Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, this.getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }

    public List<orderListData> fill_with_data2() {

        List<orderListData> data = new ArrayList<>();
        data.add(new orderListData("2","20","Chicken Riceeee", "No Pork", "Chicken Stall", "03/02/2019", "TIMEOUT"));
        data.add(new orderListData("2","20","Chicken Riceeee", "No Pork", "Chicken Stall", "04/02/2019", "COLLECTED"));
        data.add(new orderListData("2","20","Chicken Riceeee", "No Pork", "Chicken Stall", "05/02/2019", "COLLECTED"));
        data.add(new orderListData("2","20","Chicken Riceeee", "No Pork", "Chicken Stall", "07/02/2019", "CANCELLED"));
        data.add(new orderListData("2","20","Chicken Riceeee", "No Pork", "Chicken Stall", "09/03/2019", "COLLECTED"));
        data.add(new orderListData("2","20","Chicken Riceeee", "No Pork", "Chicken Stall", "05/05/2019", "TIMEOUT"));
        data.add(new orderListData("2","20","Chicken Riceeee", "No Pork", "Chicken Stall", "04/07/2019", "COLLECTED"));

        return data;
    }
}
