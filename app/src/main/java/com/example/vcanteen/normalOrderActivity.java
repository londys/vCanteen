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
    //ArrayList<String> selected = new ArrayList<>();
    CheckBox cb;

    String[] extra = {"ESAN food","Fried Chicken with Sticky Rice","Food3","Food4"};
    int[] extraPrice = {5,2,0,1};
    ArrayList <String> checkedValue;
    //SparseBooleanArray sparseBooleanArray;


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

        extraList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cb = (CheckBox)view.findViewById(R.id.checkBox2);
                addFoodName = (TextView)view.findViewById(R.id.addFoodName);
                cb.performClick();
                if (cb.isChecked()) {
                    checkedValue.add(addFoodName.getText().toString());
                } else if (!cb.isChecked()) {
                    checkedValue.remove(addFoodName.getText().toString());
                }

//                String selectedItem = ((TextView)view).getText().toString();
//                if(selected.contains(selectedItem)){
//                    selected.remove(selectedItem); //remove deselected item from the list of selected items
//                } else {
//                    selected.add(selectedItem); //add selected item to the list of selected items
//                }
            }
        });

        /////

//        extraList.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // TODO Auto-generated method stub
//
//                sparseBooleanArray = extraList.getCheckedItemPositions();
//
//
//                String ValueHolder = "" ;
//
//                int i = 0 ;
//
//                while (i < sparseBooleanArray.size()) {
//
//                    if (sparseBooleanArray.valueAt(i)) {
//
//                        ValueHolder += ListViewItems [ sparseBooleanArray.keyAt(i) ] + ",";
//                    }
//
//                    i++ ;
//                }
//
//                ValueHolder = ValueHolder.replaceAll("(,)*$", "");
//
//                Toast.makeText(MainActivity.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();
//
//            }
//        });

        ////
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

//        String checked = "";
//        for(int i = 0; i < checkedValue.size(); i++){
//            checked += ""+checkedValue.get(i);
////        }
//        StringBuilder sb = new StringBuilder();
//        for (String s : checkedValue)
//        {
//            sb.append(s);
//            sb.append("\t");
//        }

  //      System.out.println(sb.toString());
//        String selItems="";
//        for(String item:selected){
//            if(selItems=="")
//                selItems=item;
//            else
//                selItems+="/"+item;
//        }
        
        //Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
       Intent intent = new Intent(this, cartActivity.class);
       startActivity(intent);
    }
}
