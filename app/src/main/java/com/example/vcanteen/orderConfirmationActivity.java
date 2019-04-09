package com.example.vcanteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

public class orderConfirmationActivity extends AppCompatActivity {

    orderStack orderStack;
    Button orderMoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        orderMoreButton = (Button) findViewById(R.id.orderMoreButton);
        orderMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderMoreBackToHome();
            }
        });

        orderStack = getIntent().getExtras().getParcelable("orderStack");

        ListAdapter testAdapter3 = new confirmedOrderListAdapter(this,orderStack);
        final ListView confirmedList = findViewById(R.id.confirmedList);
        confirmedList.setAdapter(testAdapter3);

        String selectedServiceProvider = getIntent().getStringExtra("selectedServiceProvider");

        TextView service = (TextView)findViewById(R.id.showServiceProvider);
        service.setText("Paid through "+ selectedServiceProvider);

        setListViewHeightBasedOnChildren(confirmedList);

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void orderMoreBackToHome(){
        Intent intent = new Intent(this,homev1Activity.class);
        startActivity(intent);
    }
}
