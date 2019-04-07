package com.example.vcanteen;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.vcanteen.Data.Customers;
import com.example.vcanteen.Data.RecoverPass;
import com.example.vcanteen.Data.TokenResponse;
import com.example.vcanteen.Data.UserFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
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
    private TextView errorMessage;
    private boolean isHidden = true;

    private Button pwrecoverbtn;
    private Dialog confirmDialog;
    private Dialog recoverWarningDialog;
    private Button ok1btn;
    private Button ok2btn;
    private Button cancelbtn;

    private SharedPreferences sharedPref;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference dbUsers;

    // vcanteen.herokuapp.com/
    private final String url = "http://vcanteen.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login_page);

        FirebaseApp.initializeApp(password_login_page.this);
        mAuth = FirebaseAuth.getInstance();

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
        errorMessage = findViewById(R.id.error1);

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


        final Context context = this;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(context);
                progressDialog = ProgressDialog.show(context, "",
                        "Loading. Please wait...", true);
                final Intent intent = new Intent(password_login_page.this, homev1Activity.class);
                passwd = passwdField.getText().toString();
                if (passwd.equals("")) {
                    errorMessage.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    return;
                }

                passwd = new String(Hex.encodeHex(DigestUtils.sha256(passwd)));
                System.out.println(passwd);

                Customers postCustomer = new Customers(email, null, null, account_type, null, passwd);
                final Call<TokenResponse> call = jsonPlaceHolderApi.createCustomer(postCustomer);

                call.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, final Response<TokenResponse> response) {
                        if (!response.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Error Occured, please try again.", Toast.LENGTH_SHORT);
                            errorMessage.setText("THE PASSWORD IS INCORRECT");
                            errorMessage.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                        }
//                            TokenResponse tokenResponse = response.body();
//                            System.out.println(tokenResponse.isStatusCode());
//                            System.out.println(response.body().toString());

                        if (response.code() != 200) {
                            errorMessage.setText("THE PASSWORD IS INCORRECT");
                            errorMessage.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                        } else {
                            sharedPref.edit().putString("token", response.body().getToken()).commit();
                            sharedPref.edit().putString("email", email).commit();
                            sharedPref.edit().putString("account_type", account_type).commit();
                            progressDialog.dismiss();
                            startActivity(intent);

                        }


                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
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


    private void openRecoverPopup() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        recoverWarningDialog = new Dialog(password_login_page.this);
        recoverWarningDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        recoverWarningDialog.setContentView(R.layout.recoverwarningdialog);

        ok1btn = (Button) recoverWarningDialog.findViewById(R.id.ok1_btn);
        cancelbtn = (Button) recoverWarningDialog.findViewById(R.id.cancel_btn);

        ok1btn.setEnabled(true);
        cancelbtn.setEnabled(true);

        final Context context = this;
        ok1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(context);
                progressDialog = ProgressDialog.show(context, "",
                        "Loading. Please wait...", true);
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

                //code for send request to back-end here
                Call<Void> call = jsonPlaceHolderApi.recoverPass(new RecoverPass(email));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() != 200)
                            Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                        else {
                            confirmDialog.show();
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                recoverWarningDialog.cancel();
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
