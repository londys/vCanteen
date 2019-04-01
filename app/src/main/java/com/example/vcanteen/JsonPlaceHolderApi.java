package com.example.vcanteen;

import com.example.vcanteen.Data.Customers;
import com.example.vcanteen.Data.RecoverPass;
import com.example.vcanteen.Data.Token;
import com.example.vcanteen.Data.TokenResponse;
import com.example.vcanteen.Data.TokenVerification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("v1/user-authentication/customer/check/token")
    Call<TokenResponse> createCustomer(@Body Customers customers);

    @POST("v1/user-authentication/customer/verify/token")
    Call<TokenVerification> verifyToken(@Body Token token);

    @PUT("v1/user-authentication/customer/password/recover")
    Call<Void> recoverPass(@Body RecoverPass email);

}
