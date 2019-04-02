package com.example.vcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.Toast;


public class vendorMenuActivity extends AppCompatActivity {

    orderStack orderStack;
    String[] test = {"ESAN food","Fried Chicken with Sticky Rice","Food3","Food4","Fried Chicken with Sticky Rice","Food6", "Food 77","Food 567","Food 7321"};
    int[] testPrice = {12,30,11,60,30,23,56,55,45};
    TextView minCombinationPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_menu);


        orderStack = new orderStack();
        //orderStack.setVendorId(vendorId); //< wait to get from BE

        // to open cutomize order activity
        android.support.constraint.ConstraintLayout tappable_customize = (android.support.constraint.ConstraintLayout)findViewById(R.id.tappable_customize);
        tappable_customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomizeActivity();
            }
        });
        // wait to connect with BE
//        boolean hasCombination = false; < just an assumption for testing
//        if(hasCombination==false){
//            tappable_customize.setVisibility(View.GONE);
//        }
        minCombinationPrice = (TextView) findViewById(R.id.minCombinationPrice);
        //minCombinationPrice.setText("Starting from "+ value get from BE +" Baht");

////////  DEAL WITH A LA CARTE ////////////

        final ArrayList<food> availableList = new ArrayList<>(); //need to get from BE
        ArrayList<food> soldOutList = new ArrayList<>();   //need to get from BE

        //test
        availableList.add(new food(15,"Meal",40, "A LA CARTE"));
        availableList.add(new food(16,"Food 2",25, "A LA CARTE"));
        soldOutList.add(new food(9,"Sold Out Food",30, "A LA CARTE"));

        ArrayList<food> shownFoodList = new ArrayList<>(availableList);
        shownFoodList.addAll(soldOutList);

        TextView restaurantName = (TextView)findViewById(R.id.restaurantName);
        restaurantName.setText(""+shownFoodList.get(2).foodName);


        ListAdapter testAdapter1 = new menuListAdapter(this,shownFoodList,availableList.size());
        final ListView menuList = findViewById(R.id.menuList);
        menuList.setAdapter(testAdapter1);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<availableList.size()) {
                    Intent passALaCarte = new Intent(vendorMenuActivity.this, normalOrderActivity.class);
                    passALaCarte.putExtra("aLaCarteName", test[position]);
                    passALaCarte.putExtra("aLaCartePrice", "" + testPrice[position] + "");
                    startActivity(passALaCarte);
                }else{
                    menuList.getChildAt(position).setEnabled(false);
                }
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
