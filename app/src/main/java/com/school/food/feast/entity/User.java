package com.school.food.feast.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2015/12/16.
 */
public class User extends BmobUser {
    private static final long serialVersionUID = 3292304921701053144L;
    private String payPassword;

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
}
