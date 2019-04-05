package com.example.vcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.internal.LockOnGetVariable;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class homev1Activity extends AppCompatActivity {

    private List<vendorList> vendorLists;
    private TextView mTextMessage;
    private ListView listView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                //remove the comments and edit class name


                case R.id.navigation_profile:
                    //Intent intent = new Intent(homev1Activity.this, profile class here.class);
                    //startActivity(intent);
                    break;
                case R.id.navigation_orders:
                    //Intent intent = new Intent(homev1Activity.this, orders class here.class);
                    //startActivity(intent);
                    break;
                case R.id.navigation_settings:
                    //Intent intent = new Intent(homev1Activity.this, setting class here.class);
                    //startActivity(intent);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_v1);

        mTextMessage = (TextView) findViewById(R.id.message);
        listView = findViewById(R.id.vendorlist);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       /* String[] test = {"ESAN food","Fried Chicken with Sticky Rice","Food3","Food4","Fried Chicken with Sticky RiceFried Chicken with Sticky RiceFried Chicken with Sticky RiceFried Chicken with Sticky Rice","Food6", "Food 77"};
        ListAdapter testAdapter = new vendorListAdapter(this, test);
        ListView vendorList = findViewById(R.id.vendorlist);
        vendorList.setAdapter(testAdapter);*/
        getVendorList();



    }

    private void getVendorList(){

        String url = "https://vcanteen.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        vendorListApi jsonPlaceHolderApi = retrofit.create(vendorListApi.class);

        Call<List<vendorList>> call = jsonPlaceHolderApi.getVendorList();

        call.enqueue(new Callback<List<vendorList>>() {
            @Override
            public void onResponse(Call<List<vendorList>> call, Response<List<vendorList>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"cannot connect error code: "+response.code(),Toast.LENGTH_LONG).show();
                    //System.out.println("\n\n\n\n********************"+ "Code: " + response.code() +"********************\n\n\n\n");
                    return;
                }
                //Log.w("gson response :",new Gson().toJson(response.body().getRestaurantName()));
               // vendorLists = (List<vendorList>) response.body();

                //listView.setAdapter(new vendorListAdapter(getApplicationContext(), vendorLists));

                List<vendorList> vendorLists = response.body();
                Log.d("TEST", String.valueOf(vendorLists.size()));
                ArrayList<vendorList> temp = new ArrayList<vendorList>();
                //for(vendorList v: vendorLists){
                    for(int i =0; i<vendorLists.size();i++){
                   // Log.d("vendor name: ",v.getRestaurantName());
                    /*int vendorId = v.getVendorId();
                    String vendorName = v.getRestaurantName();
                    int vendorNumber = v.getRestaurantNumber();
                    String vendorImageURL = v.getVendorImage();
                    String vendorStatus = v.getVendorStatus();*/

                    int vendorId = vendorLists.get(i).getVendorId();
                    String vendorName = vendorLists.get(i).getRestaurantName();
                    int vendorNumber = vendorLists.get(i).getRestaurantNumber();
                    String vendorImageURL = vendorLists.get(i).getVendorImage();
                    String vendorStatus = vendorLists.get(i).getVendorStatus();


                    vendorList newVendorList = new vendorList(vendorId,vendorName,vendorNumber,vendorImageURL,vendorStatus);
                    temp.add(newVendorList);
                   /* if(vendorStatus=="CLOSED") {
                        temp.add(vendorLists.size(),newVendorList);
                    }else{
                        temp.add(newVendorList);
                    }*/
                }
                vendorListAdapter adapter = new vendorListAdapter(homev1Activity.this,temp);

                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<vendorList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



        /*call.enqueue(new Callback<vendorList>() {
            @Override
            public void onResponse(Call<vendorList> call, Response<vendorList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"cannot connect error code: "+response.code(),Toast.LENGTH_LONG).show();
                    //System.out.println("\n\n\n\n********************"+ "Code: " + response.code() +"********************\n\n\n\n");
                    return;
                }
                Log.w("gson response :",new Gson().toJson(response.body().getRestaurantName()));
                    vendorLists = (List<vendorList>) response.body();

                listView.setAdapter(new vendorListAdapter(getApplicationContext(), vendorLists));

            }

            @Override
            public void onFailure(Call<vendorList> call, Throwable t) {

            }
        });*/
    }
    public boolean isEnabled(String status) {
        if (status == "CLOSED") {
            return false;
        } else {
            return true;
        }
    }

}
