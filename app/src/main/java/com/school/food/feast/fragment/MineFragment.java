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
import android.widget.Toast;

import com.school.food.feast.R;
import com.school.food.feast.activity.CZActivity;
import com.school.food.feast.activity.FeedBackActivity;
import com.school.food.feast.activity.LoginActivity;
import com.school.food.feast.activity.PasswordActivity;
import com.school.food.feast.activity.QueryBalanceActivity;
import com.school.food.feast.entity.Discount;
import com.school.food.feast.entity.Recharge;
import com.school.food.feast.entity.User;
import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.Constant;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.BmobDialogButtonListener;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

public class MineFragment extends Fragment implements View.OnClickListener{
	LayoutInflater inflater;
	Context mContext;
	LinearLayout unlogin_layout,login_layout;
	Button login_btn;
	View root;
	TextView phoneNum;
	UpdateResponse ur;
	RelativeLayout payPassword,logOut,share_btn,ye_btn,cz_btn,cj_btn,check_update;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root =  inflater.inflate(R.layout.fragment_mine, container, false);
		mContext = getActivity();
		this.inflater = inflater;
		initUI();
		return root;
	}

	private void initUI(){
		check_update = (RelativeLayout) root.findViewById(R.id.check_update);
		check_update.setOnClickListener(this);
		ye_btn = (RelativeLayout) root.findViewById(R.id.ye_btn);
		cz_btn = (RelativeLayout) root.findViewById(R.id.cz_btn);
		cj_btn = (RelativeLayout) root.findViewById(R.id.cj_btn);
		ye_btn.setOnClickListener(this);
		cz_btn.setOnClickListener(this);
		cj_btn.setOnClickListener(this);
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
	}

	@Override
	public void onClick(View v) {
		if(v == login_btn){
			Intent intent = new Intent(mContext, LoginActivity.class);
			((Activity)mContext).startActivityForResult(intent, 1);
		}
		else if(v == ye_btn){
			Intent intent = new Intent(mContext,QueryBalanceActivity.class);
			mContext.startActivity(intent);
		}
		else if(check_update == v){
			//	BmobUpdateAgent.initAppVersion(mContext);
			BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

				@Override
				public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
					//V3.4.4版本开始，增加版本更新错误提示，可通过此方法获取到错误信息
				/*	BmobException e = updateInfo.getException();
					if(e!=null){
						Toast.makeText(mContext, "检测更新返回："+e.getMessage()+"("+e.getErrorCode()+")", Toast.LENGTH_SHORT).show();
					}else{
						ur = updateInfo;
					}*/
					//以下适用于V3.4.4之前版本
					if (updateStatus == UpdateStatus.Yes) {
						ur = updateInfo;
					}else if(updateStatus == UpdateStatus.No){
						Toast.makeText(mContext, "版本无更新", Toast.LENGTH_SHORT).show();
					}else if(updateStatus==UpdateStatus.EmptyField){//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
						Toast.makeText(mContext, "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
					}else if(updateStatus==UpdateStatus.IGNORED){
						Toast.makeText(mContext, "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
					}else if(updateStatus==UpdateStatus.ErrorSizeFormat){
						Toast.makeText(mContext, "请检查target_size填写的格式，请使用file.length()方法获取apk大小。", Toast.LENGTH_SHORT).show();
					}else if(updateStatus==UpdateStatus.TimeOut){
						Toast.makeText(mContext, "查询出错或查询超时", Toast.LENGTH_SHORT).show();
					}
				}
			});
			BmobUpdateAgent.update(mContext);
			BmobUpdateAgent.setDialogListener(new BmobDialogButtonListener() {

				@Override
				public void onClick(int status) {
					// TODO Auto-generated method stub
					switch (status) {
						case UpdateStatus.Update:
							Toast.makeText(mContext, "点击了立即更新按钮" , Toast.LENGTH_SHORT).show();
							break;
						case UpdateStatus.NotNow:
							Toast.makeText(mContext, "点击了以后再说按钮" , Toast.LENGTH_SHORT).show();
							break;
						case UpdateStatus.Close://只有在强制更新状态下才会在更新对话框的右上方出现close按钮,如果用户不点击”立即更新“按钮，这时候开发者可做些操作，比如直接退出应用等
							Toast.makeText(mContext, "点击了对话框关闭按钮" , Toast.LENGTH_SHORT).show();
							break;
					}
				}
			});
		}
		else if(v == cz_btn){
			Intent intent = new Intent(mContext,CZActivity.class);
			mContext.startActivity(intent);
		}
		else if(v == payPassword){
			/*Intent intent = new Intent(mContext, PasswordActivity.class);
			mContext.startActivity(intent);*/
			Intent intent =new Intent(mContext,FeedBackActivity.class);
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

			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
			i.putExtra(Intent.EXTRA_TEXT, "我已安装校园美食坊app，方便快捷，推荐你也使用: " + Constant.URL.shareURL);
			startActivity(i);
			/*final User user = new User();
			user.setAccountMoney(Double.parseDouble(5+""));
			String userObjectId = UserServices.getUser(mContext).getObjectId();
			user.update(mContext, userObjectId, new UpdateListener() {

				@Override
				public void onSuccess() {
					Toast.makeText(mContext,"充值成功",Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int code, String msg) {
					Toast.makeText(mContext,"充值失败",Toast.LENGTH_SHORT).show();
				}
			});		*/
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

	@Override
	public void onResume() {
		super.onResume();
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
}
