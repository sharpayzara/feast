package com.school.food.feast.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.school.food.feast.R;
import com.school.food.feast.activity.LoginActivity;
import com.school.food.feast.activity.PasswordActivity;
import com.school.food.feast.entity.Discount;
import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.Constant;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class MineFragment extends Fragment implements View.OnClickListener{
	LayoutInflater inflater;
	Context mContext;
	LinearLayout unlogin_layout,login_layout;
	Button login_btn;
	View root;
	TextView phoneNum;
	RelativeLayout payPassword,logOut,share_btn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root =  inflater.inflate(R.layout.fragment_mine, container, false);
		mContext = getActivity();
		this.inflater = inflater;
		initUI();
		return root;
	}

	private void initUI(){
		payPassword = (RelativeLayout) root.findViewById(R.id.payPassword);
		payPassword.setOnClickListener(this);
		share_btn = (RelativeLayout) root.findViewById(R.id.share_btn);
		share_btn.setOnClickListener(this);
		logOut = (RelativeLayout) root.findViewById(R.id.log_out);
		logOut.setOnClickListener(this);
		login_btn = (Button) root.findViewById(R.id.login_btn);
		unlogin_layout = (LinearLayout) root.findViewById(R.id.unlogin_layout);
		login_layout = (LinearLayout) root.findViewById(R.id.login_layout);
		phoneNum = (TextView) root.findViewById(R.id.phoneNum);
		login_btn.setOnClickListener(this);
		if(UserServices.isLogin(mContext)){
			login_layout.setVisibility(View.VISIBLE);
			unlogin_layout.setVisibility(View.GONE);
			phoneNum.setText(UserServices.getPhoneNum(mContext));
			logOut.setVisibility(View.VISIBLE);
		}else{
			login_layout.setVisibility(View.GONE);
			unlogin_layout.setVisibility(View.VISIBLE);
			logOut.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		if(v == login_btn){
			Intent intent = new Intent(mContext, LoginActivity.class);
			((Activity)mContext).startActivityForResult(intent, 1);
		}
		else if(v == payPassword){
			Intent intent = new Intent(mContext, PasswordActivity.class);
			mContext.startActivity(intent);
		}
		else if(v == logOut){
			new AlertDialog.Builder(mContext).setTitle("系统提示")//设置对话框标题
					.setMessage("确定注销登录？")//设置显示的内容
					.setPositiveButton("确定",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						BmobUser.logOut(mContext);
						initUI();
					}})
					.setNegativeButton("返回",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}})
					.show();
		}
		else if(v == share_btn){
			final Discount p2 = new Discount();
			p2.setDiscountTime(60 * 3l);
			p2.setDiscountValue(5.5);
			p2.save(this.getContext(), new SaveListener() {

				@Override
				public void onSuccess() {

				}

				@Override
				public void onFailure(int code, String msg) {

				}
			});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Constant.REQUESTCODE.LOGINACTIVITY){
			unlogin_layout.setVisibility(View.GONE);
			login_layout.setVisibility(View.VISIBLE);
			phoneNum.setText(UserServices.getPhoneNum(mContext));
            logOut.setVisibility(View.VISIBLE);
		}
	}
}
