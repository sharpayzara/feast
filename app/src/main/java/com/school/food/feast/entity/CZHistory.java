package com.school.food.feast.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/12/16.
 */
public class CZHistory extends BmobObject {
    private String userPhone;
    private Boolean isSuccess;
    private Double czValue;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Double getCzValue() {
        return czValue;
    }

    public void setCzValue(Double czValue) {
        this.czValue = czValue;
    }
}
