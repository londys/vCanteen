package com.example.vcanteen;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView orderId;
    TextView orderPrice;
//    ImageView orderImage;
    TextView orderName;
    TextView orderNameExtra;
    TextView vendorName;
    TextView orderDate;
    TextView orderStatus;

    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        orderId = (TextView) itemView.findViewById(R.id.orderId);
        orderPrice = (TextView) itemView.findViewById(R.id.orderPrice);
//        orderImage = (ImageView) itemView.findViewById(R.id.orderImage);
        orderName = (TextView) itemView.findViewById(R.id.orderName);
        orderNameExtra = (TextView) itemView.findViewById(R.id.orderNameExtra);
        vendorName = (TextView) itemView.findViewById(R.id.vendorName);
        orderDate = (TextView) itemView.findViewById(R.id.orderDate);
        orderStatus = (TextView) itemView.findViewById(R.id.orderStatus);
    }
}