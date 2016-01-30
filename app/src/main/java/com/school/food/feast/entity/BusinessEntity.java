package com.school.food.feast.entity;

import java.io.Serializable;

/**
 * author   Jhong
 * Date:  2016/1/30.
 * version:  V1.0
 * Description:
 */
public class BusinessEntity implements Serializable{
    private Integer  id;
    private String name;

    public BusinessEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
