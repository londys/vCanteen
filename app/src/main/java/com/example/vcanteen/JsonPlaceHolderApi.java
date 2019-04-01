package com.example.vcanteen;

import com.example.vcanteen.Data.Customers;
import com.example.vcanteen.Data.Token;
import com.example.vcanteen.Data.TokenResponse;
import com.example.vcanteen.Data.TokenVerification;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("v1/user-authentication/customer/check/token")
    Call<TokenResponse> createCustomer(@Body Customers customers);

    @POST("/v1/user-authentication/customer/verify/token")
    Call<TokenVerification> verifyToken(@Body Token token);

}
