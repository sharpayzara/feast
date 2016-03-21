package com.school.food.feast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;
import com.school.food.feast.entity.User;
import com.school.food.feast.entity.UserAccount;
import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.CommonUtils;
import com.school.food.feast.util.Constant;
import com.school.food.feast.util.TimeCountUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;

public class QueryBalanceActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    private TextView account_balance;
    private Button go_cz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_query_balance);
        super.onCreate(savedInstanceState);
        initUI();
        judgeIsLogin();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!UserServices.isLogin(this)){
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDate();
    }

    private void initUI() {
        showBackBtn();
        setHeadTitle("账户余额");
        account_balance = (TextView) findViewById(R.id.account_balance);
        go_cz = (Button) findViewById(R.id.go_cz);
        go_cz.setOnClickListener(this);
    }
    private void loadDate(){
        //account_balance.setText(UserServices.getAccountBalance(this) + "元");
        getAccountBalance();
    }

    @Override
    public void onClick(View v) {
        if(v == go_cz){
            Intent intent = new Intent(this,CZActivity.class);
            startActivity(intent);

        }
    }
    public void getAccountBalance(){
        BmobQuery<UserAccount> bmobQuery = new BmobQuery<UserAccount>();
        bmobQuery.addWhereEqualTo("userPhone", BmobUser.getCurrentUser(this).getMobilePhoneNumber());
        bmobQuery.findObjects(this, new FindListener<UserAccount>() {
            @Override
            public void onSuccess(List<UserAccount> list) {
                if(list.size() > 0){
                    account_balance.setText(CommonUtils.getDoubleData(list.get(0).getAccountMoney())+"元");
            }

        }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(QueryBalanceActivity.this,"数据获取失败，请稍后再试...",Toast.LENGTH_SHORT).show();
            }
             });
    }
}
