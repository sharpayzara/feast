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
import com.school.food.feast.entity.CZHistory;
import com.school.food.feast.entity.PreOrder;
import com.school.food.feast.entity.User;
import com.school.food.feast.entity.UserOrder;
import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.Constant;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PayPreOrderActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    private RadioButton zfb_radio,ye_radio;
    private BusinessEntity entity;
    private Button pay_btn;
    private TextView prize_et;
    private ImageView icon_layout;
    private Context mContext;
    private Double factTotalMoney;
    private TextView balance_tv;
    private String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_preorder_pay);
        super.onCreate(savedInstanceState);
        BmobPay.init(mContext, Constant.APPID);
        factTotalMoney = getIntent().getDoubleExtra("factTotalMoney",0);
        mContext = this;
        initUI();
    }

    private void initUI() {
        setHeadTitle("预定付款");
        showBackBtn();
        balance_tv = (TextView) findViewById(R.id.balance_tv);
        icon_layout = (ImageView) findViewById(R.id.icon_layout);
        pay_btn = (Button) findViewById(R.id.pay_btn);
        prize_et = (TextView) findViewById(R.id.prize_et);
        prize_et.setText(factTotalMoney+"");
        zfb_radio = (RadioButton) findViewById(R.id.zfb_btn);
        ye_radio = (RadioButton) findViewById(R.id.ye_btn);
        ye_radio.setChecked(true);
        pay_btn.setOnClickListener(this);
//        icon_layout.setOnClickListener(this);
        balance_tv.setText(UserServices.getAccountBalance(this)+"元");
    }

    @Override
    public void onClick(View v) {

        if(v == pay_btn){
            if(factTotalMoney == 0){
                toast("无效金额，付款失败");
                return;
            }
            pay();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Constant.REQUESTCODE.DMFRESULT){
            entity = (BusinessEntity) getIntent().getSerializableExtra("chooseEntity");
        }
    }

    public void updateAccount(){
        final User user = new User();
        user.setAccountMoney(Double.parseDouble(UserServices.getAccountBalance(this)) - Double.parseDouble(prize_et.getText().toString()));
        String userObjectId = UserServices.getUser(this).getObjectId();
        user.update(this, userObjectId, new UpdateListener() {

            @Override
            public void onSuccess() {
                updateOrder();
                balance_tv.setText(UserServices.getAccountBalance(mContext) + "元");
            }

            @Override
            public void onFailure(int code, String msg) {
            }
        });
    }


    private void createOrder() {

    }
    public String getRandomCode(){
        String str = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        String str2[] = str.split(",");//将字符串以,分割
        int sum = 0;//计数器
        Random rand = new Random();//创建Random类的对象rand
        String  randomStr = "";
        int index = 0;
        for (int i=0; i<4; ++i)
        {
            index = rand.nextInt(str2.length-1);//在0到str2.length-1生成一个伪随机数赋值给index
            randomStr += str2[index];//将对应索引的数组与randStr的变量值相连接
        }
        return randomStr;
    }

    public void updateOrder(){
        UserOrder order = new UserOrder();
        order.setPhoneNum(UserServices.getPhoneNum(mContext));
        order.setTotalMoney(getIntent().getDoubleExtra("totalMoney",0));
        order.setFactTotalMoney(getIntent().getDoubleExtra("factTotalMoney",0)+"");
        order.setBusinessName(getIntent().getStringExtra("businessName"));
        order.setOrderId(UserServices.getPhoneNum(this).substring(7,11) + getRandomCode());
        order.setPreOrders((List<PreOrder>) getIntent().getSerializableExtra("preOrderList"));
        order.setUse(false);
        order.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                toast("下单成功，请在订单中查看或消费");
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(mContext,"下单失败，针对财产问题请在意见反馈栏备注说明，工作人员会及时处理",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void pay(){
        if(TextUtils.isEmpty(prize_et.getText().toString())){
            Toast.makeText(mContext,"金额不能为空，付款失败",Toast.LENGTH_SHORT).show();
        }else{
            if(ye_radio.isChecked()){
                if( Double.parseDouble(UserServices.getAccountBalance(this)) < Double.parseDouble(prize_et.getText().toString())){
                    Toast.makeText(mContext,"账户余额不足",Toast.LENGTH_SHORT).show();
                }else{
                    updateAccount();
                }
            }else if(zfb_radio.isChecked()){
                Toast.makeText(PayPreOrderActivity.this, "正在支付，请稍后..", Toast.LENGTH_SHORT).show();
                new BmobPay((Activity) mContext).pay(Double.parseDouble(prize_et.getText().toString()),"菜品",UserServices.getPhoneNum(mContext),new PayListener(){
                    @Override
                    public void orderId(String s) {
                       // Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
                        orderId = s;
                    }

                    @Override
                    public void succeed() {
                        Toast.makeText(mContext,"支付成功",Toast.LENGTH_SHORT).show();
                        UserServices.addLotteryNum(mContext);
                        updateOrder();
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
