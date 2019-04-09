package com.example.vcanteen;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class orderStack implements Parcelable {

    int customerId;
    int vendorId;
    ArrayList<order> orderList;
    int totalPrice;
    int customerMoneyAccount;
    //Date createdAt; //null until the customer taps CONFIRM & PAY on popup dialog

    public void setEmpty(){
        this.customerId = 0;
        this.vendorId = 0;
        this.orderList = new ArrayList<order>();
        this.totalPrice = 0;
        this.customerMoneyAccount = 0;
       // this.createdAt = null;
    }


    public orderStack(int customerId, int vendorId, ArrayList<order> orderList, int totalPrice, int customerMoneyAccount) {
        this.customerId = customerId;
        this.vendorId = vendorId;
        this.orderList = orderList;
        this.totalPrice = totalPrice;
        this.customerMoneyAccount = customerMoneyAccount;
    }

    protected orderStack(Parcel in) {
        customerId = in.readInt();
        vendorId = in.readInt();
        orderList = in.readArrayList(order.class.getClassLoader());
        totalPrice = in.readInt();
        customerMoneyAccount = in.readInt();

    }

    public static final Creator<orderStack> CREATOR = new Creator<orderStack>() {
        @Override
        public orderStack createFromParcel(Parcel in) {
            return new orderStack(in);
        }

        @Override
        public orderStack[] newArray(int size) {
            return new orderStack[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(customerId);
        dest.writeInt(vendorId);
        dest.writeList(orderList);
        dest.writeInt(totalPrice);
        dest.writeInt(customerMoneyAccount);
    }
}
