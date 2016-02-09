package com.school.food.feast.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bmob.pay.tool.BmobPay;
import com.school.food.feast.R;
import com.school.food.feast.activity.ChannelActivity;
import com.school.food.feast.activity.PayActivity;
import com.school.food.feast.activity.TakeAwayActivity;
import com.school.food.feast.layout.AdColumnFrame;
import com.school.food.feast.util.Constant;
import com.school.food.feast.util.SizeUtils;

public class HomeFragment extends Fragment implements View.OnClickListener{
	LayoutInflater inflater;
	RelativeLayout adLayout,preorderLayout,waimaiLayout;
	Context mContext;
	LinearLayout adcolumnLayout;
	RelativeLayout dmf_layout;
	private View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root =  inflater.inflate(R.layout.fragment_home, container, false);
		adcolumnLayout = (LinearLayout) root.findViewById(R.id.ad_column);
		mContext = getActivity();
		this.inflater = inflater;
        BmobPay.init(mContext, Constant.APPID);
		initUI();
		return root;
	}

	private void initUI() {
		dmf_layout = (RelativeLayout) root.findViewById(R.id.dmf_layout);
		preorderLayout = (RelativeLayout) root.findViewById(R.id.preorder_layout);
		waimaiLayout = (RelativeLayout) root.findViewById(R.id.waimai_layout);
		dmf_layout.setOnClickListener(this);
		preorderLayout.setOnClickListener(this);
		waimaiLayout.setOnClickListener(this);
		initAdColumn();
	}

	public void initAdColumn(){
		adLayout = (RelativeLayout) inflater.inflate(R.layout.ad_layout, null);
		ViewGroup.LayoutParams lp = adLayout.findViewById(R.id.image_layout).getLayoutParams();
		lp.height = (int) (SizeUtils.getSysWidthPx(mContext)/2.3);
		adLayout.findViewById(R.id.image_layout).setLayoutParams(lp);
		adcolumnLayout.addView(adLayout);
		AdColumnFrame.getInstence(mContext, adLayout);
	}

	@Override
	public void onClick(View v) {
		if(v == dmf_layout){
            Intent intent = new Intent(mContext, PayActivity.class);
            mContext.startActivity(intent);
		}
		else if(v == preorderLayout){
			Intent intent = new Intent(mContext, ChannelActivity.class);
			mContext.startActivity(intent);
		}
		else if(v == waimaiLayout){
			Intent intent = new Intent(mContext, TakeAwayActivity.class);
			mContext.startActivity(intent);
		}
	}
}
