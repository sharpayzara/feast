package com.school.food.feast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class OrderPagerAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> fragments;

	public OrderPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public OrderPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
