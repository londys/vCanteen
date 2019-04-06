package com.example.vcanteen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("v1/orders/1/menu")
    Call<vendorAlacarteMenu> getVendorMenu();

}
