package com.school.food.feast.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/12/16.
 */
public class PreOrder extends BmobObject {
    private String dishName;
    private Double dishValue;
    private Integer dishNum;

    public PreOrder(String dishName, Double dishValue, Integer dishNum) {
        this.dishName = dishName;
        this.dishValue = dishValue;
        this.dishNum = dishNum;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Double getDishValue() {
        return dishValue;
    }

    public void setDishValue(Double dishValue) {
        this.dishValue = dishValue;
    }

    public Integer getDishNum() {
        return dishNum;
    }

    public void setDishNum(Integer dishNum) {
        this.dishNum = dishNum;
    }
}
