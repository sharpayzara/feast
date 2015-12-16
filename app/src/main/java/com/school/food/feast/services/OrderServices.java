package com.school.food.feast.services;

import android.content.Context;
import android.widget.Toast;

import com.school.food.feast.entity.Order;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2015/12/16.
 */
public class OrderServices{

    public static void addOrder(final Context mContext,Order order){

        order.save(mContext, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Toast.makeText(mContext,"订单添加成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(mContext,"订单添加失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
