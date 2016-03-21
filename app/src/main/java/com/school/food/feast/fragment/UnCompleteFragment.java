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
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.school.food.feast.R;
import com.school.food.feast.activity.LoginActivity;
import com.school.food.feast.adapter.OrderQueryListAdapter;
import com.school.food.feast.entity.Menu;
import com.school.food.feast.entity.Order;
import com.school.food.feast.entity.UserOrder;
import com.school.food.feast.services.UserServices;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class UnCompleteFragment extends Fragment implements ReflushListener{
	private Context mContext;
	private TextView tv_text;
	private Button login_btn;
	private View root;
	private RecyclerView mRecyclerView;
	private MaterialRefreshLayout materialRefreshLayout;
	private List<Order> orderList = new ArrayList<Order>();
	private OrderQueryListAdapter mAdapter;
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
				orderList.clear();
				Toast.makeText(mContext,"正在加载，请稍后..",Toast.LENGTH_SHORT).show();
				loadData();
				materialRefreshLayout.finishRefresh();
			}
		});
		mAdapter =new OrderQueryListAdapter(mContext,orderList,false,this);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
	}

	private void loadData() {
		BmobQuery<UserOrder> eq1 = new BmobQuery<UserOrder>();
		eq1.addWhereEqualTo("phoneNum",UserServices.getPhoneNum(mContext));
		BmobQuery<UserOrder> eq2 = new BmobQuery<UserOrder>();
		eq2.addWhereEqualTo("isUse",false);
		BmobQuery<UserOrder> eq3 = new BmobQuery<UserOrder>();
		eq3.addWhereEqualTo("isUnReg",false);
		List<BmobQuery<UserOrder>> queries = new ArrayList<BmobQuery<UserOrder>>();
		queries.add(eq1);
		queries.add(eq2);
		queries.add(eq3);

		BmobQuery<UserOrder> bmobQuery = new BmobQuery<UserOrder>();
		bmobQuery.and(queries);
		bmobQuery.order("-createdAt");
		bmobQuery.findObjects(mContext, new FindListener<UserOrder>() {
			@Override
			public void onSuccess(List<UserOrder> list) {
				 if(list.size() > 0){
					 orderList.clear();
					 for(UserOrder order : list){
						 orderList.add(new Order(order.getObjectId(),order.getBusinessName(),order.getOrderId().toString(),order.getTotalMoney().toString(),order.getCreatedAt()
						 ,order.getFactTotalMoney()));
					 }
					 mAdapter.notifyDataSetChanged();
				 }
			}

			@Override
			public void onError(int i, String s) {
				Toast.makeText(mContext, "获取数据失败，请稍后再试", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void reflush() {
		Toast.makeText(mContext,"退订成功，金额已退订到你的账户余额！",Toast.LENGTH_SHORT).show();
		//mAdapter.notifyDataSetChanged();
		orderList.clear();
		loadData();
	}
}
