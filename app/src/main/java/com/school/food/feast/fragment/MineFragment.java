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

public class MineFragment extends Fragment{
	LayoutInflater inflater;
	RelativeLayout adLayout;
	Context mContext;
	LinearLayout adcolumnLayout;

	private View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root =  inflater.inflate(R.layout.fragment_mine, container, false);
		mContext = getActivity();
		this.inflater = inflater;
		return root;
	}

}
