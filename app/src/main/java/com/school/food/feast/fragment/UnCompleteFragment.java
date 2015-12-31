package com.school.food.feast.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.school.food.feast.R;
import com.school.food.feast.activity.LoginActivity;
import com.school.food.feast.adapter.OrderQueryListAdapter;
import com.school.food.feast.entity.Order;
import com.school.food.feast.services.UserServices;

import java.util.ArrayList;
import java.util.List;

public class UnCompleteFragment extends Fragment {
	private Context mContext;
	private TextView tv_text;
	private Button login_btn;
	private View root;
	private RecyclerView mRecyclerView;
	private MaterialRefreshLayout materialRefreshLayout;
	private List<Order> list = new ArrayList<Order>();
	private OrderQueryListAdapter mAdapter;
	int i = 110010;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mContext = this.getActivity();
		if(UserServices.isLogin(mContext)){
			root  = inflater.inflate(R.layout.complete_fragment, null);
			initUI();
			initData();
		}else{
			root  = inflater.inflate(R.layout.unlogin_fragment, null);
			root.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, LoginActivity.class);
					((Activity)mContext).startActivityForResult(intent, 1);
				}
			});
		}
		return root;
	}

	private void initData() {
		loadData();
	}

	private void initUI() {
		materialRefreshLayout = (MaterialRefreshLayout) root.findViewById(R.id.materialRefreshLayout);
		mRecyclerView = (RecyclerView) root.findViewById(R.id.recycle_view);
		materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
			@Override
			public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
				loadData();
				materialRefreshLayout.finishRefresh();
			}
		});
		mAdapter =new OrderQueryListAdapter(mContext,list,false);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
	}

	private void loadData() {
		Order order = new Order("菜品：蛋炒饭  6元","2","12");
		//OrderServices.addOrder(mContext,order);
		list.add(order);
	}

}
