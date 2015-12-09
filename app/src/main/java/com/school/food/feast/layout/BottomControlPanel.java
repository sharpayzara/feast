package com.school.food.feast.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.school.food.feast.R;
import com.school.food.feast.util.Constant;
import com.school.food.feast.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;


public class BottomControlPanel extends RelativeLayout implements View.OnClickListener{
	private Context mContext;
	private ImageText mHomeBtn = null;
	private ImageText mOrderBtn = null;
	private ImageText mMineBtn = null;
	private int DEFALUT_BACKGROUND_COLOR = Color.rgb(243, 243, 243);
	private BottomPanelCallback mBottomCallback = null;
	private List<ImageText> viewList = new ArrayList<ImageText>();

	public interface BottomPanelCallback{
		public void onBottomPanelClick(int itemId);
	}

	public BottomControlPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mHomeBtn = (ImageText)findViewById(R.id.btn_home);
		mOrderBtn = (ImageText)findViewById(R.id.btn_order);
		mMineBtn = (ImageText)findViewById(R.id.btn_mine);
		setBackgroundColor(DEFALUT_BACKGROUND_COLOR);
		viewList.add(mHomeBtn);
		viewList.add(mOrderBtn);
		viewList.add(mMineBtn);
	}
	public void initBottomPanel(){
		if(mHomeBtn != null){
			mHomeBtn.setImage(R.drawable.home);
			mHomeBtn.setText("主页");
		}
		if(mOrderBtn != null){
			mOrderBtn.setImage(R.drawable.order);
			mOrderBtn.setText("订单");
		}
		if(mMineBtn != null){
			mMineBtn.setImage(R.drawable.mine);
			mMineBtn.setText("我的");
		}
		setBtnListener();
	}

	private void setBtnListener(){
		int num = this.getChildCount();
		for(int i = 0; i < num; i++){
			View v = getChildAt(i);
			if(v != null){
				v.setOnClickListener(this);
			}
		}
	}

	public void setBottomCallback(BottomPanelCallback bottomCallback){
		mBottomCallback = bottomCallback;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		initBottomPanel();
		int index = -1;
		switch(v.getId()){
			case R.id.btn_home:
				index = Constant.BTN_FLAG_HOME;
				mHomeBtn.setChecked(Constant.BTN_FLAG_HOME);
				break;
			case R.id.btn_order:
				index = Constant.BTN_FLAG_ORDER;
				mOrderBtn.setChecked(Constant.BTN_FLAG_ORDER);
				break;
			case R.id.btn_mine:
				index = Constant.BTN_FLAG_MINE;
				mMineBtn.setChecked(Constant.BTN_FLAG_MINE);
				break;
			default:break;
		}
		if(mBottomCallback != null){
			mBottomCallback.onBottomPanelClick(index);
		}
	}

	public void defaultBtnChecked(){
		if(mHomeBtn != null){
			mHomeBtn.setChecked(Constant.BTN_FLAG_HOME);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		layoutItems(left, top, right, bottom);
	}
	/**最左边和最右边的view由母布局的padding进行控制位置。这里需对第2、3个view的位置重新设置
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	private void layoutItems(int left, int top, int right, int bottom){
		int n = getChildCount();
		int widthPx = SizeUtils.getSysWidthPx(mContext);
		int eachWidth = widthPx / n;
		for (int i = 0; i < n; i++) {
			LayoutParams tempParams = (LayoutParams) viewList.get(i)
					.getLayoutParams();
			tempParams.width = eachWidth;
			viewList.get(i).setLayoutParams(tempParams);
		}
	}
}
