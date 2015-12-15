package com.school.food.feast.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

public class LoginActivity extends CommonHeadPanelActivity implements View.OnClickListener{

    EditText phoneNum,verifyNum;
    String currentSMSId,currentPhoneNum;
    Button obtainVerify,loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void  initUI(){
        setHeadTitle("登录");
        showBackBtn();
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        verifyNum = (EditText) findViewById(R.id.verifyNum);
        obtainVerify = (Button) findViewById(R.id.obtain_verify);
        obtainVerify.setOnClickListener(this);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    private void requestSmsCode(){
        currentPhoneNum = phoneNum.getText().toString();
        if(!TextUtils.isEmpty(currentPhoneNum)){
            BmobSMS.requestSMSCode(this, currentPhoneNum, "注册模板", new RequestSMSCodeListener() {

                @Override
                public void done(Integer smsId, BmobException ex) {
                    // TODO Auto-generated method stub
                    if (ex == null) {//验证码发送成功
                        currentSMSId = smsId+"";
                    } else {
                        toast("验证码获取失败，请稍后重试");
                    }
                }
            });
        }else{
            toast("请输入手机号码");
        }
    }

    @Override
    public void onClick(View v) {
        if(v == obtainVerify){
            requestSmsCode();
        }
        if(v == loginBtn){
            if(phoneNum.getText().toString().equals(currentPhoneNum) && verifyNum.getText().toString().equals(currentSMSId)){
                toast("验证通过");
            }else{
                toast("验证码错误");
            }
        }
    }
}
