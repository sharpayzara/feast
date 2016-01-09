package com.school.food.feast.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;
import com.school.food.feast.entity.User;
import com.school.food.feast.util.Constant;
import com.school.food.feast.util.TimeCountUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;

public class LoginActivity extends CommonHeadPanelActivity implements View.OnClickListener{

    EditText phoneNum,verifyNum;
    Button obtainVerify,loginBtn;
    CountDownTimer timeCountUtil;

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
        if(!TextUtils.isEmpty(phoneNum.getText().toString()) || phoneNum.getText().toString().length() == 11){
            timeCountUtil = new TimeCountUtil(this, 60000, 1000, obtainVerify);
            timeCountUtil.start();
            BmobSMS.requestSMSCode(this, phoneNum.getText().toString(), "注册模板", new RequestSMSCodeListener() {
                @Override
                public void done(Integer smsId, BmobException ex) {
                    // TODO Auto-generated method stub
                    if (ex == null) {//验证码发送成功
                        toast("验证码请求成功，请稍后..");
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
            //signOrLogin();
            setResult(Constant.REQUESTCODE.LOGINACTIVITY);
            finish();
        }
    }


   /* private void verifySmsCode(){

        if(!TextUtils.isEmpty(phoneNum.getText().toString())&&!TextUtils.isEmpty(verifyNum.getText().toString())){
            BmobSMS.verifySmsCode(this,phoneNum.getText().toString(),verifyNum.getText().toString(), new VerifySMSCodeListener() {
                @Override
                public void done(BmobException ex) {
                    // TODO Auto-generated method stub
                    if(ex==null){//短信验证码已验证成功
                        testSignUp();
                    }else{
                        toast("验证失败");
                    }
                }
            });
        }else{
            toast("请输入手机号和验证码");
        }
    }*/

    private void signOrLogin() {
        BmobUser.signOrLoginByMobilePhone(this, phoneNum.getText().toString(), verifyNum.getText().toString(),new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                // TODO Auto-generated method stub
                if (user != null) {
                    toast("登录成功");
                    /*setResult(Constant.REQUESTCODE.LOGINACTIVITY);
                    finish();*/
                } else {
                    toast("验证失败");
                }
            }
        });
    }
}
