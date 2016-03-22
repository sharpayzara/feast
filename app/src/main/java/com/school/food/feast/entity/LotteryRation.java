package com.school.food.feast.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/12/16.
 */
public class LotteryRation extends BmobObject {
    private String prizeName;
    private Integer lottery_ration;

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Integer getLottery_ration() {
        return lottery_ration;
    }

    public void setLottery_ration(Integer lottery_ration) {
        this.lottery_ration = lottery_ration;
    }
}
