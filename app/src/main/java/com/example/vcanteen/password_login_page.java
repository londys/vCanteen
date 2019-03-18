package com.example.vcanteen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class password_login_page extends AppCompatActivity {

    private String email;
    private EditText passwdField;
    private Button showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login_page);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                email = null;
            } else {
                email = extras.getString("cachedemail");
            }
        } else {
            email = (String) savedInstanceState.getSerializable("cachedemail");
        }

        System.out.println(email);

        passwdField = findViewById(R.id.passwordBox);
        showBtn = findViewById(R.id.show_pw_btn);

        System.out.println(passwdField.getText().toString());

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(passwdField.getInputType());
                System.out.println(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                System.out.println(InputType.TYPE_NULL);
                if (passwdField.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    passwdField.setInputType(InputType.TYPE_NULL);
                    System.out.println("set to null");
                } else {
                    passwdField.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    System.out.println("set to passwd");
                }
            }
        });
    }

    /*@Override
    public void finish() {
         super.finish();
         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/
}
