package com.example.vcanteen;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

class Done_View_Holder extends View_Holder {
//might not use

    CardView cv;
    TextView orderId;
    TextView orderPrice;
    TextView orderName;
    TextView orderNameExtra;
    TextView vendorName;
    TextView orderDate;
    TextView orderStatus;
    Button dismissBtn;

    public Done_View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        orderId = (TextView) itemView.findViewById(R.id.orderId);
        orderPrice = (TextView) itemView.findViewById(R.id.orderPrice);
        orderName = (TextView) itemView.findViewById(R.id.orderName);
        orderNameExtra = (TextView) itemView.findViewById(R.id.orderNameExtra);
        vendorName = (TextView) itemView.findViewById(R.id.vendorName);
        orderDate = (TextView) itemView.findViewById(R.id.orderDate);
        orderStatus = (TextView) itemView.findViewById(R.id.orderStatus);
        dismissBtn = (Button) itemView.findViewById(R.id.dismiss_btn);
    }
}
