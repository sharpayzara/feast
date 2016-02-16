package com.school.food.feast.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.school.food.feast.R;
import com.school.food.feast.activity.LoginActivity;
import com.school.food.feast.layout.HeadControlPanel;
import com.school.food.feast.services.UserServices;

/**
 * Created by Administrator on 2015/12/8.
 */
public class CommonHeadPanelActivity extends BaseActivity {
    LinearLayout backBtn;
    HeadControlPanel headControlPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headControlPanel = (HeadControlPanel) findViewById(R.id.head_layout);
        backBtn = (LinearLayout) findViewById(R.id.left_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setHeadTitle(String msg) {
        headControlPanel.setMiddleTitle(msg);
    }

    public void showBackBtn() {
        backBtn.setVisibility(View.VISIBLE);
    }

    public void judgeIsLogin(){
        if(!UserServices.isLogin(this)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent,1);
        }
    }
}
