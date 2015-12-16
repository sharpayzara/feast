package com.school.food.feast.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.school.food.feast.R;
import com.school.food.feast.activity.LoginActivity;
import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.Constant;

public class MineFragment extends Fragment implements View.OnClickListener{
	LayoutInflater inflater;
	Context mContext;
	LinearLayout unlogin_layout,login_layout;
	Button login_btn;
	View root;
	TextView phoneNum;
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
		unlogin_layout = (LinearLayout) root.findViewById(R.id.unlogin_layout);
		login_layout = (LinearLayout) root.findViewById(R.id.login_layout);
		phoneNum = (TextView) root.findViewById(R.id.phoneNum);
		login_btn.setOnClickListener(this);
		if(UserServices.isLogin(mContext)){
			login_layout.setVisibility(View.VISIBLE);
			phoneNum.setText(UserServices.getPhoneNum(mContext));
		}else{
			unlogin_layout.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View v) {
		if(v == login_btn){
			Intent intent = new Intent(mContext, LoginActivity.class);
			((Activity)mContext).startActivityForResult(intent, 1);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Constant.REQUESTCODE.LOGINACTIVITY){
			unlogin_layout.setVisibility(View.GONE);
			login_layout.setVisibility(View.VISIBLE);
			phoneNum.setText(UserServices.getPhoneNum(mContext));
		}
	}
}
