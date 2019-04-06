package com.example.vcanteen;

import com.example.vcanteen.POJO.orderHistory;
import com.example.vcanteen.POJO.orderProgress;
import com.example.vcanteen.POJO.orderStatus;
import com.example.vcanteen.POJO.pickupSlot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    //----------
    @GET("v1/orders/customers/{customerId}/history")
    Call<List<orderHistory>> getHistory(@Path("customerId") int customerId);

    @GET("v1/orders/customers/{customerId}/in-progress")
    Call<List<orderProgress>> getProgress(@Path("customerId") int customerId);

    @GET("/v1/orders/{orderId}/slot")
    Call<pickupSlot> getPickupSlot(@Path("orderId") int orderId);


    @PUT("v1/orders/{orderId}/status/collected")
    Call<orderStatus> putOrderStatus(@Path("orderId") int orderId);
    //----------
}