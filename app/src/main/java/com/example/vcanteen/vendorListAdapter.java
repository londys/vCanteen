package com.example.vcanteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class vendorListAdapter extends ArrayAdapter {

    vendorListAdapter(Context context, String[] a){

        super(context, R.layout.vendor_listview , a);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater vendorListInflater = LayoutInflater.from(getContext());
        View customView = vendorListInflater.inflate(R.layout.vendor_listview, parent, false);

        String singleItem = (String) getItem(position);
        TextView vendorname = (TextView) customView.findViewById(R.id.vendorName);

        vendorname.setText(singleItem);
        return customView;
    }
}

