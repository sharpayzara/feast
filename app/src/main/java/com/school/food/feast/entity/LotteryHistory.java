package com.school.food.feast.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/12/16.
 */
public class LotteryHistory extends BmobObject {
    private String userPhoneNum;
    private String  prizeDetail;
    private Boolean isGrant;

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public Boolean getGrant() {
        return isGrant;
    }

    public void setGrant(Boolean grant) {
        isGrant = grant;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getPrizeDetail() {
        return prizeDetail;
    }

    public void setPrizeDetail(String prizeDetail) {
        this.prizeDetail = prizeDetail;
    }
}
