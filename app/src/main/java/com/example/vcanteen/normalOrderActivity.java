package com.example.vcanteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.lang.Integer;
import java.util.ArrayList;

import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class normalOrderActivity extends AppCompatActivity {

    int amount,op;
    TextView orderQuantity, orderPrice, foodPrice, foodName, addFoodName, addFoodPrice;
    ImageView addToCartImg;


    String[] extra = {"ESAN food","Fried Chicken with Sticky Rice","Food3","Food4"};
    int[] extraPrice = {5,2,0,1};

    food chosenALaCarte;
    orderStack orderStack;
    order order;


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
        foodPrice = (TextView) findViewById(R.id.mainComPrice);
        foodName = (TextView) findViewById(R.id.mainComName);

        //Get Selected A La Carte Info
        Bundle bundle = getIntent().getExtras();
        //chosenALaCarte = bundle.getParcelable("chosenFood");
        chosenALaCarte = getIntent().getExtras().getParcelable("chosenFood");
        orderStack = getIntent().getExtras().getParcelable("orderStack");


        foodName.setText(chosenALaCarte.foodName);
        foodPrice.setText(""+chosenALaCarte.foodPrice);
        orderPrice.setText(""+chosenALaCarte.foodPrice);

        orderQuantity = (TextView) findViewById(R.id.orderQuantity);
        TextView subtractSign = findViewById(R.id.subtractSign);
        TextView addSign = findViewById(R.id.addSign);

        amount = Integer.parseInt(orderQuantity.getText().toString());

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

        ArrayList<food> shownFoodList = new ArrayList<>(availableExtraList);
        shownFoodList.addAll(soldOutExtraList);

        ListAdapter testAdapter2 = new foodListAdapter(this,shownFoodList,availableExtraList.size());
        final ListView extraListShow = findViewById(R.id.extraList);
        extraListShow.setAdapter(testAdapter2);

    }


    private void increaseQuntity() {
        amount = Integer.parseInt(orderQuantity.getText().toString());
        if(amount<9){
            amount = amount+1;
            //Toast.makeText(normalOrderActivity.this, "Now "+amount+"!", Toast.LENGTH_LONG).show();
            orderQuantity.setText(""+amount+"");
            op = amount * chosenALaCarte.foodPrice;
            orderPrice.setText("" + op +"");
        }
    }

    public void decreaseQuntity(){
        amount = Integer.parseInt(orderQuantity.getText().toString());
        if(amount>1){
            amount = amount-1;
            //Toast.makeText(normalOrderActivity.this, "Now "+amount+"!", Toast.LENGTH_LONG).show();
            orderQuantity.setText(""+amount+"");
            op = amount * chosenALaCarte.foodPrice;
            orderPrice.setText("" + op +"");
        }
    }

    public void openCart(){
        // get number of checked checkbox +1
        food foodList[] = new food[1];
        foodList[0] = chosenALaCarte;
//        for(int i = 0; i<foodList.length; i++){
//            foodList[i] = new food();
//        }

        // assume that there is no extra since checkbox doesn't work yet
        order = new order(chosenALaCarte.foodName,null, op, foodList);
        Intent intent = new Intent(this, cartActivity.class);
        startActivity(intent);
    }
}
