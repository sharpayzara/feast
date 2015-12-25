package com.school.food.feast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;

public class PasswordActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    RelativeLayout setPasswrodLayout,changePasswrodLayout,findPasswrodLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay_password);
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void  initUI(){
        setHeadTitle("登录");
        showBackBtn();
        findPasswrodLayout = (RelativeLayout) findViewById(R.id.findPasswrodLayout);
        changePasswrodLayout = (RelativeLayout) findViewById(R.id.changePasswrodLayout);
        setPasswrodLayout = (RelativeLayout) findViewById(R.id.setPasswrodLayout);
        findPasswrodLayout.setOnClickListener(this);
        changePasswrodLayout.setOnClickListener(this);
        setPasswrodLayout.setOnClickListener(this);
        /*if(TextUtils.isEmpty(UserServices.getUser(this).getPayPassword())){
            findPasswrodLayout.setVisibility(View.GONE);
            changePasswrodLayout.setVisibility(View.GONE);
            setPasswrodLayout.setVisibility(View.VISIBLE);
        }else{
            findPasswrodLayout.setVisibility(View.VISIBLE);
            changePasswrodLayout.setVisibility(View.VISIBLE);
            setPasswrodLayout.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onClick(View v) {
        if(findPasswrodLayout == v){
            Intent intent = new Intent();
        }
        if(changePasswrodLayout == v){
            Intent intent = new Intent();
        }
        if(setPasswrodLayout == v){

        }
    }
}
