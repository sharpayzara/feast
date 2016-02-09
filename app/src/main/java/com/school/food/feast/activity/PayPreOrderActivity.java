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
import com.school.food.feast.entity.PreOrder;
import com.school.food.feast.entity.UserOrder;
import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.Constant;

import java.util.List;

import cn.bmob.v3.listener.SaveListener;

public class PayPreOrderActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    private RadioButton zfb_radio,ye_radio;
    private BusinessEntity entity;
    private Button pay_btn;
    private TextView prize_et;
    private ImageView icon_layout;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_preorder_pay);
        super.onCreate(savedInstanceState);
        BmobPay.init(mContext, Constant.APPID);
        mContext = this;
        initUI();
    }

    private void initUI() {
        setHeadTitle("当面付");
        showBackBtn();
        icon_layout = (ImageView) findViewById(R.id.icon_layout);
        pay_btn = (Button) findViewById(R.id.pay_btn);
        prize_et = (TextView) findViewById(R.id.prize_et);
        zfb_radio = (RadioButton) findViewById(R.id.zfb_btn);
        ye_radio = (RadioButton) findViewById(R.id.ye_btn);
        ye_radio.setChecked(true);
        pay_btn.setOnClickListener(this);
        icon_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UserOrder order = new UserOrder();
        order.setPhoneNum(UserServices.getPhoneNum(mContext));
        order.setTotalMoney(getIntent().getDoubleExtra("factTotalMoney",0));
        order.setPreOrders((List<PreOrder>) getIntent().getSerializableExtra("preOrderList"));
        order.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                pay();
            }

            @Override
            public void onFailure(int code, String msg) {
                toast("下单失败，请稍候再试！");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Constant.REQUESTCODE.DMFRESULT){
            entity = (BusinessEntity) getIntent().getSerializableExtra("chooseEntity");
        }
    }
    public void pay(){
        if(TextUtils.isEmpty(prize_et.getText().toString())){
            Toast.makeText(mContext,"金额不能为空，付款失败",Toast.LENGTH_SHORT).show();
        }else{
            if(ye_radio.isChecked()){
                Toast.makeText(mContext,"账户余额不足",Toast.LENGTH_SHORT).show();
            }else if(zfb_radio.isChecked()){

                new BmobPay((Activity) mContext).pay(Double.parseDouble(prize_et.getText().toString()),"菜品",new PayListener(){
                    @Override
                    public void orderId(String s) {
                        Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void succeed() {
                        Toast.makeText(mContext,"支付成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(int i, String s) {
                        Toast.makeText(mContext,"支付失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void unknow() {
                        Toast.makeText(mContext,"支付失败，网络异常",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
