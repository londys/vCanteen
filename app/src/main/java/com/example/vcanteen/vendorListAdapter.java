package com.example.vcanteen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class vendorListAdapter extends ArrayAdapter<vendorList> {
    RequestOptions option = new RequestOptions().centerCrop();
    String vendorStatus;

    Context context;
    int resource;
    List<vendorList> vendorLists = null;

    public vendorListAdapter(Context context, List<vendorList> vendorLists) {

        super(context, R.layout.vendor_listview, vendorLists);
        this.context = context;
        this.vendorLists = vendorLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        vendorList item = vendorLists.get(position);
        vendorStatus = item.getVendorStatus();
            if(vendorStatus.equals("CLOSED")){
                convertView = LayoutInflater.from(context).inflate(R.layout.vendor_listview_disabled, parent, false);
            } else
            convertView = LayoutInflater.from(context).inflate(R.layout.vendor_listview, parent, false);


        TextView vendorName = (TextView) convertView.findViewById(R.id.vendorName);
        ImageView vendorImage = convertView.findViewById(R.id.vendorImage);

        vendorName.setText(item.getRestaurantName());
        //vendorImage.setImageBitmap(loadBitmap(item.getVendorImage()));
        Glide.with(context).load(item.getVendorImage()).apply(option).into(vendorImage);
        vendorImage.setClipToOutline(true);


        return convertView;
    }

}


