package com.example.vcanteen;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.view.View;

public class cartActivity extends AppCompatActivity {

    RadioButton scbEasy, kplus, trueMoney,cunex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        scbEasy = (RadioButton) findViewById(R.id.scbEasy);
        kplus = (RadioButton) findViewById(R.id.kplus);
        trueMoney = (RadioButton) findViewById(R.id.trueMoney);
        cunex = (RadioButton)findViewById(R.id.cunex);



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
