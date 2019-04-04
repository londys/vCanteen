package com.example.vcanteen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class orderConfirmationActivity extends AppCompatActivity {

    orderStack orderStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        orderStack = getIntent().getExtras().getParcelable("orderStack");

        ListAdapter testAdapter3 = new confirmedOrderListAdapter(this,orderStack);
        final ListView confirmedList = findViewById(R.id.confirmedList);
        confirmedList.setAdapter(testAdapter3);

        String selectedServiceProvider = getIntent().getStringExtra("selectedServiceProvider");

        TextView service = (TextView)findViewById(R.id.showServiceProvider);
        service.setText("Paid through "+ selectedServiceProvider);

    }
}
