package com.school.food.feast.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.school.food.feast.R;
import com.school.food.feast.layout.AdColumnFrame;
import com.school.food.feast.util.SizeUtils;

public class HomeFragment extends Fragment{
	LayoutInflater inflater;
	RelativeLayout adLayout;
	Context mContext;
	LinearLayout adcolumnLayout;

	private View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root =  inflater.inflate(R.layout.fragment_home, container, false);
		adcolumnLayout = (LinearLayout) root.findViewById(R.id.ad_column);
		mContext = getActivity();
		this.inflater = inflater;
		initUI();
		return root;
	}

	private void initUI() {
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
}
