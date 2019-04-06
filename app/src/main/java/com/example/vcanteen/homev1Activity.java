package com.example.vcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class homev1Activity extends AppCompatActivity {

    private TextView mTextMessage;
    orderStack orderStack; //pin add
    ArrayList<order> orderList; //pin add

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    mTextMessage.setText("PROFILE");
                    return true;
                case R.id.navigation_orders:
                    mTextMessage.setText("ORDERS");
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText("SETTINGS");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_v1);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String[] test = {"ESAN food","Fried Chicken with Sticky Rice","Food3","Food4","Fried Chicken with Sticky RiceFried Chicken with Sticky RiceFried Chicken with Sticky RiceFried Chicken with Sticky Rice","Food6", "Food 77"};
        ListAdapter testAdapter = new vendorListAdapter(this, test);
        ListView vendorList = findViewById(R.id.vendorlist);
        vendorList.setAdapter(testAdapter);

        //pin add

//        final int vendorId = 45; // for testing only
//
//        orderList = new ArrayList<>();
//        orderStack = new orderStack(22,vendorId, orderList,0,0,new Date());

        vendorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<test.length) {
                    Intent sent = new Intent(homev1Activity.this, vendorMenuActivity.class);
                    sent.putExtra("chosenVendor", test[position]);
                    sent.putExtra("orderStack", orderStack);

                    startActivity(sent);
                }
//                else{
//                    vendorList.getChildAt(position).setEnabled(false);
//                }
            }
        });
    }
    
}
