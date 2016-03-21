package com.school.food.feast.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2015/12/16.
 */
public class User extends BmobUser {
    private static final long serialVersionUID = 3292304921701053144L;
    private String payPassword;
   /* private Double accountMoney = 0d;*/
    private Integer lotteryNum;

    public Integer getLotteryNum() {
        return lotteryNum;
    }

    public void setLotteryNum(Integer lotteryNum) {
        this.lotteryNum = lotteryNum;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /*public Double getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Double accountMoney) {
        this.accountMoney = accountMoney;
    }*/
}
