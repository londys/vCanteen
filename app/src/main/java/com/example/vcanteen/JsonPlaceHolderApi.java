package com.example.vcanteen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
//    @GET("/v1/orders/customers/1")
//    Call<JSONResponse> getJSON();

//    @GET("bQubsDhuoO?indent=2")
//    Call<List<orderProgress>> getProgress();
//
//    @GET("cfuBkMygwO?indent=2")
//    Call<List<orderHistory>> getHistory();


    //----------
    @GET("v1/orders/customers/1/history")
    Call<List<orderHistory>> getHistory();

    @GET("v1/orders/customers/1/in-progress")
    Call<List<orderProgress>> getProgress();

    @GET("/v1/orders/{orderID}/slot")
    Call<pickupSlot> getPickupSlot(@Path("orderID") int orderID);
    //----------

//    http://www.json-generator.com/api/json/get/bQubsDhuoO?indent=2


}