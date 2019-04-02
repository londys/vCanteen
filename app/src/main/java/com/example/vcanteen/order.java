package com.example.vcanteen;

public class order {

    String orderName;
    //For combination, combination_base comes before combination_main
    //for the same food_type, sort alphabetically
    //ask for the code of forming orderName from BE
    String orderNameExtra;
    int orderPrice;
    food foodList[];

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderNameExtra() {
        return orderNameExtra;
    }

    public void setOrderNameExtra(String orderNameExtra) {
        this.orderNameExtra = orderNameExtra;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public food[] getFoodList() {
        return foodList;
    }

    public void setFoodList(food[] foodList) {
        this.foodList = foodList;
    }
}
