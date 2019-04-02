package com.example.vcanteen;

import java.util.ArrayList;

public class orderStack {

    int customerId;
    int vendorId;
    ArrayList<order> orderList;
    int totalPrice;
    int customerMoneyAccount;
    //DATETIME createdAt; //null until the customer taps CONFIRM & PAY on popup dialog
//
//    public void setEmpty(){
//        this.customerId = null;
//        this.vendorId = null;
//        this.orderList = new ArrayList<order>();
//        this.totalPrice = null;
//        this.customerMoneyAccount = null;
//        this.createdAt = null;
//    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public ArrayList<order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<order> orderList) {
        this.orderList = orderList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCustomerMoneyAccount() {
        return customerMoneyAccount;
    }

    public void setCustomerMoneyAccount(int customerMoneyAccount) {
        this.customerMoneyAccount = customerMoneyAccount;
    }
}
