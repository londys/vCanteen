package com.example.vcanteen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vcanteen.Data.Customers;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class homev1Activity extends AppCompatActivity {

    private TextView mTextMessage;
    private SharedPreferences sharedPref;
    private FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    return true;
                case R.id.navigation_orders:
//                    mTextMessage.setText("ORDERS");
                    startActivity(new Intent(homev1Activity.this, OrderListActivity.class));
                    return true;
                case R.id.navigation_settings:
//                    mTextMessage.setText("SETTINGS");
                    startActivity(new Intent(homev1Activity.this, settingActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_v1);


        mAuth = FirebaseAuth.getInstance();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                            System.out.println(token);
                            saveToken(token);
                        } else {

                        }
                    }
                });

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        System.out.println(sharedPref.getString("token", "empty token"));
        System.out.println(sharedPref.getString("email", "empty email"));

        final String[] test = {"ESAN food","Fried Chicken with Sticky Rice","Food3","Food4","Fried Chicken with Sticky RiceFried Chicken with Sticky RiceFried Chicken with Sticky RiceFried Chicken with Sticky Rice","Food6", "Food 77"};
        ListAdapter testAdapter = new vendorListAdapter(this, test);
        ListView vendorList = findViewById(R.id.vendorlist);
        vendorList.setAdapter(testAdapter);

        //pin add

//        final int vendorId = 45; // for testing only
//
//        orderList = new ArrayList<>();
//        orderStack = new orderStack(22,vendorId, orderList,0,0,new Date());

        vendorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<test.length) {
                    Intent sent = new Intent(homev1Activity.this, vendorMenuActivity.class);
                    sent.putExtra("chosenVendor", test[position]);
//                    sent.putExtra("orderStack", orderStack); //TODO NOTSURE
//
                    startActivity(sent);

                }
//                else{
//                    vendorList.getChildAt(position).setEnabled(false);
//                }
            }
        });
    }

    private void saveToken(String token) {
        System.out.println("entered aavetoken");
        String email = mAuth.getCurrentUser().getEmail();
//        User user = new User(email, token);

        System.out.println("firebase: "+email);
        Customers customer = new Customers(email, null, null, "CUSTOMER", null, null, token);

        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("users");

        dbUsers.child(mAuth.getCurrentUser().getUid())
                .setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(homev1Activity.this, "Token Saved", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
