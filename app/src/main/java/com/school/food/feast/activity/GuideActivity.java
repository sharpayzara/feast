package com.school.food.feast.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.school.food.feast.R;
import com.school.food.feast.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends Activity implements OnPageChangeListener,OnClickListener{
	private ViewPager viewPager;
	private List<View> views;
	private int[]  guideImages = {R.drawable.show1,R.drawable.show2,R.drawable.show3,R.drawable.show4};
	private ViewPagerAdapter viewPagerAdapter;
	private ImageView[] points;
	private LinearLayout llt; 
	
	private int currentPoint;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initView();
		initData();
	}
	
	protected void initView(){
		llt = (LinearLayout) findViewById(R.id.linearLayout);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		views = new ArrayList<View>();
		viewPagerAdapter = new ViewPagerAdapter(views);
	}

	@SuppressLint("NewApi")
	private void initData(){ 
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		for(int i:guideImages){
			ImageView  imageView = new ImageView(this);
			imageView.setImageResource(i);
			imageView.setLayoutParams(layoutParams);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			views.add(imageView);
		}
		views.get(views.size()-1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(GuideActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		viewPager.setAdapter(viewPagerAdapter);
		initPoint();
		viewPager.setOnPageChangeListener(this);
	} 
	public void initPoint(){
		points = new ImageView[guideImages.length];
		for(int i=0;i<llt.getChildCount();i++){
			points[i] = (ImageView) llt.getChildAt(i);
			points[i].setImageResource(R.drawable.point2);
			points[i].setOnClickListener(this);
			points[i].setTag(i);
		}
		currentPoint = 0;
		points[currentPoint].setImageResource(R.drawable.point1);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		setCurrentPoint(position);
	}

	private void setCurrentPoint(int position) {
		// TODO Auto-generated method stub
		for(int i=0;i<llt.getChildCount();i++){
			points[i] = (ImageView) llt.getChildAt(i);
			points[i].setImageResource(R.drawable.point2);
			points[i].setTag(i);
		}
		points[position].setImageResource(R.drawable.point1);
	}

	@Override
	public void onClick(View v) { 
		// TODO Auto-generated method stub
		int i = (Integer)v.getTag();
		viewPager.setCurrentItem(i);
	}
}
