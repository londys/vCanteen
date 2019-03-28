package com.example.vcanteen;

import com.example.vcanteen.Data.Customers;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("v1/user-authentication/customer/check/token")
    Call<JSONObject> createCustomer(@Body Customers customers);

}
