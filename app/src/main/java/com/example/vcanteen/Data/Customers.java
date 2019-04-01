package com.example.vcanteen.Data;

import android.support.annotation.Nullable;

import org.json.JSONObject;

public class Customers {

    private String email;
//    private String first_name;
//    private String last_name;
    private String account_type;
//    private String profile_pic;
    @Nullable
    private String password;

    public Customers(String email, String account_type, String password) {
        this.email = email;
//        this.first_name = first_name;
//        this.last_name = last_name;
        this.account_type = account_type;
//        this.profile_pic = profile_pic;
        this.password = password;
    }

    public String getAccount_type() {
        return account_type;
    }

    public String getEmail() {
        return email;
    }

//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }
//
    @Nullable
    public String getPassword() {
        return password;
    }
//
//    public String getProfile_pic() {
//        return profile_pic;
//    }
}
