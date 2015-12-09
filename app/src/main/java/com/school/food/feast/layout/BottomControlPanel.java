package com.school.food.feast.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.school.food.feast.R;
import com.school.food.feast.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class BottomControlPanel extends RelativeLayout implements View.OnClickListener{
	//private Context mContext;
	private ImageText mHomeBtn = null;
	private ImageText mCardBtn = null;
	private ImageText mOrderBtn = null;
	private ImageText mFunBtn = null;
	private int DEFALUT_BACKGROUND_COLOR = Color.rgb(243, 243, 243);
	private BottomPanelCallback mBottomCallback = null;
	private List<ImageText> viewList = new ArrayList<ImageText>();

	public interface BottomPanelCallback{
		public void onBottomPanelClick(int itemId);
	}

	public BottomControlPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mHomeBtn = (ImageText)findViewById(R.id.btn_home);
		mCardBtn = (ImageText)findViewById(R.id.btn_cart);
		mOrderBtn = (ImageText)findViewById(R.id.btn_order);
		mFunBtn = (ImageText)findViewById(R.id.btn_fun);
		setBackgroundColor(DEFALUT_BACKGROUND_COLOR);
		viewList.add(mHomeBtn);
		viewList.add(mCardBtn);
		viewList.add(mOrderBtn);
		viewList.add(mFunBtn);
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
		if(mCardBtn != null){
			mCardBtn.setImage(R.drawable.mine);
			mCardBtn.setText("购物车");
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
			case R.id.btn_cart:
				index = Constant.BTN_FLAG_MINE;
				mCardBtn.setChecked(Constant.BTN_FLAG_MINE);
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
		if(n == 0){
			return;
		}
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int width = right - left;
		int allViewWidth = 0;
		for(int i = 0; i< n; i++){
			View v = getChildAt(i);
			allViewWidth += v.getWidth();
		}
		int blankWidth = (width - allViewWidth - paddingLeft - paddingRight) / (n - 1);

		LayoutParams params1 = (LayoutParams) viewList.get(1).getLayoutParams();
		params1.leftMargin = blankWidth;
		viewList.get(1).setLayoutParams(params1);

		LayoutParams params2 = (LayoutParams) viewList.get(2).getLayoutParams();
		params2.leftMargin = blankWidth;
		viewList.get(2).setLayoutParams(params2);
	}
}
