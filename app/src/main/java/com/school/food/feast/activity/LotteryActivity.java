package com.school.food.feast.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.school.food.feast.R;
import com.school.food.feast.services.UserServices;

public class LotteryActivity extends Activity implements OnClickListener{

	//设置一个时间常量，此常量有两个作用，1.圆灯视图显示与隐藏中间的切换时间；2.指针转一圈所需要的时间，现设置为500毫秒
	private static final long ONE_WHEEL_TIME = 500;
	//记录圆灯视图是否显示的布尔常量
	private boolean lightsOn = true;
	//开始转动时候的角度，初始值为0
	private int startDegree = 0;
	int increaseDegree;
	private boolean isEnd = true;
	private ImageView lightIv;
	private TextView lottery_num_tv,start_btn;
	private ImageView pointIv;
	private RelativeLayout wheelIv;
	private Integer lotteryNum = 0;

	//指针转圈圈数数据源
	private int[] laps = { 5, 7, 10, 15 };
	//指针所指向的角度数据源，因为有6个选项，所有此处是6个值
	private int[] angles = { 0, 45, 90, 135, 180, 225, 270, 315 };
	//转盘内容数组
	private String[] lotteryStr = { "苹果充电宝", "谢谢参与", "迷你音响", "谢谢参与",
			"5元储备金", "谢谢参与","mini iPad","2元储备金" };

	//子线程与UI线程通信的handler对象
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					if (lightsOn) {
						// 设置lightIv不可见
						lightIv.setVisibility(View.INVISIBLE);
						lightsOn = false;
					} else {
						// 设置lightIv可见
						lightIv.setVisibility(View.VISIBLE);
						lightsOn = true;
					}
					break;

				default:
					break;
			}
		};

	};

	//监听动画状态的监听器
	private AnimationListener al = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			isEnd = true;
			String name = lotteryStr[Math.abs(increaseDegree)% 360 / 45];
			Toast.makeText(LotteryActivity.this, name, Toast.LENGTH_LONG).show();
			lotteryNum = lotteryNum - 1;
			String source ="你还有<font color='yellow'>" + lotteryNum + "</font>次抽奖机会";
			lottery_num_tv.setText(Html.fromHtml(source));
			UserServices.subLotteryNum(LotteryActivity.this);
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lottery);
		setupViews();
		flashLights();
	}
	private void setupViews(){
		lightIv = (ImageView) findViewById(R.id.light);
		pointIv = (ImageView) findViewById(R.id.point);
		wheelIv = (RelativeLayout) findViewById(R.id.bigWheel);
		lottery_num_tv = (TextView) findViewById(R.id.lottery_num_tv);
		start_btn = (TextView) findViewById(R.id.start_btn);
		start_btn.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		lotteryNum = UserServices.getLotteryNum(this);
		String source ="你还有<font color='yellow'>" + lotteryNum + "</font>次抽奖机会";
		lottery_num_tv.setText(Html.fromHtml(source));
	}

	//控制灯圈动画的方法
	private void flashLights() {

		Timer timer = new Timer();
		TimerTask tt = new TimerTask() {

			@Override
			public void run() {
				// 向UI线程发送消息
				mHandler.sendEmptyMessage(0);

			}
		};

		// 每隔ONE_WHEEL_TIME毫秒运行tt对象的run方法
		timer.schedule(tt, 0, ONE_WHEEL_TIME);
	}

	@Override
	public void onClick(View v) {
		if(v == start_btn){
			if(lotteryNum == 0){
				Toast.makeText(LotteryActivity.this,"你还没有抽奖的机会，可通过美食预定或当面付消费一次获得！",Toast.LENGTH_LONG).show();
				return;
			}
			if(isEnd == false){
				return;
			}
			int lap = laps[(int) (Math.random() * 4)];
			int angle = angles[(int) (Math.random() * 6)];
			//每次转圈角度增量
			increaseDegree = -(lap * 360 + angle);
			//初始化旋转动画，后面的四个参数是用来设置以自己的中心点为圆心转圈
			RotateAnimation rotateAnimation = new RotateAnimation(
					startDegree, increaseDegree,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			//将最后的角度赋值给startDegree作为下次转圈的初始角度
			//计算动画播放总时间
			long time = (lap + angle / 360) * ONE_WHEEL_TIME;
			//设置动画播放时间
			rotateAnimation.setDuration(time);
			//设置动画播放完后，停留在最后一帧画面上
			rotateAnimation.setFillAfter(true);
			//设置动画的加速行为，是先加速后减速
			rotateAnimation.setInterpolator(LotteryActivity.this,
					android.R.anim.accelerate_decelerate_interpolator);
			//设置动画的监听器
			rotateAnimation.setAnimationListener(al);
			//开始播放动画
			wheelIv.startAnimation(rotateAnimation);
			isEnd = false;
		}
	}
}
