package com.example.vcanteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class setting extends AppCompatActivity {
    LinearLayout tappable_password, tappable_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        tappable_password = (LinearLayout)findViewById(R.id.tappable_password);
        // tappable_payment = (LinearLayout)findViewById(R.id.tappable_payment);

        tappable_password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openChangePassword();
            }
        });

//        tappable_payment.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                openMain();
//            }
//        });
    }

    public void openChangePassword(){
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }
}
