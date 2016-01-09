package com.school.food.feast.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.school.food.feast.R;
import com.school.food.feast.adapter.OrderPagerAdapter;
import com.school.food.feast.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements
		ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
	private LayoutInflater inflater;
	private RelativeLayout adLayout;
	private ViewPager viewPager;
	private Context mContext;
	private LinearLayout adcolumnLayout;
	private RadioGroup radioGroup;
	private RadioButton radio0,radio1;
	private OrderPagerAdapter adapter;
	private View root;
	private List<Fragment> fragments;
	private Fragment completeFrag,unCompleteFrag;
	private ImageView completeIcon,unCompleteIcon;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root =  inflater.inflate(R.layout.fragment_order, container, false);
		mContext = getActivity();
		this.inflater = inflater;
		return root;
	}

	@Override
	public void onResume() {
		initUI();
		super.onResume();
	}

	private void initUI() {
		radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup);
		radio0 = (RadioButton) root.findViewById(R.id.radio0);
		radio1 = (RadioButton) root.findViewById(R.id.radio1);
		radio0.setChecked(true);
		radioGroup.setOnCheckedChangeListener(this);

		if(fragments == null){
			fragments = new ArrayList<Fragment>();
			fragments.add(new UnCompleteFragment());
			fragments.add(new CompleteFragment());
		}
		viewPager = (ViewPager) root.findViewById(R.id.pager);
		adapter = new OrderPagerAdapter(getChildFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
		viewPager.setOffscreenPageLimit(fragments.size() - 1);
		viewPager.setOnPageChangeListener(this);
		unCompleteIcon = (ImageView) root.findViewById(R.id.uncomplete_icon);
		completeIcon = (ImageView) root.findViewById(R.id.complete_icon);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
			case R.id.radio0:
				viewPager.setCurrentItem(0);
				break;
			case R.id.radio1:
				viewPager.setCurrentItem(1);
				break;
			default:
				break;
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		getTabState(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
	private void getTabState(int index) {
		radio0.setChecked(false);
		radio1.setChecked(false);
		unCompleteIcon.setVisibility(View.GONE);
		completeIcon.setVisibility(View.GONE);
		switch (index) {
			case 0:
				radio0.setChecked(true);
				unCompleteIcon.setVisibility(View.VISIBLE);
				break;
			case 1:
				radio1.setChecked(true);
				completeIcon.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Constant.REQUESTCODE.LOGINACTIVITY){
			fragments.clear();
			fragments.add(new UnCompleteFragment());
			fragments.add(new CompleteFragment());
			fragments.add(new CompleteFragment());
			adapter.notifyDataSetChanged();
		}
	}
}
