package com.example.vcanteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.lang.Integer;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class normalOrderActivity extends AppCompatActivity {

    int amount,fp,op;
    TextView orderQuantity, orderPrice, foodPrice, foodName, addFoodName, addFoodPrice;
    ImageView addToCartImg;


    String[] extra = {"ESAN food","Fried Chicken with Sticky Rice","Food3","Food4"};
    int[] extraPrice = {5,2,0,1};


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
        foodPrice = (TextView) findViewById(R.id.addFoodPrice);
        foodName = (TextView) findViewById(R.id.addFoodName);

        //Get Selected A La Carte Info
        Intent passALaCarte = getIntent();
        foodName.setText(passALaCarte.getStringExtra("aLaCarteName"));
        foodPrice.setText(""+passALaCarte.getStringExtra("aLaCartePrice")+"");
        orderPrice.setText(""+passALaCarte.getStringExtra("aLaCartePrice")+"");

        orderQuantity = (TextView) findViewById(R.id.orderQuantity);
        TextView subtractSign = findViewById(R.id.subtractSign);
        TextView addSign = findViewById(R.id.addSign);

        amount = Integer.parseInt(orderQuantity.getText().toString());
        fp = Integer.parseInt(foodPrice.getText().toString());

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


        ListAdapter testAdapter2 = new foodListAdapter(this,extra,extraPrice);
        final ListView extraList = findViewById(R.id.extraList);
        extraList.setAdapter(testAdapter2);

    }


    private void increaseQuntity() {
        amount = Integer.parseInt(orderQuantity.getText().toString());
        if(amount<9){
            amount = amount+1;
            //Toast.makeText(normalOrderActivity.this, "Now "+amount+"!", Toast.LENGTH_LONG).show();
            orderQuantity.setText(""+amount+"");
            op = amount * fp;
            orderPrice.setText("" + op +"");
        }
    }

    public void decreaseQuntity(){
        amount = Integer.parseInt(orderQuantity.getText().toString());
        if(amount>1){
            amount = amount-1;
            //Toast.makeText(normalOrderActivity.this, "Now "+amount+"!", Toast.LENGTH_LONG).show();
            orderQuantity.setText(""+amount+"");
            op = amount * fp;
            orderPrice.setText("" + op +"");
        }
    }

    public void openCart(){

       Intent intent = new Intent(this, cartActivity.class);
       startActivity(intent);
    }
}
