package com.example.vcanteen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
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

    @GET("/v1/orders/{orderId}/slot")
    Call<pickupSlot> getPickupSlot(@Path("orderId") int orderId);

//    @Headers({"Accept: application/json"})
    @PUT("v1/orders/{orderId}/status/collected")
    Call<orderStatus> putOrderStatus(@Path("orderId") int orderId);


    //----------
//    @Path("orderId") int orderId,

//    @PUT("user/classes")
//    Call<playlist> addToPlaylist(
//            @Header("Content-Type") String contentType,
//            @Body PlaylistParm parm);
}