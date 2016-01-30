package com.school.food.feast.entity;

import java.io.Serializable;

public class Dish  implements Serializable {
    private String dishName;
    private Double dishValue;

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
}