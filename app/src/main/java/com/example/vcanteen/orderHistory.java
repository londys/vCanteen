package com.example.vcanteen;

import com.google.gson.annotations.SerializedName;

public class orderHistory {
    @SerializedName("order_id")
    private int orderId;

    @SerializedName("order_name")
    private String orderName;

    @SerializedName("order_name_extra")
    private String orderNameExtra;

    @SerializedName("food_image")
    private String foodImage;

    @SerializedName("order_price")
    private int orderPrice;

    @SerializedName("restaurant_name")
    private String restaurantName;

    @SerializedName("restaurant_number")
    private int restaurantNumber; //number or id

    @SerializedName("order_status")
    private String orderStatus;

    @SerializedName("created_at")
    private String createdAt;


//    private orderListData data;
//
//    public orderListData getData() {
//        return data;
//    }


    public int getOrderId() {
        return orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getOrderNameExtra() {
        return orderNameExtra;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getRestaurantNumber() {
        return restaurantNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
