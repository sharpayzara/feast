package com.school.food.feast.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.school.food.feast.R;
import com.school.food.feast.activity.CZActivity;
import com.school.food.feast.activity.FeedBackActivity;
import com.school.food.feast.activity.LoginActivity;
import com.school.food.feast.activity.LotteryActivity;
import com.school.food.feast.activity.MainActivity;
import com.school.food.feast.activity.QueryBalanceActivity;

import com.school.food.feast.services.UserServices;
import com.school.food.feast.util.Constant;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;

public class MineFragment extends Fragment implements View.OnClickListener{
	LayoutInflater inflater;
	Context mContext;
	LinearLayout unlogin_layout,login_layout;
	Button login_btn;
	View root;
	TextView phoneNum,version_code;
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
		version_code = (TextView) root.findViewById(R.id.version_code);
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
		PackageManager manager = root.getContext().getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(mContext.getPackageName(), 0);
			version_code.setText("当前版本：" + info.versionName);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	public boolean judgeIsLogin(){
		if(!UserServices.isLogin(mContext)){
			Intent intent = new Intent(mContext, LoginActivity.class);
			startActivityForResult(intent,1);
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void onClick(View v) {
		if(v == login_btn){
			Intent intent = new Intent(mContext, LoginActivity.class);
			((Activity)mContext).startActivityForResult(intent, 1);
		}
		else if(v == ye_btn){
			if(!judgeIsLogin()){
				return;
			}
			Intent intent = new Intent(mContext,QueryBalanceActivity.class);
			mContext.startActivity(intent);
		}else if(v == cj_btn){
			if(!judgeIsLogin()){
				return;
			}
			Intent intent = new Intent(mContext,LotteryActivity.class);
			mContext.startActivity(intent);
		}
		else if(check_update == v){
			//	BmobUpdateAgent.initAppVersion(mContext);

			BmobUpdateAgent.update(mContext);
		/*	BmobUpdateAgent.setDialogListener(new BmobDialogButtonListener() {

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
			});*/
		}
		else if(v == cz_btn){
			if(!judgeIsLogin()){
				return;
			}
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
							int pid = android.os.Process.myPid(); //获取当前应用程序的PID
							android.os.Process.killProcess(pid);
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
			i.putExtra(Intent.EXTRA_TEXT, "我已安装课间便当app，方便快捷，推荐你也使用: " + Constant.URL.shareURL);
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
