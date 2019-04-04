package com.example.vcanteen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class paymentSplash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    orderStack orderStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_splash);

        orderStack = getIntent().getExtras().getParcelable("orderStack");

        String selectedServiceProvider = getIntent().getStringExtra("selectedServiceProvider");
        ImageView img= (ImageView) findViewById(R.id.serviceProviderImg);

        if(selectedServiceProvider.equalsIgnoreCase("TrueMoney Wallet")){
            img.setImageResource(R.drawable.truemoney);
        }else if(selectedServiceProvider.equalsIgnoreCase("CUNEX")){
            img.setImageResource(R.drawable.cunex);
        }else if(selectedServiceProvider.equalsIgnoreCase("KBANK")) {
            img.setImageResource(R.drawable.kbank);
        }else if(selectedServiceProvider.equalsIgnoreCase("SCB EASY")) {
            img.setImageResource(R.drawable.scb);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(paymentSplash.this,orderConfirmationActivity.class);
                intent.putExtra("orderStack", orderStack);
                intent.putExtra("selectedServiceProvider",selectedServiceProvider);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
