package com.school.food.feast.entity;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/12/16.
 */
public class UserOrder extends BmobObject implements Serializable{
    private Double totalMoney;
    private String businessName;
    private String phoneNum;
    private List<PreOrder> preOrders;

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<PreOrder> getPreOrders() {
        return preOrders;
    }

    public void setPreOrders(List<PreOrder> preOrders) {
        this.preOrders = preOrders;
    }
}
