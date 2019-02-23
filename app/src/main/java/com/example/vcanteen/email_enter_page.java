package com.example.vcanteen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class email_enter_page extends AppCompatActivity {
    public ImageButton next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_enter_page);
        next_button = (ImageButton) findViewById(R.id.next_button /*xml next_button */);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpassword_login_page();
            }
        });
        final Button cleartxtbtn = (Button) findViewById(R.id.clear_text_btn);
        final EditText emailbox = (EditText) findViewById(R.id.editEmail);

        cleartxtbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                emailbox.getText().clear();
            }
        });


    }
    public void openpassword_login_page() {
        Intent pwloginintent = new Intent(this, password_login_page.class);
        //check for email validation here
        startActivity(pwloginintent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
