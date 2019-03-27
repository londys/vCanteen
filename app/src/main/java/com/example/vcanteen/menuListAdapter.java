package com.example.vcanteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class menuListAdapter extends ArrayAdapter {

    menuListAdapter(Context context, String[] a){

        super(context, R.layout.menu_listview , a);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater menuListInflater = LayoutInflater.from(getContext());
        View customView = menuListInflater.inflate(R.layout.menu_listview, parent, false);

        String singleItem = (String) getItem(position);
        TextView menuName = (TextView) customView.findViewById(R.id.menuName);

        menuName.setText(singleItem);
        return customView;
    }
}
