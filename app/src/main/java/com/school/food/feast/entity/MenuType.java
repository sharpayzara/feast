package com.school.food.feast.entity;

import java.io.Serializable;
import java.util.List;
public class MenuType  implements Serializable {
    private String typeName;
    private List<Dish> dish;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Dish> getDish() {
        return dish;
    }

    public void setDish(List<Dish> dish) {
        this.dish = dish;
    }
}