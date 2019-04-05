package com.example.vcanteen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vcanteen.Data.Customers;
import com.example.vcanteen.Data.RecoverPass;
import com.example.vcanteen.Data.TokenResponse;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class emailActivity extends AppCompatActivity {

    private ImageButton next_button;
    private static final String EMAIL = "email";
    private LoginButton loginButton;
    private CallbackManager callbackManager = CallbackManager.Factory.create();
    private SharedPreferences sharedPref;
    private ProgressDialog progressDialog;
    private String email;
    private TextView error1;
    private TextView error2;
    private EditText emailbox2;

    private final String url = "https://vcanteen.herokuapp.com/";
    private boolean exit = false;

    private FirebaseAuth mAuth;
    private DatabaseReference dbUsers;
    private String firebaseToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_enter_page);

        FirebaseApp.initializeApp(emailActivity.this);
        mAuth = FirebaseAuth.getInstance();

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
//        LoginManager.getInstance().logInWithReadPermissions(emailActivity.this, Arrays.asList("public_profile", "email"));

        next_button = (ImageButton) findViewById(R.id.next_button /*xml next_button */);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        emailbox2 = (EditText) findViewById(R.id.editEmail);
        final Context context = this;

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpassword_login_page();
            }
        });
        final Button cleartxtbtn = (Button) findViewById(R.id.clear_text_btn);
        final EditText emailbox = (EditText) findViewById(R.id.editEmail);
        error1 = (TextView) findViewById(R.id.error1);
        error2 = (TextView) findViewById(R.id.error2);

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        System.out.println(sharedPref.getString("token", "empty token"));
        System.out.println(sharedPref.getString("email", "empty email"));

        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        cleartxtbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                emailbox.getText().clear();
            }
        });



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                progressDialog = new ProgressDialog(context);
                progressDialog = ProgressDialog.show(context, "",
                        "Loading. Please wait...", true);
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                final Intent intent = new Intent(emailActivity.this, homev1Activity.class);
                                email = object.optString("email");
                                String first_name = object.optString("first_name");
                                String last_name = object.optString("last_name");

                                String profile_url = null;
                                try {
                                    profile_url = (String) object.getJSONObject("picture").getJSONObject("data").get("url");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                final String account_type = "FACEBOOK";

                                Customers postCustomer = new Customers(email, first_name, last_name, account_type, profile_url, "alsfkjsadf");
                                System.out.println(postCustomer.toString());
                                postCustomer = new Customers(email, first_name, last_name, account_type, profile_url, null);
                                Call<TokenResponse> call = jsonPlaceHolderApi.createCustomer(postCustomer);




                                // HTTP POST
                                call.enqueue(new Callback<TokenResponse>() {
                                    @Override
                                    public void onResponse(Call<TokenResponse> call, final Response<TokenResponse> response) {
                                        if(!response.isSuccessful())
                                            Toast.makeText(getApplicationContext(), "Error Occured, please try again.", Toast.LENGTH_SHORT);
//                                        TokenResponse tokenResponse = response.body();
//                                        System.out.println(tokenResponse.statusCode);
//                                        System.out.println(response.body().toString());
                                        if(response.code() != 200)
                                            Toast.makeText(getApplicationContext(), "Either email or password is incorrect.", Toast.LENGTH_SHORT).show();
                                        else {
                                            mAuth.signInWithEmailAndPassword(email, "firebaseOnlyNaja")
                                                    .addOnCompleteListener(emailActivity.this, new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                System.out.println("SUCCESS");
                                                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                                dbUsers = FirebaseDatabase.getInstance().getReference("users").child(uid);

                                                                dbUsers.addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        for(DataSnapshot dsUser: dataSnapshot.getChildren())
                                                                            firebaseToken = dsUser.getValue(String.class);
                                                                        System.out.println(firebaseToken);
                                                                        sharedPref.edit().putString("token", response.body().getToken()).commit();
                                                                        sharedPref.edit().putString("email", email).commit();
                                                                        sharedPref.edit().putString("account_type", account_type).commit();
                                                                        sharedPref.edit().putString("firebaseToken", firebaseToken).commit();
                                                                        progressDialog.dismiss();
                                                                        startActivity(intent);
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });
                                                            } else {
                                                                System.out.println("Firebase login FAIL");
                                                                progressDialog.dismiss();
                                                            }
                                                        }
                                                    });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                                        System.out.println("ERROR ESUS");
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "An error occured. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });





                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name, last_name, email,link, picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                exception.printStackTrace();

            }
        });

    }

    public void openpassword_login_page() {
        if (TextUtils.isEmpty(emailbox2.getText().toString())) {
            error2.setVisibility(View.INVISIBLE);
            error1.setVisibility(View.VISIBLE);
            return;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailbox2.getText().toString()).matches()) {
            error1.setVisibility(View.INVISIBLE);
            error2.setVisibility(View.VISIBLE);
            return;
        }
        progressDialog = new ProgressDialog(emailActivity.this);
        progressDialog = ProgressDialog.show(emailActivity.this, "",
                "Loading. Please wait...", true);
        final Intent pwloginintent = new Intent(this, password_login_page.class);
        //check for email validation here
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        email = emailbox2.getText().toString();
        Call<Void> call = jsonPlaceHolderApi.verifyEmail(new RecoverPass(email));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());
                System.out.println(new RecoverPass(email).toString());
                if(response.code()!= 200) {
                    error1.setVisibility(View.INVISIBLE);
                    error2.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                } else {
                    emailbox2.setInputType(InputType.TYPE_CLASS_TEXT);
                    String customerEmail = null;
                    customerEmail = emailbox2.getText().toString();

                    pwloginintent.putExtra("cachedemail", customerEmail);
                    progressDialog.dismiss();
                    startActivity(pwloginintent);

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }


}

