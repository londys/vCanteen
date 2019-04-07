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

import java.util.ArrayList;
import java.util.Date;

public class cartActivity extends AppCompatActivity {

    RadioButton scbEasy, kplus, trueMoney,cunex;

    TextView orderTotalPrice, orderTotalItems, orderTotalPriceTop;

    orderListAdapter orderAdapter;


    int total=0;

    //dealing with popup
    Dialog showpopup;
    ImageView confirmImgButton;
    TextView cancelButton;
    Button confirmButton;

    orderStack orderStack;
    ArrayList<RadioButton> unavailableService;
    String selectedServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ArrayList<paymentList> paymentList = new ArrayList<>(); // need to get from BE

        // test
        paymentList.add(new paymentList(1,"SCB_EASY"));
        paymentList.add(new paymentList(03,"TRUEMONEY_WALLET"));

        scbEasy = (RadioButton) findViewById(R.id.scbEasy);
        kplus = (RadioButton) findViewById(R.id.kplus);
        trueMoney = (RadioButton) findViewById(R.id.trueMoney);
        cunex = (RadioButton)findViewById(R.id.cunex);

//// FOR DISABLE UNAVAILABLE SERVICE PROVIDER ////
        unavailableService = new ArrayList<>();
        unavailableService.add(scbEasy);
        unavailableService.add(kplus);
        unavailableService.add(cunex);
        unavailableService.add(trueMoney);

        String a,b;
        for(int i = 0; i<paymentList.size();i++){
            a = String.valueOf(paymentList.get(i).serviceProvider.charAt(0));
            for(int j = 0; j < unavailableService.size();j++){
                b = String.valueOf(unavailableService.get(j).getText().toString().charAt(0));
                if(a.equalsIgnoreCase(b)){
                    unavailableService.remove(j);
                }
            }
        }

        for(int k = 0; k<unavailableService.size();k++){
            unavailableService.get(k).setEnabled(false);
            unavailableService.get(k).setTextColor(Color.parseColor("#E0E0E0"));
        }

//// FOR DEALING WITH POP UP ////
        showpopup = new Dialog(this);
        confirmImgButton = (ImageView)findViewById(R.id.confirmImgButton);
        confirmImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

//// FOR DEALING WITH ORDER LIST ////
        //orderStack = getIntent().getExtras().getParcelable("sendOrderStack");
        orderStack = com.example.vcanteen.orderStack.getInstance();

        orderAdapter = new orderListAdapter(this,orderStack);
        final ListView orderList = findViewById(R.id.orderList);
        orderList.setAdapter(orderAdapter);



        orderTotalItems = (TextView) findViewById(R.id.orderTotalItems);
        orderTotalPrice = (TextView) findViewById(R.id.orderTotalPrice);
        orderTotalPriceTop = (TextView) findViewById(R.id.orderTotalPrice1);

        for(int i = 0; i<orderStack.orderList.size(); i++){
            total += orderStack.orderList.get(i).orderPrice;
        }

        orderStack.setTotalPrice(total);
        orderTotalItems.setText("Total "+ orderStack.orderList.size()+" item(s)");
        orderTotalPrice.setText("" + orderStack.totalPrice +"");
        orderTotalPriceTop.setText("" + orderStack.totalPrice +"");

//        orderList.setOnItemClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });


    }

    public void showPopUp(){
        showpopup.setContentView(R.layout.cart_reset_popup);
        confirmButton = (Button)showpopup.findViewById(R.id.confirmButtonPassword);
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
        // fill customer money account in orderstack
        // fill timestamp
        orderStack.setCreatedAt(new Date());

        Intent intent = new Intent(this, processingPaymentActivity.class);
        intent.putExtra("orderStack", orderStack);
        intent.putExtra("selectedServiceProvider", selectedServiceProvider);
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
                    selectedServiceProvider = scbEasy.getText().toString();
                    break;
            case R.id.kplus:
                if (checked)
                    kplus.setTextColor(Color.parseColor("#FF5A5A"));
                    unClickRadioButton(cunex,scbEasy,trueMoney);
                    selectedServiceProvider = kplus.getText().toString();
                    break;
            case R.id.cunex:
                if (checked)
                    cunex.setTextColor(Color.parseColor("#FF5A5A"));
                    unClickRadioButton(kplus,scbEasy,trueMoney);
                    selectedServiceProvider = cunex.getText().toString();
                    break;
            case R.id.trueMoney:
                if (checked)
                    trueMoney.setTextColor(Color.parseColor("#FF5A5A"));
                    unClickRadioButton(kplus,scbEasy,cunex);
                    selectedServiceProvider = trueMoney.getText().toString();
                    break;
        }
    }

    public void unClickRadioButton(RadioButton a, RadioButton b, RadioButton c){
        a.setChecked(false);
        b.setChecked(false);
        c.setChecked(false);
        if(!unavailableService.contains(a)){
            a.setTextColor(Color.parseColor("#828282"));
        }
        if(!unavailableService.contains(b)){
            b.setTextColor(Color.parseColor("#828282"));
        }
        if(!unavailableService.contains(c)){
            c.setTextColor(Color.parseColor("#828282"));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent goToMenu = new Intent(cartActivity.this,vendorMenuActivity.class);
        goToMenu.putExtra("orderStackFromCart",orderStack);
        //goToMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(goToMenu);
        finish();
    }


    public void updateOrder() {

        orderStack.orderList.remove(orderAdapter.passPosition());
        total = 0;
        for(int i = 0; i<orderStack.orderList.size(); i++){
            total += orderStack.orderList.get(i).orderPrice;
        }

        orderStack.setTotalPrice(total);
        orderTotalItems.setText("Total "+ orderStack.orderList.size()+" item(s)");
        orderTotalPrice.setText("" + total +"");
        orderTotalPriceTop.setText("" + orderStack.totalPrice +"");

    }
}
