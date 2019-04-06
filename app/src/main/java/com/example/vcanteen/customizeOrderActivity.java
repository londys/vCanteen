package com.example.vcanteen;

import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.os.Bundle;

public class customizeOrderActivity extends AppCompatActivity {

    String[] items1 = { "Base1", "Base2" };
    String[] items2 = { "Main1", "Main2", "Main3", "Main4", "Main5" ,"Main1", "Main2", "Main3", "Main4", "Main5"};
    String[] items3 = { "Extra1", "Extra2", "Extra3" ,"Extra4","Extra5"};
    int[] items3Price = {10,5,3,6,2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_order);
        ListView list1 = (ListView) findViewById(R.id.list1);
        ListView list2 = (ListView) findViewById(R.id.list2);
        //ListView list3 = (ListView) findViewById(R.id.list3);




        ListAdapter testAdapter2 = new foodListAdapter(this,items3,items3Price);
        final ListView extraList = findViewById(R.id.list3);
        extraList.setAdapter(testAdapter2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items1);
        list1.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items2);
        list2.setAdapter(adapter2);
//        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, items3);
//        list3.setAdapter(adapter3);

        setListViewHeightBasedOnChildren(list1);
        setListViewHeightBasedOnChildren(list2);
        //setListViewHeightBasedOnChildren(list3);

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


}
