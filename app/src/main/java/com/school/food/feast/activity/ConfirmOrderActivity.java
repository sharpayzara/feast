package com.school.food.feast.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPaneFraglActivity;
import com.school.food.feast.entity.Discount;
import com.school.food.feast.entity.PreOrder;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetServerTimeListener;


public class ConfirmOrderActivity extends CommonHeadPaneFraglActivity implements View.OnClickListener{
    Long sysCurrentTime;
    TableLayout order_tlt;
    LayoutInflater inflater;
    List<PreOrder> preOrderList;
    Date endDate;
    double totalMoney,factTotalMoney;
    TextView factPayMoney_tv,order_time_tv,discount_tv,hasDiscountValue;
    List<Discount> discountList;
    Context mContext;
    Button pay_btn;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("MM月dd日 HH点mm分");
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            if(date.getTime() < sysCurrentTime){
                Toast.makeText(mContext,"时间不合法，请从新输入",Toast.LENGTH_SHORT).show();
                return;
            }
            DecimalFormat df = new DecimalFormat("######0.0");
            order_time_tv.setText(mFormatter.format(date));
            Long discountTime = date.getTime() - sysCurrentTime;
            Long tempPitch = discountTime/1000/60;
            for(Discount discount : discountList){
                if(tempPitch < discount.getDiscountTime() ){
                    if(discount.getDiscountValue() != 10){
                        discount_tv.setText(discount.getDiscountValue()+"折");
                    }else{
                        discount_tv.setText("");
                    }
                    double tempFactPayMoney = totalMoney * discount.getDiscountValue() / 10;
                    factTotalMoney = tempFactPayMoney;
                    factPayMoney_tv.setText("￥" + df.format(tempFactPayMoney) + "元");
                    hasDiscountValue.setText("优惠" + df.format(totalMoney -  Double.parseDouble(df.format(tempFactPayMoney))) + "元");
                    return;
                }
            }
            Discount dis = discountList.get(discountList.size()-1);
            if(dis.getDiscountValue() != 10){
                discount_tv.setText(dis.getDiscountValue()+"折");
            }
            double tempFactPayMoney = totalMoney * dis.getDiscountValue() / 10;
            factPayMoney_tv.setText("￥" + tempFactPayMoney + "元");
            hasDiscountValue.setText("优惠" + df.format(totalMoney -  Double.parseDouble(df.format(tempFactPayMoney))) + "元");
            factTotalMoney = tempFactPayMoney;
            return;
        }

        @Override
        public void onDateTimeCancel() {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm_order);
        super.onCreate(savedInstanceState);
        mContext = this.getBaseContext();
        initUI();
        initData();
    }

    private void initData() {
        sysCurrentTime = new Date().getTime();
        getServerTime();
        discountList = new ArrayList<>();
        BmobQuery<Discount> bmobQuery = new BmobQuery<Discount>();
        bmobQuery.order("discountTime");
        bmobQuery.findObjects(this, new FindListener<Discount>() {

            @Override
            public void onSuccess(List<Discount> objects) {
                // TODO Auto-generated method stub
                if(objects!=null && objects.size()>0){
                    discountList.addAll(objects);
                }
            }

            @Override
            public void onError(int code, String arg0) {
                Toast.makeText(mContext, "获取折扣信息失败，建议返回从新获取！", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void initUI() {
        setHeadTitle("下单");
        showBackBtn();
        pay_btn = (Button)findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(this);
        factPayMoney_tv = (TextView) findViewById(R.id.factPayMoney_tv);
        discount_tv = (TextView) findViewById(R.id.discount_tv);
        hasDiscountValue = (TextView) findViewById(R.id.hasDiscountValue);
        order_tlt = (TableLayout) findViewById(R.id.order_tlt);
        preOrderList = (List<PreOrder>) getIntent().getSerializableExtra("preOrderList");
        inflater = LayoutInflater.from(this);

        for(PreOrder order : preOrderList){
            View view = inflater.inflate(R.layout.item_menu,null);
            ((TextView)view.findViewById(R.id.dish_name)).setText(order.getDishName());
            ((TextView)view.findViewById(R.id.dish_value)).setText(order.getDishValue().toString());
            ((TextView)view.findViewById(R.id.dish_num)).setText(order.getDishNum().toString());
            ((TextView)view.findViewById(R.id.dish_total_value)).setText(order.getDishNum()*order.getDishValue()+"");
            totalMoney += order.getDishNum() * order.getDishValue();
            order_tlt.addView(view);
        }
        View view = inflater.inflate(R.layout.item_menu,null);
        ((TextView)view.findViewById(R.id.dish_name)).setText("总价");
        ((TextView)view.findViewById(R.id.dish_total_value)).setText(totalMoney+"");
        order_tlt.addView(view);
        order_time_tv = (TextView) findViewById(R.id.order_time);
        order_time_tv.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v == order_time_tv){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(endDate);
            calendar.add(calendar.DATE,7);//把日期往后增加一天.整数往后推,负数往前移动
            endDate = calendar.getTime();
            new SlideDateTimePicker.Builder(this.getSupportFragmentManager())
                    .setListener(listener)
                    .setInitialDate(new Date())
                    .setMinDate(new Date())
                    .setMaxDate(endDate)
                    .setIs24HourTime(true)
                    //.setTheme(SlideDateTimePicker.HOLO_DARK)
                    //.setIndicatorColor(Color.parseColor("#990000"))
                    .build()
                    .show();
        }else if(v == pay_btn){
            if(TextUtils.isEmpty(factPayMoney_tv.getText().toString())){
                Toast.makeText(mContext, "请选择就餐时间", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(mContext,PayPreOrderActivity.class);
            intent.putExtra("preOrderList", (Serializable) preOrderList);
            intent.putExtra("factTotalMoney",factTotalMoney);
            intent.putExtra("totalMoney",totalMoney);
            intent.putExtra("businessName",getIntent().getStringExtra("businessName"));
            startActivity(intent);
        }
    }

    private void getServerTime() {
        Bmob.getServerTime(mContext, new GetServerTimeListener() {
            @Override
            public void onSuccess(long time) {
                Log.e("s",sysCurrentTime+"");
                sysCurrentTime = time * 1000;
                endDate = new Date(time * 1000L);
                Log.e("s",sysCurrentTime+"");
                order_time_tv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(mContext,"获取系统时间失败",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}
