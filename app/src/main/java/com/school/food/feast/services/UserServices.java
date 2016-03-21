package com.school.food.feast.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.school.food.feast.entity.Order;
import com.school.food.feast.entity.User;
import com.school.food.feast.entity.UserAccount;
import com.school.food.feast.entity.UserOrder;

import java.text.DecimalFormat;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2015/12/16.
 */
public class UserServices{

    public static void addLotteryNum(Context mContext){

        User myUser = BmobUser.getCurrentUser(mContext, User.class);
        if(myUser != null && myUser.getLotteryNum() == null){
            myUser.setLotteryNum(1);
        }else if(myUser != null){
            myUser.setLotteryNum(myUser.getLotteryNum() + 1);
        }
        myUser.update(mContext);
    }

    public static Integer getLotteryNum(Context mContext){
        User myUser = BmobUser.getCurrentUser(mContext, User.class);
        if(myUser != null && myUser.getLotteryNum() == null){
            return  0;
        }else if(myUser != null){
            return myUser.getLotteryNum();
        }else{
            return 0;
        }
    }

    public static void subLotteryNum(Context mContext){
        User myUser = BmobUser.getCurrentUser(mContext, User.class);
        if(myUser == null){
            return;
        }
        else if(myUser.getLotteryNum() == null){
            myUser.setLotteryNum(0);
        }else if(myUser.getLotteryNum() == 0){
            return;
        }else{
            myUser.setLotteryNum(myUser.getLotteryNum() - 1);
        }
        myUser.update(mContext);
    }

    public static User getUser(Context mContext){
        return BmobUser.getCurrentUser(mContext, User.class);
    }

    public static String getPhoneNum(Context mContext){
        return (String) BmobUser.getObjectByKey(mContext, "mobilePhoneNumber");
    }

   /* public static String getAccountBalance(Context mContext){
        User myUser = BmobUser.getCurrentUser(mContext, User.class);
        if(myUser != null && myUser.getAccountMoney() != null){
            DecimalFormat df   = new DecimalFormat("######0.00");
            return df.format(myUser.getAccountMoney()).toString();
        }else{
            return "0";
        }
    }*/

    public static boolean isLogin(Context mContext) {
        User myUser = BmobUser.getCurrentUser(mContext, User.class);
        if (myUser != null) {  //已经登录
            return true;
        } else {
            return false;  //没有登录
        }
    }



}
