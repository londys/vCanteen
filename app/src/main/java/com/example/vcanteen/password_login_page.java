package com.example.vcanteen;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class password_login_page extends AppCompatActivity {

    private String email;
    private String passwd;
    private String account_type = "NORMAL";
    private EditText passwdField;
    private Button showBtn;
    private ImageButton next;
    private boolean isHidden = true;

    private Button pwrecoverbtn;
    private Dialog confirmDialog;
    private Dialog recoverWarningDialog;
    private Button ok1btn;
    private Button ok2btn;
    private Button cancelbtn;

    // vcanteen.herokuapp.com/v1/user-authentication/customer/check/token
    private final String dbAddress = "https://en33remma22tb.x.pipedream.net/";
    private boolean exit = false;

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

        passwdField = findViewById(R.id.passwordBox);
        showBtn = findViewById(R.id.show_pw_btn);
        next = findViewById(R.id.next_button);

        System.out.println(passwdField.getText().toString());

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    passwdField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHidden = false;
                } else {
                    passwdField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHidden = true;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(password_login_page.this, homev1Activity.class);
                passwd = org.apache.commons.codec.digest.DigestUtils.sha256Hex(passwdField.getText().toString());
                System.out.println(passwd);
                JSONObject postData = new JSONObject();
                try {
                    postData.put("account_type", account_type);
                    postData.put("email", email);
                    postData.put("passwd", passwd == null ? JSONObject.NULL : (Object)passwd);
                    System.out.println(postData.toString());
                    (new SendDeviceDetails()).execute(dbAddress, postData.toString());
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT);
                }
            }
        });

        pwrecoverbtn = (Button) findViewById(R.id.password_recov_btn);
        pwrecoverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecoverPopup();
            }
        });
    }

    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }

    private byte[] hash256(String text) {
        byte[] hash = null;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(text.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return hash;
    }

    private void openRecoverPopup() {
        recoverWarningDialog = new Dialog(password_login_page.this);
        recoverWarningDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        recoverWarningDialog.setContentView(R.layout.recoverwarningdialog);

        ok1btn = (Button)recoverWarningDialog.findViewById(R.id.ok1_btn);
        cancelbtn = (Button)recoverWarningDialog.findViewById(R.id.cancel_btn);

        ok1btn.setEnabled(true);
        cancelbtn.setEnabled(true);

        ok1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for send request to back-end here
                Toast.makeText(getApplicationContext(),"Email sent to "+email,Toast.LENGTH_SHORT).show();
                recoverWarningDialog.cancel();
                openConfirmDialog();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverWarningDialog.cancel();
            }
        });

        recoverWarningDialog.show();
    }
    private void openConfirmDialog(){
        confirmDialog = new Dialog(password_login_page.this);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.setContentView(R.layout.confirmdialog);

        ok2btn = (Button)confirmDialog.findViewById(R.id.ok2_btn);

        ok2btn.setEnabled(true);

        ok2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.cancel();
            }
        });
        confirmDialog.show();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(password_login_page.this, emailActivity.class));
    }

    /*@Override
    public void finish() {
         super.finish();
         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/
}
