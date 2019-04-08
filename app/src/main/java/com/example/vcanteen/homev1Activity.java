package com.example.vcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

    FloatingActionButton profilebtn;
    FloatingActionButton ordersbtn;
    FloatingActionButton settingsbtn;
    orderStack orderStack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_v1);

        mTextMessage = (TextView) findViewById(R.id.message);
        listView = findViewById(R.id.vendorlist);
        profilebtn = findViewById(R.id.buttonprofile);
        ordersbtn = findViewById(R.id.buttonorders);
        settingsbtn = findViewById(R.id.buttonsettings);

        //orderStack.setEmpty();
        //orderStack = new orderStack(orderStack.getCustomerId(),orderStack.getVendorId(),orderStack.getOrderList(),orderStack.getTotalPrice(),orderStack.getCustomerMoneyAccount());


        /*profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homev1Activity.this, profile .class);
                startActivity(intent);
            }
        });*/

       /*ordersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homev1Activity.this, order in progress.class);
                startActivity(intent);
            }
        });*/

        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homev1Activity.this, settingActivity.class);
                startActivity(intent);
            }
        });

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
                    return;
                }

                List<vendorList> vendorLists = response.body();
                Log.d("TEST", String.valueOf(vendorLists.size()));
                ArrayList<vendorList> temp = new ArrayList<vendorList>();
                for(int i =0; i<vendorLists.size();i++){

                    int vendorId = vendorLists.get(i).getVendorId();
                    String vendorName = vendorLists.get(i).getRestaurantName();
                    int vendorNumber = vendorLists.get(i).getRestaurantNumber();
                    String vendorImageURL = vendorLists.get(i).getVendorImage();
                    String vendorStatus = vendorLists.get(i).getVendorStatus();

                    vendorList newVendorList = new vendorList(vendorId,vendorName,vendorNumber,vendorImageURL,vendorStatus);
                    temp.add(newVendorList);
                }
                vendorListAdapter adapter = new vendorListAdapter(homev1Activity.this,temp);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int vendornumber = vendorLists.get(position).getVendorId();
                        Intent i = new Intent(homev1Activity.this, vendorMenuActivity.class);
                        i.putExtra("vendor id", vendornumber);
                        startActivity(i);
                        /*On the second activity:
                        Bundle bundle = getIntent().getExtras();
                         int value = bundle.getInt("vendor id");
                         */
                    }
                });


            }

            @Override
            public void onFailure(Call<List<vendorList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
