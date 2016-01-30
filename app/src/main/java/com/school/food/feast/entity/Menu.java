package com.school.food.feast.entity;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/12/16.
 */
public class Menu extends BmobObject implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer seqId;
    private String BusinessName;
    private List<MenuType> menuType;

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public List<MenuType> getMenuType() {
        return menuType;
    }

    public void setMenuType(List<MenuType> menuType) {
        this.menuType = menuType;
    }
}
