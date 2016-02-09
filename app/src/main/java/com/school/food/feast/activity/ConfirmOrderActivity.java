package com.school.food.feast.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPaneFraglActivity;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;
import com.school.food.feast.entity.Dish;
import com.school.food.feast.entity.PreOrder;
import com.school.food.feast.entity.User;
import com.school.food.feast.util.Constant;
import com.school.food.feast.util.TimeCountUtil;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.zip.Inflater;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;

public class ConfirmOrderActivity extends CommonHeadPaneFraglActivity implements View.OnClickListener{
    TableLayout order_tlt;
    LayoutInflater inflater;
    List<PreOrder> preOrderList;
    double totalMoney;
    TextView order_time_tv;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("MM月dd日 HH点mm分");
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            order_time_tv.setText(mFormatter.format(date));
        }

        @Override
        public void onDateTimeCancel() {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm_order);
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setHeadTitle("下单");
        showBackBtn();
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
            Date endDate = new Date();
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
        }
    }
}
