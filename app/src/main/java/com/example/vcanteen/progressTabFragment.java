package com.example.vcanteen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class progressTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "ProgressTabFragment";
    List<orderListData> data = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerView;
    CardView cv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_tab_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.progress_recycler);
        cv = (CardView) view.findViewById(R.id.cardView);
//        cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                loadRecyclerViewData();
            }
        });
//        loadRecyclerViewData();

         System.out.println("object added..");

         View view2 = inflater.inflate(R.layout.popup_confirm_pickup,container,false);
         CardView cv = (CardView) view.findViewById(R.id.cardView);
        Button btn = (Button) view2.findViewById(R.id.confirmPickup_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                System.out.println("detected onclick..");
            }
        });

//        while(!pass){
//        }
        System.out.println("my array: " +data.toString());


//        Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, this.getActivity());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return view;
    }



    @Override
    public void onRefresh() {
        // Fetching data from server
        loadRecyclerViewData();
    }



    public void loadRecyclerViewData() {

        data = new ArrayList<>();
        System.out.println("Loading Progress Data");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://vcanteen.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<orderProgress>> call =  jsonPlaceHolderApi.getProgress(1);

        call.enqueue(new Callback<List<orderProgress>>() {
            @Override
            public void onResponse(Call<List<orderProgress>> call, Response<List<orderProgress>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "CODE: "+response.code(),
                            Toast.LENGTH_LONG).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }

                List<orderProgress> posts = response.body();
                System.out.println(posts.toString());

                for (orderProgress post : posts) {
                    if(String.valueOf(post.getOrderStatus()).equals("DONE")) {
                        data.add(new orderListData(Integer.toString(post.getOrderId()),Integer.toString(post.getOrderPrice()),post.getOrderName(),post.getOrderNameExtra(), post.getRestaurantName(), post.getCreatedAt(), post.getOrderStatus(),0));
                    }
                    data.add(new orderListData(Integer.toString(post.getOrderId()),Integer.toString(post.getOrderPrice()),post.getOrderName(),post.getOrderNameExtra(), post.getRestaurantName(), post.getCreatedAt(), post.getOrderStatus(),1));
                }

                Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<orderProgress>> call, Throwable t) {
                Toast.makeText(getActivity(), "ERROR: "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println("some error");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

}
