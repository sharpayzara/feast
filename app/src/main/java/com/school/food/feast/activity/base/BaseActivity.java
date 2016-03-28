package com.school.food.feast.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.school.food.feast.activity.LoginActivity;
import com.school.food.feast.services.UserServices;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2015/12/8.
 */
public class BaseActivity extends Activity {
    public static String TAG = "bmob";

    protected ListView mListview;

    public boolean judgeIsLogin(){
        if(!UserServices.isLogin(this)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent,1);
            return false;
        }else return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }
    public void toastForLong(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        Log.d(TAG, msg);
    }

    Toast mToast;

    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    public static void showLog(String msg) {
        Log.i("smile", msg);
    }
}
