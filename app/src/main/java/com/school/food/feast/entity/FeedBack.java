package com.school.food.feast.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class FeedBack  extends BmobObject implements Serializable {
    private String feedBack;
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }
}