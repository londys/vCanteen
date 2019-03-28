package com.example.vcanteen.Data;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class TokenResponse {

    @SerializedName("status")
    private JSONObject statusCode;
    @SerializedName("customerId")
    private String custID;
    @SerializedName("token")
    private String token;

}
