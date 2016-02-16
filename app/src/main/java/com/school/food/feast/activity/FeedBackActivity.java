package com.school.food.feast.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;
import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;
import com.school.food.feast.entity.BusinessEntity;
import com.school.food.feast.entity.FeedBack;
import com.school.food.feast.entity.User;
import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.Constant;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class FeedBackActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    EditText feedback_tv,phoneNum_tv;
    Button send_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_feedback);
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        showBackBtn();
        setHeadTitle("意见反馈");
        phoneNum_tv = (EditText) findViewById(R.id.phoneNum_tv);
        feedback_tv = (EditText) findViewById(R.id.feedback_tv);
        send_btn =  (Button) findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == send_btn){
            FeedBack fb = new FeedBack();
            fb.setPhoneNum(phoneNum_tv.getText().toString());
            fb.setFeedBack(feedback_tv.getText().toString());
            fb.save(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    toast("提交成功，谢谢！");
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("网络异常，请稍后再再试！");
                }
            });
        }
    }
}
