package com.school.food.feast.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/12/16.
 */
public class Order extends BmobObject {
    private static final long serialVersionUID = 1L;
    private String dishesDetail;
    private String orderNum;
    private String totalMoney;

    public Order(String dishesDetail, String orderNum, String totalMoney) {
        super();
        this.dishesDetail = dishesDetail;
        this.orderNum = orderNum;
        this.totalMoney = totalMoney;
    }

    public String getDishesDetail() {
        return dishesDetail;
    }

    public void setDishesDetail(String dishesDetail) {
        this.dishesDetail = dishesDetail;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
