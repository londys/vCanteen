package com.example.vcanteen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class emailActivity extends AppCompatActivity {
    private ImageButton next_button;
    private static final String EMAIL = "email";
    private LoginButton loginButton;
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    private String email;
    private String first_name;
    private String last_name;
    private String account_type;
    private String profile_pic;
    private String password;

    private final String dbAddress = "https://en33remma22tb.x.pipedream.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_enter_page);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        LoginManager.getInstance().logInWithReadPermissions(emailActivity.this, Arrays.asList("public_profile", "email"));

        next_button = (ImageButton) findViewById(R.id.next_button /*xml next_button */);
        loginButton = (LoginButton) findViewById(R.id.login_button);

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

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Intent intent = new Intent(emailActivity.this, homev1Activity.class);
                                email = object.optString("email");
                                first_name = object.optString("first_name");
                                last_name = object.optString("last_name");

                                String profilePicUrl = null;
                                try {
                                    profilePicUrl = (String) object.getJSONObject("picture").getJSONObject("data").get("url");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Bitmap profilePic = null;
                                try {
                                    profilePic = new myTask().execute(profilePicUrl).get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                profile_pic = encodeTobase64(profilePic);
//                                System.out.println(profile_pic.length());
//                                System.out.println(object.toString());

                                account_type = "FACEBOOK";
                                password = null;

                                JSONObject postData = new JSONObject();
                                try {
                                    postData.put("account_type", account_type);
                                    postData.put("firstname", first_name);
                                    postData.put("lastname", last_name);
                                    postData.put("customer_image", profile_pic == null ? JSONObject.NULL : profile_pic);
                                    postData.put("email", email);
                                    postData.put("passwd", password == null ? JSONObject.NULL : password);
                                    System.out.println(postData.toString());
                                    (new SendDeviceDetails()).execute(dbAddress, postData.toString());
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT);
                                }


                                startActivity(intent);
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
        Intent pwloginintent = new Intent(this, password_login_page.class);
        //check for email validation here
        TextView error1 = (TextView) findViewById(R.id.error1);
        TextView error2 = (TextView) findViewById(R.id.error2);
        EditText emailbox2 = (EditText) findViewById(R.id.editEmail);
        emailbox2.setInputType(InputType.TYPE_CLASS_TEXT);
        String customerEmail = null;
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

    private class myTask extends AsyncTask<String, Void, Bitmap> {


        protected Bitmap doInBackground(String... src) {
            try {
                URL url = new URL(src[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
                //do stuff
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //do stuff

        }
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

}

