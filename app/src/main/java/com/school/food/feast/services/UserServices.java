package com.school.food.feast.services;

import android.content.Context;

import com.school.food.feast.entity.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2015/12/16.
 */
public class UserServices{

    public static String getPhoneNum(Context mContext){
        return (String) BmobUser.getObjectByKey(mContext, "mobilePhoneNumber");
    }
    public static boolean isLogin(Context mContext) {
        User myUser = BmobUser.getCurrentUser(mContext, User.class);
        if (myUser != null) {  //已经登录
            return true;
        } else {
            return false;  //没有登录
        }
    }
}
