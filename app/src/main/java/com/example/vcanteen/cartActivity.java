package com.example.vcanteen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class cartActivity extends AppCompatActivity {

    RadioButton scbEasy, kplus, trueMoney,cunex;

    TextView orderTotalPrice, orderTotalItems;

    String[] order = {"order 1", "order 2", "order 3"};
    String[] orderInfo = {"","Extra Rice, More food, More food, More food, More food, Alibaba","put more love <3"};
    int [] orderPrice = {30,45,55};

    int total=0;

    //dealing with popup
    Dialog showpopup;
    ImageView confirmImgButton;
    TextView cancelButton;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        scbEasy = (RadioButton) findViewById(R.id.scbEasy);
        kplus = (RadioButton) findViewById(R.id.kplus);
        trueMoney = (RadioButton) findViewById(R.id.trueMoney);
        cunex = (RadioButton)findViewById(R.id.cunex);

        ListAdapter testAdapter2 = new orderListAdapter(this,order,orderPrice,orderInfo);
        final ListView orderList = findViewById(R.id.orderList);
        orderList.setAdapter(testAdapter2);

        orderTotalItems = (TextView) findViewById(R.id.orderTotalItems);
        orderTotalPrice = (TextView) findViewById(R.id.orderTotalPrice);

        for(int i = 0; i<orderPrice.length; i++){
            total += orderPrice[i];
        }

        orderTotalItems.setText("Total "+ orderPrice.length+" item(s)");
        orderTotalPrice.setText("" + total +"");

        //dealing with popup
        showpopup = new Dialog(this);
        confirmImgButton = (ImageView)findViewById(R.id.confirmImgButton);
        confirmImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

    }

    public void showPopUp(){
        showpopup.setContentView(R.layout.cart_reset_popup);
        confirmButton = (Button)showpopup.findViewById(R.id.confirmButton);
        cancelButton = (TextView)showpopup.findViewById(R.id.cancelButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProcessingPayment();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopup.dismiss();
            }
        });

        showpopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showpopup.show();
    }

    public void openProcessingPayment() {
        Intent intent = new Intent(this, processingPaymentActivity.class);
        startActivity(intent);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.scbEasy:
                if (checked)
                    scbEasy.setTextColor(Color.parseColor("#FF5A5A"));
                    unClickRadioButton(kplus,cunex,trueMoney);
                    break;
            case R.id.kplus:
                if (checked)
                    kplus.setTextColor(Color.parseColor("#FF5A5A"));
                    unClickRadioButton(cunex,scbEasy,trueMoney);
                    break;
            case R.id.cunex:
                if (checked)
                    cunex.setTextColor(Color.parseColor("#FF5A5A"));
                    unClickRadioButton(kplus,scbEasy,trueMoney);
                    break;
            case R.id.trueMoney:
                if (checked)
                    trueMoney.setTextColor(Color.parseColor("#FF5A5A"));
                    unClickRadioButton(kplus,scbEasy,cunex);
                    break;
        }
    }

    public void unClickRadioButton(RadioButton a, RadioButton b, RadioButton c){
        a.setChecked(false);
        b.setChecked(false);
        c.setChecked(false);
        a.setTextColor(Color.parseColor("#828282"));
        b.setTextColor(Color.parseColor("#828282"));
        c.setTextColor(Color.parseColor("#828282"));
    }

}
