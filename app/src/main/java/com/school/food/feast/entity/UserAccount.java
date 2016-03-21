package com.school.food.feast.entity;

import cn.bmob.v3.BmobObject;

public class UserAccount extends BmobObject {
   private String userPhone;
   private Double accountMoney;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Double getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Double accountMoney) {
        this.accountMoney = accountMoney;
    }
}