package com.school.food.feast.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;
import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;
import com.school.food.feast.entity.BusinessEntity;
import com.school.food.feast.entity.CZHistory;
import com.school.food.feast.entity.Discount;
import com.school.food.feast.entity.Recharge;
import com.school.food.feast.entity.User;
import com.school.food.feast.entity.UserAccount;
import com.school.food.feast.entity.UserOrder;
import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.Constant;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CZActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    private Context mContext;
    private TextView cz_tv1,czs_tv1,cz_tv2,czs_tv2,cz_tv3,czs_tv3,cz_tv4,czs_tv4,zs_value,balance_tv;
    private List<Recharge> rechargeList;
    private EditText prize_et;
    private Button pay_btn;
    private int zsValue = 0;
    public static String objectId="";
    private LinearLayout cz_llt1,cz_llt2,cz_llt3,cz_llt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cz);
        super.onCreate(savedInstanceState);
        initUI();
        initData();
        mContext = this;
        judgeIsLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void getAccountBalance(){
        BmobQuery<UserAccount> bmobQuery = new BmobQuery<UserAccount>();
        bmobQuery.addWhereEqualTo("userPhone",BmobUser.getCurrentUser(mContext).getMobilePhoneNumber());
        bmobQuery.findObjects(mContext, new FindListener<UserAccount>() {
            @Override
            public void onSuccess(List<UserAccount> list) {
                if(list.size() > 0){
                   balance_tv.setText(list.get(0).getAccountMoney()+"元");
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mContext, "获取数据失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
       //balance_tv.setText(UserServices.getAccountBalance(this)+"元");
        getAccountBalance();
        rechargeList = new ArrayList<>();
        BmobQuery<Recharge> bmobQuery = new BmobQuery<Recharge>();
        bmobQuery.order("chargeValue");
        bmobQuery.findObjects(this, new FindListener<Recharge>() {

            @Override
            public void onSuccess(List<Recharge> objects) {
                // TODO Auto-generated method stub
                if(objects!=null && objects.size()>0){
                    rechargeList.addAll(objects);
                    if(rechargeList.size() > 0 ){
                        cz_tv1.setText("充"+rechargeList.get(0).getChargeValue()+"元");
                        czs_tv1.setText("充"+rechargeList.get(0).getChargeGive()+"元");
                    }
                    if (rechargeList.size() > 1) {
                        cz_tv2.setText("充"+rechargeList.get(1).getChargeValue()+"元");
                        czs_tv2.setText("充"+rechargeList.get(1).getChargeGive()+"元");
                    }
                    if (rechargeList.size() > 2) {
                        cz_tv3.setText("充"+rechargeList.get(2).getChargeValue()+"元");
                        czs_tv3.setText("充"+rechargeList.get(2).getChargeGive()+"元");
                    }
                    if (rechargeList.size() > 3) {
                        cz_tv4.setText("充"+rechargeList.get(3).getChargeValue()+"元");
                        czs_tv4.setText("充"+rechargeList.get(3).getChargeGive()+"元");
                    }
                }
            }

            @Override
            public void onError(int code, String arg0) {
                Toast.makeText(mContext, "获取数据失败，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initUI() {
        showBackBtn();
        setHeadTitle("账户充值");
        balance_tv = (TextView) findViewById(R.id.balance_tv);
        cz_tv1 = (TextView) findViewById(R.id.cz_tv1);
        czs_tv1 = (TextView) findViewById(R.id.czs_tv1);
        cz_tv2 = (TextView) findViewById(R.id.cz_tv2);
        czs_tv2 = (TextView) findViewById(R.id.czs_tv2);
        cz_tv3 = (TextView) findViewById(R.id.cz_tv3);
        czs_tv3 = (TextView) findViewById(R.id.czs_tv3);
        cz_tv4 = (TextView) findViewById(R.id.cz_tv4);
        czs_tv4 = (TextView) findViewById(R.id.czs_tv4);
        prize_et = (EditText) findViewById(R.id.prize_et);
        zs_value = (TextView) findViewById(R.id.zs_value);
        pay_btn = (Button) findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(this);
        cz_llt1 = (LinearLayout) findViewById(R.id.cz_llt1);
        cz_llt2 = (LinearLayout) findViewById(R.id.cz_llt2);
        cz_llt3 = (LinearLayout) findViewById(R.id.cz_llt3);
        cz_llt4 = (LinearLayout) findViewById(R.id.cz_llt4);
        cz_llt1.setOnClickListener(this);
        cz_llt2.setOnClickListener(this);
        cz_llt3.setOnClickListener(this);
        cz_llt4.setOnClickListener(this);
        prize_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    for(int i = rechargeList.size()-1;i >= 0; i--){
                        if(Double.parseDouble(s.toString()) >= rechargeList.get(i).getChargeValue()){
                            zs_value.setText("送" + rechargeList.get(i).getChargeGive() + "元");
                            zsValue = rechargeList.get(i).getChargeGive();
                            return;
                        }
                    }
                }
                zs_value.setText("");
                zsValue = 0;
                return;
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if(!UserServices.isLogin(this)){
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == pay_btn){
            if(TextUtils.isEmpty(prize_et.getText().toString())){
                toast("请输入充值金额");
                return;
            }
            final CZHistory history = new CZHistory();
            history.setUserPhone(UserServices.getPhoneNum(this));
            history.setCzValue(Double.parseDouble(prize_et.getText().toString()));
            history.setSuccess(false);
            history.save(this, new SaveListener() {

                @Override
                public void onSuccess() {
                    objectId = history.getObjectId();
                    pay();
                }

                @Override
                public void onFailure(int code, String msg) {
                    toast("充值失败，请稍后重试");
                }
            });
        }else if(cz_llt1 == v){
            prize_et.setText(rechargeList.get(0).getChargeValue()+"");
        }else if(cz_llt2 == v){
            prize_et.setText(rechargeList.get(1).getChargeValue()+"");
        }else if(cz_llt3 == v){
            prize_et.setText(rechargeList.get(2).getChargeValue()+"");
        }else if(cz_llt4 == v){
            prize_et.setText(rechargeList.get(3).getChargeValue()+"");
        }
    }

    public void updateHistory(){
        final CZHistory history = new CZHistory();
        history.setSuccess(true);
        history.update(this, objectId, new UpdateListener() {

            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
            }
        });
    }
    public void updateAccount(){
        BmobQuery<UserAccount> bmobQuery = new BmobQuery<UserAccount>();
        bmobQuery.addWhereEqualTo("userPhone",BmobUser.getCurrentUser(mContext).getMobilePhoneNumber());
        bmobQuery.findObjects(mContext, new FindListener<UserAccount>() {
            @Override
            public void onSuccess(List<UserAccount> list) {
                if(list.size() > 0){
                    updateUserAccount(list.get(0));
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mContext, "获取数据失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateUserAccount(UserAccount userAccount){
        final UserAccount account = new UserAccount();
        account.setAccountMoney(userAccount.getAccountMoney() + Double.parseDouble(prize_et.getText().toString()) + zsValue );
        account.update(this, userAccount.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                updateHistory();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public void pay(){
        new BmobPay((Activity) mContext).pay(Double.parseDouble(prize_et.getText().toString()),"充值",new PayListener(){
            @Override
            public void orderId(String s) {
                //  Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void succeed() {
                Toast.makeText(mContext,"充值成功",Toast.LENGTH_SHORT).show();
                updateAccount();
            }

            @Override
            public void fail(int i, String s) {
                Toast.makeText(mContext,"充值失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unknow() {
                Toast.makeText(mContext,"充值失败，网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.REQUESTCODE.LOGINACTIVITY) {
           // balance_tv.setText(UserServices.getAccountBalance(this)+"元");
            getAccountBalance();
        }
    }
}
