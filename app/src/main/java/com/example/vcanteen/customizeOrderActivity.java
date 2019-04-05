package com.example.vcanteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.Button;

public class customizeOrderActivity extends AppCompatActivity {

    String[] items1 = { "Base1", "Base2" };
    String[] items2 = { "Main1", "Main2", "Main3", "Main4", "Main5" ,"Main1", "Main2", "Main3", "Main4", "Main5"};
    String[] items3 = { "Extra1", "Extra2", "Extra3" ,"Extra4","Extra5"};
    int[] items3Price = {10,5,3,6,2};

    order order;
    ArrayList<food> mainList;
    ListAdapter testAdapter2;

    ImageView addToCartImgFromCustomize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_order);

        addToCartImgFromCustomize = (ImageView) findViewById(R.id.addToCartImgFromCustomize);
        addToCartImgFromCustomize.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openCart();
            }
        });

///// FOR COMBINATION BASE //////
        ArrayList<food> baseList = new ArrayList<>(); //need to get from BE

        // for testing
        baseList.add(new food(3, "Jasmine Rice", 5, "COMBINATION_BASE"));
        baseList.add(new food(1, "Sticky Rice", 10, "COMBINATION_BASE"));
        baseList.add(new food(2, "Fried Rice", 15, "COMBINATION_BASE"));

        ListAdapter testAdapter1 = new combinationBaseListAdapter(this, baseList);
        final ListView baseListShow = findViewById(R.id.list1);
        baseListShow.setAdapter(testAdapter1);

        setListViewHeightBasedOnChildren(baseListShow);

///// FOR COMBINATION MAIN //////
        final ArrayList<food> availableMainList = new ArrayList<>(); //need to get from BE
        ArrayList<food> soldOutMainList = new ArrayList<>();   //need to get from BE

        // for testing
        availableMainList.add(new food(3, "Pork", 15, "COMBINATION_MAIN"));
        availableMainList.add(new food(1, "Beef", 10, "COMBINATION_MAIN"));
        availableMainList.add(new food(2, "Fried Chicken", 15, "COMBINATION_MAIN"));
        availableMainList.add(new food(3, "Curry", 15, "COMBINATION_MAIN"));
        soldOutMainList.add(new food(32, "Salad", 25, "COMBINATION_MAIN"));
        soldOutMainList.add(new food(13, "Curry Beef", 35, "COMBINATION_MAIN"));

        mainList = new ArrayList<>(availableMainList);
        mainList.addAll(soldOutMainList);

        testAdapter2 = new foodListAdapter(this, mainList, availableMainList.size());
        final ListView mainListShow = findViewById(R.id.list2);
        mainListShow.setAdapter(testAdapter2);

        setListViewHeightBasedOnChildren(mainListShow);

///// FOR EXTRA //////


    }
//        ListView list1 = (ListView) findViewById(R.id.list1);
//        ListView list2 = (ListView) findViewById(R.id.list2);
        //ListView list3 = (ListView) findViewById(R.id.list3);


//        ListAdapter testAdapter2 = new foodListAdapter(this,items3,items3Price);
//        final ListView extraList = findViewById(R.id.list3);
//        extraList.setAdapter(testAdapter2);
//
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, items1);
//        list1.setAdapter(adapter1);
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, items2);
//        list2.setAdapter(adapter2);
////        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
////                android.R.layout.simple_list_item_1, items3);
////        list3.setAdapter(adapter3);
//
//        setListViewHeightBasedOnChildren(list1);
//        setListViewHeightBasedOnChildren(list2);
//        //setListViewHeightBasedOnChildren(list3);
//
//    }
//
    public void openCart(){


        //order = new order("Base, Main","", 100 ,foodList);


//        int n = 0;
//        for(int i=0;i<mainList.size();i++)
//        {
//            if(testAdapter2.isChecked(i)==true)
//            {
//                order.orderNameExtra = order.orderNameExtra + "\n" + adapter.foodList.get(i).getFoodName();
//            }
//        }
//
//
//        Log.d("1",orderStack.orderList.get(0).orderName);
//        Intent intent = new Intent(this, cartActivity.class);
//
//        intent.putExtra("sendOrderStack", orderStack);

       // startActivity(intent);
    }
//
//    public void notifyExtraChange(){
//        int tempex = 0;
//        for (int i = 0; i < mainList.size(); i++) {
//            if (testAdapter2.isChecked(i) == true) {
//                tempex += shownFoodList.get(i).getFoodPrice();
//            }
//        }
//        eop = tempex;
//        orderPrice.setText("" + (eop + op)*mainAmount );
//    }

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


}

