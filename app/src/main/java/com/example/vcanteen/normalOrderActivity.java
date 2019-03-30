package com.example.vcanteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.lang.Integer;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class normalOrderActivity extends AppCompatActivity {

    int amount;
    TextView orderQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_order);

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

        String[] test = {"ESAN food","Fried Chicken with Sticky Rice","Food3","Food4"};
        ListAdapter testAdapter2 = new foodListAdapter(this,test);
        ListView extraList = findViewById(R.id.extraList);
        extraList.setAdapter(testAdapter2);
    }

    private void increaseQuntity() {
        amount = Integer.parseInt(orderQuantity.getText().toString());
        if(amount<9){
            amount = amount+1;
            //Toast.makeText(normalOrderActivity.this, "Now "+amount+"!", Toast.LENGTH_LONG).show();
            orderQuantity.setText(""+amount+"");
        }
    }

    public void decreaseQuntity(){
        amount = Integer.parseInt(orderQuantity.getText().toString());
        if(amount>1){
            amount = amount-1;
            //Toast.makeText(normalOrderActivity.this, "Now "+amount+"!", Toast.LENGTH_LONG).show();
            orderQuantity.setText(""+amount+"");
        }
    }
}
