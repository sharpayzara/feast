package com.school.food.feast.util;

import android.content.Context;
import android.util.DisplayMetrics;

import java.text.DecimalFormat;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.GetServerTimeListener;

public class CommonUtils {
    public static String getDoubleData(Double data){
        DecimalFormat    df   = new DecimalFormat("######0.00");
        return df.format(data);
    }

}
