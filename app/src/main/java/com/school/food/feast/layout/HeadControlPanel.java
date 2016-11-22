package com.school.food.feast.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.school.food.feast.R;
import com.school.food.feast.util.Constant;

public class HeadControlPanel extends RelativeLayout{
	private Context mContext;
	private TextView mMidleTitle;
	private LinearLayout mLeftBtn;
	private Button mRightTitle;
	private static final float middle_title_size = 20f; 
	private static final float right_title_size = 17f; 
	private static final int default_background_color = Color.rgb(229,125,30);

	public HeadControlPanel(Context context) {
		super(context);
	}

	public HeadControlPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HeadControlPanel(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mMidleTitle = (TextView)findViewById(R.id.midle_title);
		mRightTitle = (Button) findViewById(R.id.right_title);
		mLeftBtn = (LinearLayout) findViewById(R.id.left_btn);
		setBackgroundColor(default_background_color);
	}
	public void initHeadPanel(){
		if(mMidleTitle != null){
			setMiddleTitle(Constant.FRAGMENT_FLAG_HOME);
		}
	}
	public void setTitlePanelColor(int color){
		setBackgroundColor(color);
	}
	public void setTitlePanelTextColor(int color){
		mMidleTitle.setTextColor(color);
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
