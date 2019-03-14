package com.example.vcanteen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class emailActivity extends AppCompatActivity {
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
        Intent pwloginintent = new Intent(this, passwordLoginActivity.class);
        //check for email validation here
        TextView error1 = (TextView) findViewById(R.id.error1);
        TextView error2 = (TextView) findViewById(R.id.error2);
        EditText emailbox2 = (EditText) findViewById(R.id.editEmail);
        emailbox2.setInputType(InputType.TYPE_CLASS_TEXT);
        String customerEmail = null;
        {
            if (TextUtils.isEmpty(emailbox2.getText().toString())) {
                error2.setVisibility(View.INVISIBLE);
                error1.setVisibility(View.VISIBLE);
                return;
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailbox2.getText().toString()).matches()) {
                error1.setVisibility(View.INVISIBLE);
                error2.setVisibility(View.VISIBLE);
                return;
            } else
                customerEmail = emailbox2.getText().toString();
                pwloginintent.putExtra("cachedemail", customerEmail);
                startActivity(pwloginintent);
        }
    }
}

