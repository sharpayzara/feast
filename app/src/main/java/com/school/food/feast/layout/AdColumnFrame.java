package com.school.food.feast.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Scroller;

import com.school.food.feast.R;
import com.school.food.feast.adapter.AdColumnAdapter;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class AdColumnFrame implements OnPageChangeListener {

	private static Context mContext;// 上下文

	private int width;
	
	private int newWidth;

	private int padding;

	private ViewPager mViewpager;

	private AdColumnAdapter adapter;

	private ImageView[][] mImageViews;

	private int[] mImageRes = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e };

	private ImageView[] mDots;

	private LinearLayout layoutDots;

	private final long delay = 4 * 1000;

	private final int AUTO = 0;
	
	private static AdColumnFrame obj = null;
	
	private static int flagNum = 0;
	
	private static View mView;
	
	private ViewPagerScroller scroller;
	
	private AdColumnFrame(Context context, View view){
		mContext = context;
		mView = view;
		width = context.getResources().getDisplayMetrics().widthPixels;
		newWidth = (int) (divideWidth(width, 1080, 6) * 17);
		padding = (int) (divideWidth(width, 1080, 6) * 9);  
		mViewpager = (ViewPager) view.findViewById(R.id.viewPagers);
		layoutDots = (LinearLayout) view.findViewById(R.id.dotLayout);
		scroller = new ViewPagerScroller(mContext);
		scroller.setScrollDuration(2000);
        scroller.initViewPagerScroll(mViewpager);
		//mViewpager.setOnPageChangeListener(this);
		//mViewpager.setPageTransformer(true, new ViewPagerTransformer());
		initDots();
		initViewPager();
	}
 
	public static AdColumnFrame getInstence(Context context,View view){
		if(obj == null){
			obj = new AdColumnFrame(context,view);
		}else if (( mContext != null && mContext != context) || (mView != null && mView != view)){
			obj.mHandler.removeCallbacksAndMessages(null);
			obj = new AdColumnFrame(context,view);
		}
		return obj;
	}
	 
	public double divideWidth(int screenWidth, int picWidth, int retainValue) {
		BigDecimal screenBD = new BigDecimal(Double.toString(screenWidth));
		BigDecimal picBD = new BigDecimal(Double.toString(picWidth));
		return screenBD.divide(picBD, retainValue, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	/**
	 * @author
	 * 
	 *         初始化ViewPager的底部小点
	 * 
	 * */
	private void initDots() {

		mDots = new ImageView[mImageRes.length];

		for (int i = 0; i < mImageRes.length; i++) {

			ImageView iv = new ImageView(mContext);

			LayoutParams lp = new LayoutParams(newWidth, newWidth);

			lp.leftMargin = padding;

			lp.rightMargin = padding;

			lp.topMargin = padding;

			lp.bottomMargin = padding;

			iv.setLayoutParams(lp);

			iv.setImageResource(R.drawable.dot_normal);

			layoutDots.addView(iv);

			mDots[i] = iv;

		}

		mDots[0].setImageResource(R.drawable.dot_selected);
	}

	/**
	 * @author
	 * 
	 *         初始化ViewPager
	 * 
	 * */
	private void initViewPager() {

		mImageViews = new ImageView[2][];

		mImageViews[0] = new ImageView[mImageRes.length];

		mImageViews[1] = new ImageView[mImageRes.length];

		for (int i = 0; i < mImageViews.length; i++) {

			for (int j = 0; j < mImageViews[i].length; j++) {

				ImageView iv = new ImageView(mContext);

				iv.setBackgroundResource(mImageRes[j]);

				mImageViews[i][j] = iv;

			}

		}

		adapter = new AdColumnAdapter(mImageViews, mImageRes);

		mViewpager.setAdapter(adapter);

		if(flagNum != 0){
			mViewpager.setCurrentItem(flagNum);
		}else{
			mViewpager.setCurrentItem(mImageRes.length * 50);	
		}
		mHandler.sendEmptyMessageDelayed(AUTO, delay);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case AUTO:

				if(flagNum == 0){
					flagNum = mViewpager.getCurrentItem();
				}

				mViewpager.setCurrentItem(++flagNum);

				mHandler.sendEmptyMessageDelayed(AUTO, delay);
				break;

			default:
				break;
			}

		};
	};

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		flagNum = arg0;
	}

	@Override
	public void onPageSelected(int position) {

		setCurrentDot(position % mImageRes.length);

	}

	/**
	 * @author
	 * 
	 *         设置ViewPager当前的底部小点
	 * 
	 * 
	 * */
	private void setCurrentDot(int currentPosition) {

		for (int i = 0; i < mDots.length; i++) {

			if (i == currentPosition) {

				mDots[i].setImageResource(R.drawable.dot_selected);

			} else {

				mDots[i].setImageResource(R.drawable.dot_normal);

			}
		}
	}
	public class ViewPagerScroller extends Scroller {
		private int mScrollDuration = 3000;             // 滑动速度

		/**
		 * 设置速度速度
		 * @param duration
		 */
		public void setScrollDuration(int duration){
			this.mScrollDuration = duration;
		}

		public ViewPagerScroller(Context context) {
			super(context);
		}

		public ViewPagerScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@SuppressLint("NewApi")
		public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
			super(context, interpolator, flywheel);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			super.startScroll(startX, startY, dx, dy, mScrollDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			super.startScroll(startX, startY, dx, dy, mScrollDuration);
		}

		public void initViewPagerScroll(ViewPager viewPager) {
			try {
				Field mScroller = ViewPager.class.getDeclaredField("mScroller");
				mScroller.setAccessible(true);
				mScroller.set(viewPager, this);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
