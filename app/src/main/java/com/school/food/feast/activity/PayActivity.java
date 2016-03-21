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
import com.school.food.feast.entity.User;
import com.school.food.feast.entity.UserAccount;
import com.school.food.feast.entity.UserOrder;
import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.CommonUtils;
import com.school.food.feast.util.Constant;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PayActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    private RadioButton zfb_radio,ye_radio;
    private BusinessEntity entity;
    private Button pay_btn;
    private EditText prize_et;
    private ImageView icon_layout;
    private Context mContext;
    private TextView balance_tv;
    private Double userBalance = 0d;
    private boolean isCanPay = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay);
        super.onCreate(savedInstanceState);
        BmobPay.init(mContext, Constant.APPID);
        mContext = this;
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCanPay = true;
    }

    private void initUI() {
        setHeadTitle("当面付");
        showBackBtn();
        balance_tv = (TextView) findViewById(R.id.balance_tv);
        icon_layout = (ImageView) findViewById(R.id.icon_layout);
        pay_btn = (Button) findViewById(R.id.pay_btn);
        prize_et = (EditText) findViewById(R.id.prize_et);
        zfb_radio = (RadioButton) findViewById(R.id.zfb_btn);
        ye_radio = (RadioButton) findViewById(R.id.ye_btn);
        ye_radio.setChecked(true);
        pay_btn.setOnClickListener(this);
        icon_layout.setOnClickListener(this);
        //balance_tv.setText(UserServices.getAccountBalance(this) + "元");
        getAccountBalance();
    }

    public void getAccountBalance(){
        BmobQuery<UserAccount> bmobQuery = new BmobQuery<UserAccount>();
        bmobQuery.addWhereEqualTo("userPhone", BmobUser.getCurrentUser(mContext).getMobilePhoneNumber());
        bmobQuery.findObjects(mContext, new FindListener<UserAccount>() {
            @Override
            public void onSuccess(List<UserAccount> list) {
                if(list.size() > 0){
                    balance_tv.setText(CommonUtils.getDoubleData(list.get(0).getAccountMoney())+"元");
                    userBalance = list.get(0).getAccountMoney();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mContext, "获取数据失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v == icon_layout){
            Intent intent = new Intent(mContext,ChannelActivity.class);
            intent.putExtra("dmf","当面付");
            startActivityForResult(intent,1);
        }else if(v == pay_btn){
            if(!isCanPay){
                return;
            }
            isCanPay = false;
            if(entity == null|| TextUtils.isEmpty(entity.getName())){
                toast("请选择商家");
                return;
            }


            if(TextUtils.isEmpty(prize_et.getText().toString())){
                Toast.makeText(mContext,"金额不能为空，付款失败",Toast.LENGTH_SHORT).show();
            }else{
                if(ye_radio.isChecked()){
                    if( userBalance < Double.parseDouble(prize_et.getText().toString())){
                        Toast.makeText(mContext,"账户余额不足",Toast.LENGTH_SHORT).show();
                    }else{
                        updateAccount();
                    }
                }else if(zfb_radio.isChecked()){

                    new BmobPay((Activity) mContext).pay(Double.parseDouble(prize_et.getText().toString()),"菜品",new PayListener(){
                        @Override
                        public void orderId(String s) {
                            //Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void succeed() {
                            Toast.makeText(mContext,"支付成功",Toast.LENGTH_SHORT).show();
                            UserServices.addLotteryNum(mContext);
                            createOrder();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Constant.REQUESTCODE.DMFRESULT){
            entity = (BusinessEntity) data.getSerializableExtra("chooseEntity");
            setBusinessIcon();
        }
    }

    public void setBusinessIcon(){
        if(entity.getId() == 1){
            icon_layout.setImageResource(R.mipmap.cxm_icon);
        }else if(entity.getId() == 2){
            icon_layout.setImageResource(R.mipmap.dxm_icon);
        }else if(entity.getId() == 3){
            icon_layout.setImageResource(R.mipmap.llx_icon);
        }else if(entity.getId() == 4){
            icon_layout.setImageResource(R.mipmap.xp_icon);
        }else if(entity.getId() == 5){
            icon_layout.setImageResource(R.mipmap.hls_icon);
        }else if(entity.getId() == 6){
            icon_layout.setImageResource(R.mipmap.lgj_icon);
        }else if(entity.getId() == 7){
            icon_layout.setImageResource(R.mipmap.hzd_icon);
        }else if(entity.getId() == 8){
            icon_layout.setImageResource(R.mipmap.yyys_icon);
        }else if(entity.getId() == 9){
            icon_layout.setImageResource(R.mipmap.hzw_icon);
        }else if(entity.getId() == 10){
            icon_layout.setImageResource(R.mipmap.hhmc_icon);
        }else if(entity.getId() == 11){
            icon_layout.setImageResource(R.mipmap.gg_icon);
        }else if(entity.getId() == 12){
            icon_layout.setImageResource(R.mipmap.bfg_icon);
        }else if(entity.getId() == 13){
            icon_layout.setImageResource(R.mipmap.hhlr_icon);
        }else if(entity.getId() == 14){
            icon_layout.setImageResource(R.mipmap.xael_icon);
        }else if(entity.getId() == 15){
            icon_layout.setImageResource(R.mipmap.ysys_icon);
        }else if(entity.getId() == 16){
            icon_layout.setImageResource(R.mipmap.hgd_icon);
        }else if(entity.getId() == 17){
            icon_layout.setImageResource(R.mipmap.ky_icon);
        }
    }
    /*public void updateAccount(){
        final User user = new User();
        user.setAccountMoney(Double.parseDouble(UserServices.getAccountBalance(this)) - Double.parseDouble(prize_et.getText().toString()));
        String userObjectId = UserServices.getUser(this).getObjectId();
        user.update(this, userObjectId, new UpdateListener() {

            @Override
            public void onSuccess() {
                createOrder();
               // balance_tv.setText(UserServices.getAccountBalance(mContext) + "元");
                getAccountBalance();
            }

            @Override
            public void onFailure(int code, String msg) {
            }
        });
    }*/

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
        account.setAccountMoney(userAccount.getAccountMoney() - Double.parseDouble(prize_et.getText().toString()));
        account.update(this, userAccount.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
               getAccountBalance();
                createOrder();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }



    public String getRandomCode(){
        String str = "0,1,2,3,4,5,6,7,8,9";
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
    public void createOrder(){
        UserOrder order = new UserOrder();
        order.setPhoneNum(UserServices.getPhoneNum(mContext));
        order.setTotalMoney(Double.parseDouble(prize_et.getText().toString()));
        order.setFactTotalMoney(prize_et.getText().toString());
        order.setUnReg(false);
        order.setBusinessName(entity.getName());
        order.setOrderId(UserServices.getPhoneNum(this).substring(7,11) + getRandomCode());
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
}
