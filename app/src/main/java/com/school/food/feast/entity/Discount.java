package com.school.food.feast.entity;

import cn.bmob.v3.BmobObject;

/**
 * author   Jhong
 * Date:  2016/2/9.
 * version:  V1.0
 * Description:
 */
public class Discount  extends BmobObject {
    private Double discountValue; // 折扣大小
    private Long discountTime; // 折扣时间 精确到分钟数

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Long getDiscountTime() {
        return discountTime;
    }

    public void setDiscountTime(Long discountTime) {
        this.discountTime = discountTime;
    }
}
