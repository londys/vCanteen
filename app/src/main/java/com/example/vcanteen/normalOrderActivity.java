package com.example.vcanteen;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.function.Consumer;

import android.widget.Toast;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class normalOrderActivity extends AppCompatActivity {

    int eop,op;
    TextView orderQuantity, orderPrice, foodPrice, foodName, addFoodName, addFoodPrice;
    ImageView addToCartImg;
    foodListAdapter adapter ;
    ArrayList<Boolean> foodchk = new ArrayList<Boolean>();

    food chosenALaCarte;
    orderStack orderStack;
    order order;
    int oPrice;
    int mainAmount;

    food[] foodList;
    food[] sendfoodList;
    SparseBooleanArray mCheckStates;

    ArrayList<food> shownFoodList;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_order);

        addToCartImg = (ImageView)findViewById(R.id.addToCartImg);
        addToCartImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openCart();
            }
        });

        orderPrice = (TextView) findViewById(R.id.orderPrice);
        foodPrice = (TextView) findViewById(R.id.baseComPrice);
        foodName = (TextView) findViewById(R.id.baseComName);

        //Get Selected A La Carte Info
        chosenALaCarte = getIntent().getExtras().getParcelable("chosenFood");
        orderStack = getIntent().getExtras().getParcelable("orderStack");

        foodName.setText(chosenALaCarte.foodName);
        foodPrice.setText(""+chosenALaCarte.foodPrice);
        orderPrice.setText(""+chosenALaCarte.foodPrice);
        oPrice = chosenALaCarte.foodPrice;
        mainAmount = 1;
        eop = 0;
        op = chosenALaCarte.foodPrice;

        orderQuantity = (TextView) findViewById(R.id.orderQuantity);
        TextView subtractSign = findViewById(R.id.subtractSign);
        TextView addSign = findViewById(R.id.addSign);


        subtractSign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                decreaseQuntity();
            }
        });
        addSign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                increaseQuntity();
            }
        });


        ArrayList<food> availableExtraList = new ArrayList<>(); //need to get from BE
        ArrayList<food> soldOutExtraList = new ArrayList<>();   //need to get from BE

        // for testing
        availableExtraList.add(new food(10,"Extra 1",10, "EXTRA"));
        availableExtraList.add(new food(7,"Extra 2",5, "EXTRA"));
        soldOutExtraList.add(new food(5,"Sold Out Extra 1",5, "EXTRA"));
        soldOutExtraList.add(new food(23,"Sold Out Extra 2",5, "EXTRA"));

        shownFoodList = new ArrayList<>(availableExtraList);
        shownFoodList.addAll(soldOutExtraList);

        adapter = new foodListAdapter(this,shownFoodList,availableExtraList.size());
        final ListView extraListShow = findViewById(R.id.extraList);
        extraListShow.setAdapter(adapter);



        //int numCheckBox = ... //how many checkbox are checked
//        foodList = new food[2]; //[numCheckBox+1];
//        foodList[0] = chosenALaCarte;
//        foodList[1] = shownFoodList.get(1);

        //adapter.mCheckStates.o

    }

    public void notifyExtraChange(){
        int tempex = 0;
        for (int i = 0; i < shownFoodList.size(); i++) {
            if (adapter.isChecked(i) == true) {
                tempex += shownFoodList.get(i).getFoodPrice();
            }
        }
        eop = tempex;
        orderPrice.setText("" + (eop + op)*mainAmount );
    }


    private void increaseQuntity() {
        if(mainAmount<9){
            mainAmount = mainAmount+1;
            //Toast.makeText(normalOrderActivity.this, "Now "+amount+"!", Toast.LENGTH_LONG).show();
            orderQuantity.setText(""+mainAmount+"");
            orderPrice.setText("" + (eop + op)*mainAmount);
        }
    }

    public void decreaseQuntity(){
        if(mainAmount>1){
            mainAmount = mainAmount-1;
            //Toast.makeText(normalOrderActivity.this, "Now "+amount+"!", Toast.LENGTH_LONG).show();
            orderQuantity.setText(""+mainAmount+"");
            orderPrice.setText("" + (eop + op)*mainAmount);
        }
    }

    public void openCart(){

        order = new order(chosenALaCarte.foodName,"", (op + eop) ,foodList);

        for(int i = 0; i<mainAmount; i++){
            orderStack.orderList.add(order);
        }


        int n = 0;
        for(int i=0;i<shownFoodList.size();i++)
        {
            if(adapter.isChecked(i)==true)
            {
                order.orderNameExtra = order.orderNameExtra + "\n" + adapter.foodList.get(i).getFoodName();
            }
        }


        Log.d("1",orderStack.orderList.get(0).orderName);
        Intent intent = new Intent(this, cartActivity.class);

        intent.putExtra("sendOrderStack", orderStack);

        startActivity(intent);
    }
}
