package com.example.vcanteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class vendorMenuActivity extends AppCompatActivity {

    String[] test = {"ESAN food","Fried Chicken with Sticky Rice","Food3","Food4","Fried Chicken with Sticky Rice","Food6", "Food 77","Food 567","Food 7321"};
    int[] testPrice = {12,30,11,60,30,23,56,55,45};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_menu);

        // to open cutomize order activity
        android.support.constraint.ConstraintLayout tappable_customize = (android.support.constraint.ConstraintLayout)findViewById(R.id.tappable_customize);
        tappable_customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomizeActivity();
            }
        });

        ListAdapter testAdapter1 = new menuListAdapter(this,test,testPrice);
        ListView menuList = findViewById(R.id.menuList);
        menuList.setAdapter(testAdapter1);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent passALaCarte = new Intent(vendorMenuActivity.this, normalOrderActivity.class);
                passALaCarte.putExtra("aLaCarteName", test[position]);
                passALaCarte.putExtra("aLaCartePrice", ""+testPrice[position]+"");
                startActivity(passALaCarte);
            }
        });

    }

    private void openCustomizeActivity() {
        Intent intent = new Intent(this, customizeOrderActivity.class);
        startActivity(intent);

    }


    public void openCartActivity(View view) {
        Intent intent = new Intent(this, cartActivity.class);
        startActivity(intent);
    }


}
