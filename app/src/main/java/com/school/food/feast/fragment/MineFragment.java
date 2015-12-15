package com.school.food.feast.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.school.food.feast.R;
import com.school.food.feast.activity.LoginActivity;

public class MineFragment extends Fragment implements View.OnClickListener{
	LayoutInflater inflater;
	RelativeLayout adLayout;
	Context mContext;
	LinearLayout adcolumnLayout;
	Button login_btn;
	private View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root =  inflater.inflate(R.layout.fragment_mine, container, false);
		mContext = getActivity();
		this.inflater = inflater;
		initUI();
		return root;
	}

	private void initUI(){
		login_btn = (Button) root.findViewById(R.id.login_btn);
		login_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v == login_btn){
			Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);

		}
	}
}
