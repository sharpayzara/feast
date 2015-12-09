package com.school.food.feast.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.school.food.feast.R;
import com.school.food.feast.util.Constant;

public class HeadControlPanel extends RelativeLayout{
	private Context mContext;
	private TextView mMidleTitle;
	private Button mRightTitle;
	private static final float middle_title_size = 20f; 
	private static final float right_title_size = 17f; 
	private static final int default_background_color = Color.rgb(250,190,73);
 
	public HeadControlPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		mMidleTitle = (TextView)findViewById(R.id.midle_title);
		mRightTitle = (Button) findViewById(R.id.right_title);
		setBackgroundColor(default_background_color);
	}
	public void initHeadPanel(){
		
		if(mMidleTitle != null){
			setMiddleTitle(Constant.FRAGMENT_FLAG_HOME);
		}
	}
	public void setMiddleTitle(String s){
		mMidleTitle.setText(s);
		mMidleTitle.setTextSize(middle_title_size);
	}

	public Button getmRightTitle() {
		return mRightTitle;
	}

	public void setmRightTitle(Button mRightTitle) {
		this.mRightTitle = mRightTitle;
	}
	
}
