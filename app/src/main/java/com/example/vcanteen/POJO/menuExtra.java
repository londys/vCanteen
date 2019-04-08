package com.example.vcanteen.POJO;

import com.example.vcanteen.food;

import java.util.ArrayList;

public class menuExtra {




    public com.example.vcanteen.food getFood() {
        return food;
    }

    public void setFood(com.example.vcanteen.food food) {
        this.food = food;
    }

    public ArrayList<extraList> getExtraItemList() {
        return extraItemList;
    }

    public void setExtraItemList(ArrayList<extraList> extraItemList) {
        this.extraItemList = extraItemList;
    }

    public com.example.vcanteen.food food;
    public ArrayList<extraList> extraItemList;
}
