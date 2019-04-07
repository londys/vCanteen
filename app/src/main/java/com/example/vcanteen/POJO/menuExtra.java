package com.example.vcanteen.POJO;

import com.example.vcanteen.food;

import java.util.ArrayList;

public class menuExtra {
    public com.example.vcanteen.food food;

    public com.example.vcanteen.food getFood() {
        return food;
    }

    public void setFood(com.example.vcanteen.food food) {
        this.food = food;
    }

    public ArrayList<com.example.vcanteen.POJO.extraItemList> getExtraItemList() {
        return extraItemList;
    }

    public void setExtraItemList(ArrayList<com.example.vcanteen.POJO.extraItemList> extraItemList) {
        this.extraItemList = extraItemList;
    }

    public ArrayList<extraItemList> extraItemList;
}
