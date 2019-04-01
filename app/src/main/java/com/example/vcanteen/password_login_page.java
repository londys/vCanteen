package com.example.vcanteen;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.vcanteen.Data.Customers;
import com.example.vcanteen.Data.TokenResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private SharedPreferences sharedPref;

    // vcanteen.herokuapp.com/
    private final String url = "http://vcanteen.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login_page);

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        System.out.println(sharedPref.getString("token", "empty token"));
        System.out.println(sharedPref.getString("email", "empty email"));

        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


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
                final Intent intent = new Intent(password_login_page.this, homev1Activity.class);
                passwd = passwdField.getText().toString();
//                passwd = org.apache.commons.codec.digest.DigestUtils.sha256Hex(passwdField.getText().toString());
                System.out.println(passwd);
                JSONObject postData = new JSONObject();
                try {
                    postData.put("account_type", account_type);
                    postData.put("email", email);
                    postData.put("password", passwd == null ? JSONObject.NULL : (Object) passwd);
                    System.out.println(postData.toString());

                    Customers postCustomer = new Customers(email, null, null, account_type, null, passwd);
                    Call<TokenResponse> call = jsonPlaceHolderApi.createCustomer(postCustomer);

                    // HTTP POST
                    call.enqueue(new Callback<TokenResponse>() {
                        @Override
                        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                            if (!response.isSuccessful())
                                Toast.makeText(getApplicationContext(), "Error Occured, please try again.", Toast.LENGTH_SHORT);
//                            TokenResponse tokenResponse = response.body();
//                            System.out.println(tokenResponse.isStatusCode());
                            System.out.println(response.code());

                            if (response.code() == 404) {
                                Toast.makeText(getApplicationContext(), "Either email or password is incorrect.", Toast.LENGTH_SHORT).show();
                                System.out.println("ERROR EDOK");
                            } else if (response.body().getStatus().equals("success")) {
                                sharedPref.edit().putString("token", response.body().getToken()).commit();
                                sharedPref.edit().putString("email", email).commit();

                                startActivity(intent);
                            }


                        }

                        @Override
                        public void onFailure(Call<TokenResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

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

        ok1btn = (Button) recoverWarningDialog.findViewById(R.id.ok1_btn);
        cancelbtn = (Button) recoverWarningDialog.findViewById(R.id.cancel_btn);

        ok1btn.setEnabled(true);
        cancelbtn.setEnabled(true);

        ok1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for send request to back-end here
                Toast.makeText(getApplicationContext(), "Email sent to " + email, Toast.LENGTH_SHORT).show();
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

    private void openConfirmDialog() {
        confirmDialog = new Dialog(password_login_page.this);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.setContentView(R.layout.confirmdialog);

        ok2btn = (Button) confirmDialog.findViewById(R.id.ok2_btn);

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
