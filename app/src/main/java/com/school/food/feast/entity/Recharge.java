package com.school.food.feast.entity;

import cn.bmob.v3.BmobObject;

/**
 * author   Jhong
 * Date:  2016/2/9.
 * version:  V1.0
 * Description:
 */
public class Recharge extends BmobObject {
    private Integer chargeValue;
    private Integer chargeGive;

    public Integer getChargeValue() {
        return chargeValue;
    }

    public void setChargeValue(Integer chargeValue) {
        this.chargeValue = chargeValue;
    }

    public Integer getChargeGive() {
        return chargeGive;
    }

    public void setChargeGive(Integer chargeGive) {
        this.chargeGive = chargeGive;
    }
}
