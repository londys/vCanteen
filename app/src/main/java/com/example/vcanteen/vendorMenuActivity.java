package com.example.vcanteen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.widget.Toast;

import com.example.vcanteen.POJO.orderProgress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class vendorMenuActivity extends AppCompatActivity {

    orderStack orderStack;
    order order;
    TextView minCombinationPrice;
    ArrayList<order> orderList;
    String restaurantNameString; //just add for minor fix in order confirmation
    int restaurantNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_menu);
        System.out.println("Entered Menu.....");

        orderStack = com.example.vcanteen.orderStack.getInstance();

        //orderStack = getIntent().getExtras().getParcelable("orderStack"); // delete if don't want from home activity
        restaurantNameString = getIntent().getStringExtra("chosenVendor"); // delete if don't want from home activity  //just add for minor fix in order confirmation
        restaurantNumber = getIntent().getIntExtra("vendor id",0);

        orderStack.setVendorId(restaurantNumber);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://vcanteen.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("Entered Menu.....");
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<vendorAlacarteMenu> call = jsonPlaceHolderApi.getVendorAlacarte(restaurantNumber);
        System.out.println("Entered Menu2.....");
        call.enqueue(new Callback<vendorAlacarteMenu>() {
            @Override
            public void onResponse(Call<vendorAlacarteMenu> call, Response<vendorAlacarteMenu> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(vendorMenuActivity.this, "CODE: "+response.code(),Toast.LENGTH_LONG).show();
                    return;

                }
                //get result here
                vendorAlacarteMenu menu = response.body();
                System.out.println("Received Restaurant Name: "+menu.getVendorInfo().restaurantName);

                minCombinationPrice = findViewById(R.id.minCombinationPrice);
                minCombinationPrice.setText("Starting from "+ menu.getMinCombinationPrice() +" ฿");
                addAlacarteToList(menu.availableList, menu.soldOutList);
            }

            @Override
            public void onFailure(Call<vendorAlacarteMenu> call, Throwable t) {
                System.out.println("Entered Menu Fail.....");

            }
        });
        System.out.println("Entered Menu3.....");




///TRY SINGLETON////
//        orderStack.setCustomerId(sharedPref.getInt("customerId",0));

        orderStack.setVendorId(restaurantNumber);
        orderStack.setOrderList(new ArrayList<order>());
        orderStack.setTotalPrice(0);
        orderStack.setCreatedAt(new Date());

        //try date
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Create at "+dateformat.format(orderStack.getCreatedAt()));


        TextView restaurantName = (TextView)findViewById(R.id.restaurantName);// delete if don't want from home activity
        restaurantName.setText(""+restaurantNameString);// delete if don't want from home activity   //just add for minor fix in order confirmation

        // to open cutomize order activity
        android.support.constraint.ConstraintLayout tappable_customize = (android.support.constraint.ConstraintLayout)findViewById(R.id.tappable_customize);
        tappable_customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomizeActivity();
            }
        });
        // wait to connect with BE
//        boolean hasCombination = false; < just an assumption for testing
//        if(hasCombination==false){
//            tappable_customize.setVisibility(View.GONE);
//        }

    }

    private void addAlacarteToList(ArrayList<availableList> inputAvaliableList, ArrayList<soldOutList> inputSoldOutList) {

////////  DEAL WITH A LA CARTE ////////////

        final ArrayList<food> availableList = new ArrayList<>(); //need to get from BE
        ArrayList<food> soldOutList = new ArrayList<>();   //need to get from BE

        for(availableList list : inputAvaliableList) {
            availableList.add(new food(list.getFoodId(), list.getFoodName(), list.getFoodPrice(), "A LA CARTE"));
        }
        for(soldOutList list : inputSoldOutList) {
            soldOutList.add(new food(list.getFoodId(), list.getFoodName(), list.getFoodPrice(), "A LA CARTE"));
        }

        final ArrayList<food> shownFoodList = new ArrayList<>(availableList);
        shownFoodList.addAll(soldOutList);

        //TextView restaurantName = (TextView)findViewById(R.id.restaurantName);
        //restaurantName.setText(""+);


        ListAdapter testAdapter1 = new menuListAdapter(this,shownFoodList,availableList.size());
        final ListView menuList = findViewById(R.id.menuList);
        menuList.setAdapter(testAdapter1);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<availableList.size()) {
                    Intent passALaCarte = new Intent(vendorMenuActivity.this, normalOrderActivity.class);
                    passALaCarte.putExtra("chosenFood", shownFoodList.get(position));
                    //passALaCarte.putExtra("orderStack", orderStack);
                    passALaCarte.putExtra("sendRestaurantName", restaurantNameString); //just add for minor fix in order confirmation
                    startActivity(passALaCarte);
                }else{
                    menuList.getChildAt(position).setEnabled(false);
                }
            }
        });

    }


    private void openCustomizeActivity() {
        Intent intent = new Intent(this, customizeOrderActivity.class);
        intent.putExtra("sendRestaurantName", restaurantNameString); //just add for minor fix in order confirmation
        startActivity(intent);
    }


    public void openCartActivity(View view) {
        if(orderStack.totalPrice != 0){
            Intent intent = new Intent(this, cartActivity.class);
            intent.putExtra("sendRestaurantName", restaurantNameString); //just add for minor fix in order confirmation
            startActivity(intent);
        }else{
            Toast.makeText(vendorMenuActivity.this, "No Order in the Cart!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        orderStack o = intent.getExtras().getParcelable("orderStackFromCart");
    }
}
